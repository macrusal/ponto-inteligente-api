package br.com.hibejix.pontointeligente.api.services;

import br.com.hibejix.pontointeligente.api.entities.Funcionario;
import br.com.hibejix.pontointeligente.api.repositories.FuncionarioRepository;
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
 * @since 25/03/2018 10:50
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class FuncionarioServiceTest {

    @MockBean
    private FuncionarioRepository repository;

    @Autowired
    private FuncionarioService service;

    @Before
    public void setup() throws Exception {
        BDDMockito.given(this.repository.save(Mockito.any(Funcionario.class))).willReturn(new Funcionario());
        BDDMockito.given(this.repository.findByCpf((Mockito.anyString()))).willReturn(new Funcionario());
        BDDMockito.given(this.repository.findByEmail(Mockito.anyString())).willReturn(new Funcionario());
        BDDMockito.given(this.repository.getOne(Mockito.anyLong())).willReturn(new Funcionario());
    }

    @Test
    public void testPersistirFuncionario() {
        Funcionario funcionario = this.service.persistir(new Funcionario());
        assertNotNull(funcionario);
    }

    @Test
    public void testBuscarFuncionarioPorCpf() {
        Optional<Funcionario> funcionario = this.service.buscarPorCpf("12332145690");
        assertTrue(funcionario.isPresent());
    }

    @Test
    public void testBuscarFuncionarioPorEmail() {
        Optional<Funcionario> funcionario = this.service.buscarPorEmail("email@funcionario.com.br");
        assertTrue(funcionario.isPresent());
    }

    @Test
    public void testBuscarFuncionarioPorId() {
        Optional<Funcionario> funcionario = this.service.buscarPorId(1L);
        assertTrue(funcionario.isPresent());
    }
}
