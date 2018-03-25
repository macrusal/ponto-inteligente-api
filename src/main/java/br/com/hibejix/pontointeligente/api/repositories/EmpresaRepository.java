package br.com.hibejix.pontointeligente.api.repositories;

import br.com.hibejix.pontointeligente.api.entities.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author msalvador
 * @version $Revision: $<br/>
 *          $Id: $
 * @since 23/03/2018 23:53
 */
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

    @Transactional(readOnly = true)
    Empresa findByCnpj(String senha);
}
