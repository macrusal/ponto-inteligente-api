package br.com.hibejix.pontointeligente.api.services.impl;

import br.com.hibejix.pontointeligente.api.entities.Empresa;
import br.com.hibejix.pontointeligente.api.repositories.EmpresaRepository;
import br.com.hibejix.pontointeligente.api.services.EmpresaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author msalvador
 * @version $Revision: $<br/>
 *          $Id: $
 * @since 24/03/2018 21:59
 */
@Service
public class EmpresaServiceImpl implements EmpresaService {

    private static final Logger logger = LoggerFactory.getLogger(EmpresaServiceImpl.class);

    @Autowired
    EmpresaRepository repository;

    @Override
    public Optional<Empresa> buscarPorCnpj(String cnpj) {
        logger.info("Buscando uma empresa para o CNPJ {}", cnpj);
        return Optional.ofNullable(repository.findByCnpj(cnpj));
    }

    @Override
    public Empresa persistir(Empresa empresa) {
        logger.info("Persistindo empresa{}", empresa);
        return this.repository.save(empresa);
    }
}
