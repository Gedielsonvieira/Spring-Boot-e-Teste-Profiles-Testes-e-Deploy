package br.com.alura.forum.repository;

import br.com.alura.forum.modelo.Curso;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CursoRepositoryTest {
    @Autowired
    private CursoRepository cursoRepository;

    @Test
    public void deveriaBuscarUmCursoAoCarregarPeloNome() {
        Curso nomeCurso = cursoRepository.findByNome("HTML 5");
        Assert.assertNotNull(nomeCurso);
        Assert.assertEquals("HTML 5", nomeCurso.getNome());
    }

    @Test
    public void naoDeveriaBuscarUmCursoCujoNomeNaoEstejaCadastrado() {
        Curso nomeCurso = cursoRepository.findByNome("JPA");
        Assert.assertNull(nomeCurso);
    }
}