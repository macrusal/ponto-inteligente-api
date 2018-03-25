package br.com.hibejix.pontointeligente.api.services;

import br.com.hibejix.pontointeligente.api.entities.Empresa;

import java.util.Optional;

/**
 * @author msalvador
 * @version $Revision: $<br/>
 *          $Id: $
 * @since 24/03/2018 21:54
 */
public interface EmpresaService {

    /**
     * Retorna uma empresa dado um cnpj
     *
     * @param cnpj
     * @return Optional<Empresa>
     */
    Optional<Empresa> buscarPorCnpj(String cnpj);

    /**
     * Cadastra uma nova empresa na base de dados
     *
     * @param empresa
     * @return
     */
    Empresa persistir(Empresa empresa);
}
