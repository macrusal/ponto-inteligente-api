package br.com.hibejix.pontointeligente.api.controllers;

import br.com.hibejix.pontointeligente.api.dtos.FuncionarioDTO;
import br.com.hibejix.pontointeligente.api.entities.Funcionario;
import br.com.hibejix.pontointeligente.api.response.Response;
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
 * @since 27/03/2018 06:06
 */
@RestController
@RequestMapping("/api/funcionarios")
@CrossOrigin(origins = "*")
public class FuncionarioController {

    private static final Logger log = LoggerFactory.getLogger(FuncionarioController.class);

    @Autowired
    private FuncionarioService funcionarioService;

    @Autowired
    private MessageSource messageSource;

    public FuncionarioController() {
    }

    /**
     * Atualiza os dados de um funcionário.
     *
     * @param id
     * @param funcionarioDTO
     * @param result
     * @return ResponseEntity<Response<FuncionarioDTO>>
     * @throws NoSuchAlgorithmException
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<Response<FuncionarioDTO>> atualizar(@PathVariable("id") Long id,
                                                              @Valid @RequestBody FuncionarioDTO funcionarioDTO, BindingResult result) throws NoSuchAlgorithmException {
        log.info("Atualizando funcionário: {}", funcionarioDTO.toString());
        Response<FuncionarioDTO> response = new Response<FuncionarioDTO>();

        Optional<Funcionario> funcionario = this.funcionarioService.buscarPorId(id);
        if (!funcionario.isPresent()) {
            result.addError(new ObjectError("funcionario", "Funcionário não encontrado."));
        }

        this.atualizarDadosFuncionario(funcionario.get(), funcionarioDTO, result);
        List<String> list = new ArrayList<>();
        if (result.hasErrors()) {
            log.error("Erro validando funcionário: {}", result.getAllErrors());
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

        this.funcionarioService.persistir(funcionario.get());
        response.setData(this.converterFuncionarioDTO(funcionario.get()));

        return ResponseEntity.ok(response);
    }

    /**
     * Atualiza os dados do funcionário com base nos dados encontrados no DTO.
     *
     * @param funcionario
     * @param funcionarioDTO
     * @param result
     * @throws NoSuchAlgorithmException
     */
    private void atualizarDadosFuncionario(Funcionario funcionario, FuncionarioDTO funcionarioDTO, BindingResult result)
            throws NoSuchAlgorithmException {
        funcionario.setNome(funcionarioDTO.getNome());

        if (!funcionario.getEmail().equals(funcionarioDTO.getEmail())) {
            this.funcionarioService.buscarPorEmail(funcionarioDTO.getEmail())
                    .ifPresent(func -> result.addError(new ObjectError("email", "Email já existente.")));
            funcionario.setEmail(funcionarioDTO.getEmail());
        }

        funcionario.setQtdHorasAlmoco(null);
        funcionarioDTO.getQtdHorasAlmoco()
                .ifPresent(qtdHorasAlmoco -> funcionario.setQtdHorasAlmoco(Float.valueOf(qtdHorasAlmoco)));

        funcionario.setQtdHorasTrabalhoDia(null);
        funcionarioDTO.getQtdHorasTrabalhoDia()
                .ifPresent(qtdHorasTrabDia -> funcionario.setQtdHorasTrabalhoDia(Float.valueOf(qtdHorasTrabDia)));

        funcionario.setValorHora(null);
        funcionarioDTO.getValorHora().ifPresent(valorHora -> funcionario.setValorHora(new BigDecimal(valorHora)));

        if (funcionarioDTO.getSenha().isPresent()) {
//            funcionario.setSenha(PasswordUtils.gerarBCrypt(funcionarioDTO.getSenha().get()));
            funcionario.setSenha(funcionarioDTO.getSenha().get());
        }
    }

    /**
     * Retorna um DTO com os dados de um funcionário.
     *
     * @param funcionario
     * @return FuncionarioDTO
     */
    private FuncionarioDTO converterFuncionarioDTO(Funcionario funcionario) {
        FuncionarioDTO funcionarioDTO = new FuncionarioDTO();
        funcionarioDTO.setId(funcionario.getId());
        funcionarioDTO.setEmail(funcionario.getEmail());
        funcionarioDTO.setNome(funcionario.getNome());
        funcionario.getQtdHorasAlmocoOpt().ifPresent(
                qtdHorasAlmoco -> funcionarioDTO.setQtdHorasAlmoco(Optional.of(Float.toString(qtdHorasAlmoco))));
        funcionario.getQtdHorasTrabalhoDiaOpt().ifPresent(
                qtdHorasTrabDia -> funcionarioDTO.setQtdHorasTrabalhoDia(Optional.of(Float.toString(qtdHorasTrabDia))));
        funcionario.getValorHoraOpt()
                .ifPresent(valorHora -> funcionarioDTO.setValorHora(Optional.of(valorHora.toString())));

        return funcionarioDTO;
    }

}
