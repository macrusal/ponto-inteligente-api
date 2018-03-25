package br.com.hibejix.pontointeligente.api.services;

import br.com.hibejix.pontointeligente.api.entities.Funcionario;

import java.util.Optional;

/**
 * @author msalvador
 * @version $Revision: $<br/>
 *          $Id: $
 * @since 25/03/2018 07:39
 */
public interface FuncionarioService {

    /**
     * Persiste um funcionario na base de dados
     *
     * @param funcionario
     * @return
     */
    Funcionario persistir(Funcionario funcionario);


    /**
     * Busca e retorna funcionario dado cpf
     *
     * @param cpf
     * @return Optional<Funcionario>
     */
    Optional<Funcionario> buscarPorCpf(String cpf);

    /**
     * Busca e retorna funcionario dado email
     *
     * @param email
     * @return Optional<Funcionario>
     */
    Optional<Funcionario> buscarPorEmail(String email);

    /**
     * Busca e retorna um funcionário por ID.
     *
     * @param id
     * @return Optional<Funcionario>
     */
    Optional<Funcionario> buscarPorId(Long id);
}
