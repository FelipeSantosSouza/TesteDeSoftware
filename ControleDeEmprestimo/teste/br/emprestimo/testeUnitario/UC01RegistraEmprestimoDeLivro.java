package br.emprestimo.testeUnitario;
import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import br.emprestimo.modelo.Emprestimo;
import br.emprestimo.modelo.Livro;
import br.emprestimo.modelo.Usuario;
import br.emprestimo.servico.ServicoEmprestimo;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class UC01RegistraEmprestimoDeLivro {
	static private Livro livro;
	static private Usuario usuario;
	static private ServicoEmprestimo servico;
	static private Emprestimo emprestimo;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		//cenario
		livro = new Livro();
		livro.setIsbn("121212");
		livro.setTitulo("Engenharia de Software");
		livro.setAutor("Pressman");
		usuario = new Usuario();
		usuario.setRa("11111");
		usuario.setNome("Jose da Silva");
		servico = new ServicoEmprestimo();
		emprestimo = new Emprestimo();
	}
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	@Test
	public void CT01UC01FB_registrar_emprestimo_com_sucesso() {
		assertNotNull(servico.empresta(livro, usuario));
	}
	@Test(expected=RuntimeException.class)
	public void CT02UC01FB_registrar_emprestimo_com_dados_invalidos() {
		servico.empresta(null, usuario);
	}
	
	@Test
	public void CT03UC01FB_registrar_emprestimo_get_livro() {
		assertEquals(livro, emprestimo.getLivro());
	}
	
	@Test
	public void CT03UC01FB_registrar_emprestimo_get_usuario() {
		assertEquals(usuario, emprestimo.getUsuario());
	}
	@Test
	public void CT03UC01FB_registrar_emprestimo_com_dados_invalidos(){
		try{
			servico.empresta(null, usuario);
			fail ("deveria lançar uma exceção");
		}catch(RuntimeException e){
			assertEquals("Dados inválidos.", e.getMessage());
		}
	}
	@Test
	public void CT04UC01FB_registrar_emprestimo_com_sucesso_validacao_da_data() {
		//acao
		DateTimeFormatter fmt = DateTimeFormat.forPattern("dd/MM/YYYY");
		String dataEsperada = new DateTime().plusDays(8).toString(fmt);
		emprestimo = servico.empresta(livro, usuario);
		String dataObtida = emprestimo.getDataDevolucao();
		//verificacao
	    assertTrue(dataEsperada.equals(dataObtida));
	}
	
	@Test
	public void CT04UC01FB_registrar_emprestimo_com_sucesso_validacao_da_data_invalidia() {		//acao
		assertTrue(emprestimo.validaData("09/10/2000"));
	}
	@Test
	public void CT05UC01FB_registrar_emprestimo_com_data_invalida() {
		assertFalse(emprestimo.validaData("29/30/2000"));
	}
	@Test
	public void CT05UC01FB_registrar_emprestimo_usuario_RA() {
		assertEquals("11111",usuario.getRa());
	}
	@Test
	public void CT05UC01FB_registrar_emprestimo_usuario_Nome() {
		assertEquals("Jose da Silva",usuario.getNome());
	}
	@Test
	public void CT05UC01FB_registrar_emprestimo_usuario_equals_null() {
		assertFalse(usuario.equals(null));
	}
	
	@Test
	public void CT05UC01FB_registrar_emprestimo_usuario_equals_nome() {
		Usuario u = new Usuario();
		assertFalse(usuario.equals(u));
	}
	@Test
	public void CT05UC01FB_registrar_emprestimo_usuario_equals_nome_dif() {
		Usuario u = new Usuario();
		u.setNome(null);
		Usuario u2 = new Usuario();
		u2.setNome("fjdhsalf");
		assertFalse(u.equals(u2));
	}
	@Test
	public void CT05UC01FB_registrar_emprestimo_usuario_equals_nome_dif2() {
		Usuario u = new Usuario();
		u.setNome(null);
		Usuario u2 = new Usuario();
		u2.setRa("1111111");
		assertFalse(u.equals(u2));
	}
	@Test
	public void CT05UC01FB_registrar_emprestimo_usuario_equals_diferente() {
		Usuario u = new Usuario();
		assertFalse(u.equals(usuario));
	}
	@Test
	public void CT05UC01FB_registrar_emprestimo_usuario_equals_igual() {
		assertTrue(usuario.equals(usuario));
	}
	
}
