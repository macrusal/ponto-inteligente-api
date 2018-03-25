package br.com.hibejix.pontointeligente.api.services.impl;

import br.com.hibejix.pontointeligente.api.entities.Lancamento;
import br.com.hibejix.pontointeligente.api.repositories.LancamentoRepository;
import br.com.hibejix.pontointeligente.api.services.LancamentoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author msalvador
 * @version $Revision: $<br/>
 *          $Id: $
 * @since 25/03/2018 12:40
 */
@Service
public class LancamentoServiceImpl implements LancamentoService {

    private static final Logger logger = LoggerFactory.getLogger(Lancamento.class);

    @Autowired
    private LancamentoRepository repository;

    /**
     * @param funcionarioId
     * @param pageRequest
     * @return Page<Lancamento>
     */
    @Override
    public Page<Lancamento> buscarPorFuncionarioId(Long funcionarioId, PageRequest pageRequest) {
        logger.info("Buscando lancamentos para o funcionario id{}", funcionarioId);
        return this.repository.findByFuncionarioId(funcionarioId, pageRequest);
    }

    /**
     * Busca um lancamento por id.
     *
     * @param id
     * @return Optional<Lancamento>
     */
    @Override
    public Optional<Lancamento> buscarPorId(Long id) {
        logger.info("Buscando um lancamento pelo id{}", id);
        return Optional.ofNullable(this.repository.getOne(id));
    }

    /**
     * Adiciona um lancamento a base de dados.
     *
     * @param lancamento
     * @return Lancamento
     */
    @Override
    public Lancamento persistir(Lancamento lancamento) {
        logger.info("Persistindo um lancamento{}", lancamento);
        return this.repository.save(lancamento);
    }

    /**
     * Remove um lancamento da base de dados.
     *
     * @param id
     */
    @Override
    public void remover(Long id) {
        logger.info("Removendo o lancamento de ID{}", id);
        Lancamento lancamento = this.repository.getOne(id);
        this.repository.delete(lancamento);
    }
}
