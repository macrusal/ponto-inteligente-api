package br.com.hibejix.pontointeligente.api.controllers;

import br.com.hibejix.pontointeligente.api.dtos.CadastroPFDTO;
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
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author msalvador
 * @version $Revision: $<br/>
 *          $Id: $
 * @since 27/03/2018 01:53
 */
@RestController
@RequestMapping("/api/cadastrar-pf")
@CrossOrigin(origins = "*")
public class CadastroPFController {

    private static final Logger logger = LoggerFactory.getLogger(CadastroPFController.class);

    @Autowired
    EmpresaService empresaService;

    @Autowired
    FuncionarioService funcionarioService;

    @Autowired
    private MessageSource messageSource;

    public CadastroPFController() {
    }

    /**
     * Cadastra um funcionário pessoa física no sistema.
     *
     * @param cadastroPFDTO
     * @param result
     * @return ResponseEntity<Response<CadastroPFDTO>>
     * @throws NoSuchAlgorithmException
     */
    @PostMapping
    public ResponseEntity<Response<CadastroPFDTO>> cadastrar(@Valid @RequestBody CadastroPFDTO cadastroPFDTO,
                                                             BindingResult result) throws NoSuchAlgorithmException {
        logger.info("Cadastrando PF: {}", cadastroPFDTO.toString());
        Response<CadastroPFDTO> response = new Response<CadastroPFDTO>();

        validarDadosExistentes(cadastroPFDTO, result);
        Funcionario funcionario = this.converterDTOParaFuncionario(cadastroPFDTO, result);
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

        Optional<Empresa> empresa = this.empresaService.buscarPorCnpj(cadastroPFDTO.getCnpj());
        empresa.ifPresent(emp -> funcionario.setEmpresa(emp));
        this.funcionarioService.persistir(funcionario);

        response.setData(this.converterCadastroPFDTO(funcionario));
        return ResponseEntity.ok(response);
    }

    /**
     * Verifica se a empresa está cadastrada e se o funcionário não existe na base de dados.
     *
     * @param cadastroPFDTO
     * @param result
     */
    private void validarDadosExistentes(CadastroPFDTO cadastroPFDTO, BindingResult result) {
        Optional<Empresa> empresa = this.empresaService.buscarPorCnpj(cadastroPFDTO.getCnpj());
        if (!empresa.isPresent()) {
            result.addError(new ObjectError("empresa", "Empresa não cadastrada."));
        }

        this.funcionarioService.buscarPorCpf(cadastroPFDTO.getCpf())
                .ifPresent(func -> result.addError(new ObjectError("funcionario", "CPF já existente.")));

        this.funcionarioService.buscarPorEmail(cadastroPFDTO.getEmail())
                .ifPresent(func -> result.addError(new ObjectError("funcionario", "Email já existente.")));
    }

    /**
     * Converte os dados do DTO para funcionário.
     *
     * @param cadastroPFDTO
     * @param result
     * @return Funcionario
     * @throws NoSuchAlgorithmException
     */
    private Funcionario converterDTOParaFuncionario(CadastroPFDTO cadastroPFDTO, BindingResult result)
            throws NoSuchAlgorithmException {
        Funcionario funcionario = new Funcionario();
        funcionario.setNome(cadastroPFDTO.getNome());
        funcionario.setEmail(cadastroPFDTO.getEmail());
        funcionario.setCpf(cadastroPFDTO.getCpf());
        funcionario.setPerfil(PerfilEnum.ROLE_USUARIO);
//        funcionario.setSenha(PasswordUtils.gerarBCrypt(cadastroPFDTO.getSenha()));
        funcionario.setSenha(cadastroPFDTO.getSenha());
        cadastroPFDTO.getQtdHorasAlmoco()
                .ifPresent(qtdHorasAlmoco -> funcionario.setQtdHorasAlmoco(Float.valueOf(qtdHorasAlmoco)));
        cadastroPFDTO.getQtdHorasTrabalhoDia()
                .ifPresent(qtdHorasTrabDia -> funcionario.setQtdHorasTrabalhoDia(Float.valueOf(qtdHorasTrabDia)));
        cadastroPFDTO.getValorHora().ifPresent(valorHora -> funcionario.setValorHora(new BigDecimal(valorHora)));

        return funcionario;
    }

    /**
     * Popula o DTO de cadastro com os dados do funcionário e empresa.
     *
     * @param funcionario
     * @return CadastroPFDTO
     */
    private CadastroPFDTO converterCadastroPFDTO(Funcionario funcionario) {
        CadastroPFDTO cadastroPFDTO = new CadastroPFDTO();
        cadastroPFDTO.setId(funcionario.getId());
        cadastroPFDTO.setNome(funcionario.getNome());
        cadastroPFDTO.setEmail(funcionario.getEmail());
        cadastroPFDTO.setCpf(funcionario.getCpf());
        cadastroPFDTO.setCnpj(funcionario.getEmpresa().getCnpj());
        funcionario.getQtdHorasAlmocoOpt().ifPresent(qtdHorasAlmoco -> cadastroPFDTO
                .setQtdHorasAlmoco(Optional.of(Float.toString(qtdHorasAlmoco))));
        funcionario.getQtdHorasTrabalhoDiaOpt().ifPresent(
                qtdHorasTrabDia -> cadastroPFDTO.setQtdHorasTrabalhoDia(Optional.of(Float.toString(qtdHorasTrabDia))));
        funcionario.getValorHoraOpt()
                .ifPresent(valorHora -> cadastroPFDTO.setValorHora(Optional.of(valorHora.toString())));

        return cadastroPFDTO;
    }
}
