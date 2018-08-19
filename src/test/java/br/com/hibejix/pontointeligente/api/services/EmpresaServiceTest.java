package br.com.hibejix.pontointeligente.api.services;

import br.com.hibejix.pontointeligente.api.entities.Empresa;
import br.com.hibejix.pontointeligente.api.repositories.EmpresaRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author msalvador
 * @version $Revision: $<br/>
 *          $Id: $
 * @since 25/03/2018 07:11
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class EmpresaServiceTest {

    @MockBean
    private EmpresaRepository repository;

    @Autowired
    private EmpresaService service;

    private static final String CNPJ = "51463645000100";

    @Before
    public void setup() throws Exception {
        BDDMockito.given(this.repository.findByCnpj(Mockito.anyString())).willReturn(new Empresa());
        BDDMockito.given(this.repository.save(Mockito.any(Empresa.class))).willReturn(new Empresa());
    }

    @Test
    public void testBuscarEmpresaPorCnpj() {
        Optional<Empresa> empresa = this.service.buscarPorCnpj(CNPJ);
        assertTrue(empresa.isPresent());
    }

    @Test
    public void testPersistirEmpresa() {
        Empresa empresa = this.service.persistir(new Empresa());
        assertNotNull(empresa);
    }
}
