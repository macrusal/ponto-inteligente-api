package br.com.hibejix.pontointeligente.api.controllers;

import br.com.hibejix.pontointeligente.api.dtos.EmpresaDTO;
import br.com.hibejix.pontointeligente.api.entities.Empresa;
import br.com.hibejix.pontointeligente.api.response.Response;
import br.com.hibejix.pontointeligente.api.services.EmpresaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author msalvador
 * @version $Revision: $<br/>
 *          $Id: $
 * @since 27/03/2018 03:04
 */
@RestController
@RequestMapping("/api/empresas")
@CrossOrigin(origins = "*")
public class EmpresaController {

    private static final Logger log = LoggerFactory.getLogger(EmpresaController.class);

    @Autowired
    private EmpresaService empresaService;

    public EmpresaController() {
    }

    /**
     * Retorna uma empresa dado um CNPJ.
     *
     * @param cnpj
     * @return ResponseEntity<Response<EmpresaDTO>>
     */
    @GetMapping(value = "/cnpj/{cnpj}")
    public ResponseEntity<Response<EmpresaDTO>> buscarPorCnpj(@PathVariable("cnpj") String cnpj) {
        log.info("Buscando empresa por CNPJ: {}", cnpj);
        Response<EmpresaDTO> response = new Response<EmpresaDTO>();
        Optional<Empresa> empresa = empresaService.buscarPorCnpj(cnpj);
        List<String> list = new ArrayList<>();
        if (!empresa.isPresent()) {
            log.info("Empresa nao encontrada para o CNPJ: {}", cnpj);
            list.add("Empresa nao encontrada para o CNPJ " + cnpj);
            response.setErrors(list);
            //response.getErrors().add("Empresa nao encontrada para o CNPJ " + cnpj);

            return ResponseEntity.badRequest().body(response);
        }

        response.setData(this.converterEmpresaDTO(empresa.get()));
        return ResponseEntity.ok(response);
    }

    /**
     * Popula um DTO com os dados de uma empresa.
     *
     * @param empresa
     * @return EmpresaDTO
     */
    private EmpresaDTO converterEmpresaDTO(Empresa empresa) {
        EmpresaDTO empresaDTO = new EmpresaDTO();
        empresaDTO.setId(empresa.getId());
        empresaDTO.setCnpj(empresa.getCnpj());
        empresaDTO.setRazaoSocial(empresa.getRazaoSocial());

        return empresaDTO;
    }
}
