package br.com.hibejix.pontointeligente.api.services;

import br.com.hibejix.pontointeligente.api.entities.Lancamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

/**
 * @author msalvador
 * @version $Revision: $<br/>
 *          $Id: $
 * @since 25/03/2018 12:34
 */
public interface LancamentoService {

    /**
     * @param funcionarioId
     * @param pageRequest
     * @return Page<Lancamento>
     */
    Page<Lancamento> buscarPorFuncionarioId(Long funcionarioId, PageRequest pageRequest);

    /**
     * Busca um lancamento por id.
     *
     * @param id
     * @return Optional<Lancamento>
     */
    Optional<Lancamento> buscarPorId(Long id);

    /**
     * Adiciona um lancamento a base de dados.
     *
     * @param lancamento
     * @return Lancamento
     */
    Lancamento persistir(Lancamento lancamento);

    /**
     * Remove um lancamento da base de dados.
     *
     * @param id
     */
    void remover(Long id);

}
