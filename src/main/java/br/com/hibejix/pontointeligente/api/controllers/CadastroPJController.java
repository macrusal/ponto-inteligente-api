package br.com.hibejix.pontointeligente.api.controllers;

import br.com.hibejix.pontointeligente.api.dtos.CadastroPJDTO;
import br.com.hibejix.pontointeligente.api.entities.Empresa;
import br.com.hibejix.pontointeligente.api.entities.Funcionario;
import br.com.hibejix.pontointeligente.api.enums.PerfilEnum;
import br.com.hibejix.pontointeligente.api.response.Response;
import br.com.hibejix.pontointeligente.api.services.EmpresaService;
import br.com.hibejix.pontointeligente.api.services.FuncionarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author msalvador
 * @version $Revision: $<br/>
 *          $Id: $
 * @since 25/03/2018 16:16
 */
@RestController
@RequestMapping("/api/cadastrar-pj")
@CrossOrigin(origins = "*")
public class CadastroPJController {

    private static final Logger logger = LoggerFactory.getLogger(CadastroPJController.class);

    @Autowired
    private FuncionarioService funcionarioService;

    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private MessageSource messageSource;

    public CadastroPJController() {

    }

    /**
     * Cadastra uma pessoa jurídica no sistema.
     *
     * @param cadastroPJDTO
     * @param result
     * @return ResponseEntity<Response<CadastroPJDto>>
     * @throws NoSuchAlgorithmException
     */
    @PostMapping
    public ResponseEntity<Response<CadastroPJDTO>> cadastrar(@Valid @RequestBody CadastroPJDTO cadastroPJDTO,
                                                             BindingResult result) throws NoSuchAlgorithmException {

        logger.info("Cadastrando PJ: {}", cadastroPJDTO.toString());
        Response<CadastroPJDTO> response = new Response<CadastroPJDTO>();

        validarDadosExistentes(cadastroPJDTO, result);
        Empresa empresa = this.converterDTOParaEmpresa(cadastroPJDTO);
        Funcionario funcionario = this.converterDTOParaFuncionario(cadastroPJDTO, result);
        List<String> list = new ArrayList<>();
        if (result.hasErrors()) {
            logger.error("Erro validando dados de cadastro PJ: {}", result.getAllErrors());
            for (Object object : result.getAllErrors()) {
                if (object instanceof ObjectError) {
                    ObjectError error = (ObjectError) object;
                    String message = messageSource.getMessage(error, null);
                    list.add(message);
                }
            }
            response.setErrors(list);
            //result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        this.empresaService.persistir(empresa);
        funcionario.setEmpresa(empresa);
        this.funcionarioService.persistir(funcionario);

        response.setData(this.converterCadastroPJDTO(funcionario));
        return ResponseEntity.ok(response);
    }

    /**
     * Verifica se a empresa ou funcionário já existem na base de dados.
     *
     * @param cadastroPJDto
     * @param result
     */
    private void validarDadosExistentes(CadastroPJDTO cadastroPJDto, BindingResult result) {
        this.empresaService.buscarPorCnpj(cadastroPJDto.getCnpj())
                .ifPresent(emp -> result.addError(new ObjectError("empresa", "Empresa ja existente.")));

        this.funcionarioService.buscarPorCpf(cadastroPJDto.getCpf())
                .ifPresent(func -> result.addError(new ObjectError("funcionario", "CPF ja existente.")));

        this.funcionarioService.buscarPorEmail(cadastroPJDto.getEmail())
                .ifPresent(func -> result.addError(new ObjectError("funcionario", "Email ja existente.")));
    }

    /**
     * Converte os dados do DTO para empresa.
     *
     * @param cadastroPJDto
     * @return Empresa
     */
    private Empresa converterDTOParaEmpresa(CadastroPJDTO cadastroPJDto) {
        Empresa empresa = new Empresa();
        empresa.setCnpj(cadastroPJDto.getCnpj());
        empresa.setRazaoSocial(cadastroPJDto.getRazaoSocial());

        return empresa;
    }

    /**
     * Converte os dados do DTO para funcionário.
     *
     * @param cadastroPJDto
     * @param result
     * @return Funcionario
     * @throws NoSuchAlgorithmException
     */
    private Funcionario converterDTOParaFuncionario(CadastroPJDTO cadastroPJDto, BindingResult result)
            throws NoSuchAlgorithmException {
        Funcionario funcionario = new Funcionario();
        funcionario.setNome(cadastroPJDto.getNome());
        funcionario.setEmail(cadastroPJDto.getEmail());
        funcionario.setCpf(cadastroPJDto.getCpf());
        funcionario.setPerfil(PerfilEnum.ROLE_ADMIN);
//        funcionario.setSenha(PasswordUtils.gerarBCrypt(cadastroPJDto.getSenha()));
        funcionario.setSenha(cadastroPJDto.getSenha());

        return funcionario;
    }

    /**
     * Popula o DTO de cadastro com os dados do funcionário e empresa.
     *
     * @param funcionario
     * @return CadastroPJDTO
     */
    private CadastroPJDTO converterCadastroPJDTO(Funcionario funcionario) {
        CadastroPJDTO cadastroPJDto = new CadastroPJDTO();
        cadastroPJDto.setId(funcionario.getId());
        cadastroPJDto.setNome(funcionario.getNome());
        cadastroPJDto.setEmail(funcionario.getEmail());
        cadastroPJDto.setCpf(funcionario.getCpf());
        cadastroPJDto.setRazaoSocial(funcionario.getEmpresa().getRazaoSocial());
        cadastroPJDto.setCnpj(funcionario.getEmpresa().getCnpj());

        return cadastroPJDto;
    }

}
