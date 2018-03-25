package br.com.hibejix.pontointeligente.api.services.impl;

import br.com.hibejix.pontointeligente.api.entities.Funcionario;
import br.com.hibejix.pontointeligente.api.repositories.FuncionarioRepository;
import br.com.hibejix.pontointeligente.api.services.FuncionarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author msalvador
 * @version $Revision: $<br/>
 *          $Id: $
 * @since 25/03/2018 07:45
 */
@Service
public class FuncionarioServiceImpl implements FuncionarioService {

    private static final Logger logger = LoggerFactory.getLogger(FuncionarioServiceImpl.class);

    @Autowired
    private FuncionarioRepository repository;

    /**
     * Persiste um funcionario na base de dados
     *
     * @param funcionario
     * @return
     */
    @Override
    public Funcionario persistir(Funcionario funcionario) {
        logger.info("Persistindo Funcionario: {}", funcionario);
        return this.repository.save(funcionario);
    }

    /**
     * Busca e retorna funcionario dado cpf
     *
     * @param cpf
     * @return
     */
    @Override
    public Optional<Funcionario> buscarPorCpf(String cpf) {
        logger.info("Buscar Funcionario por cpf: {}", cpf);
        return Optional.ofNullable(this.repository.findByCpf(cpf));
    }

    /**
     * Busca e retorna funcionario dado email
     *
     * @param email
     * @return
     */
    @Override
    public Optional<Funcionario> buscarPorEmail(String email) {
        logger.info("Buscando Funcionario por email: {}", email);
        return Optional.ofNullable(this.repository.findByEmail(email));
    }

    /**
     * Busca e retorna funcionario dado id
     *
     * @param id
     * @return
     */
    @Override
    public Optional<Funcionario> buscarPorId(Long id) {
        logger.info("Buscando funcionário pelo IDl {}", id);
        return Optional.ofNullable(this.repository.getOne(id));
    }
}
