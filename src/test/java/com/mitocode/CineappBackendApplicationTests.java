package com.mitocode;

import static org.junit.Assert.assertTrue;

import java.time.LocalDate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import com.mitocode.model.Cliente;
import com.mitocode.model.Usuario;
import com.mitocode.service.IUsuarioService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CineappBackendApplicationTests {

	@Autowired
	private IUsuarioService service;

	@Autowired
	private BCryptPasswordEncoder bcrypt;
		
	@Test
	public void crearUsuario() {
		Usuario us = new Usuario();
		us.setNombre("codex");
		us.setClave(bcrypt.encode("123"));
		us.setEstado(true);

		Cliente c = new Cliente();
		c.setNombres("JAIME");
		c.setApellidos("DELGADO");
		c.setDni("72301306");
		c.setFechaNac(LocalDate.of(1991, 1, 21));
		c.setUsuario(us);
		us.setCliente(c);

		Usuario retorno = service.registrarTransaccional(us);

		assertTrue(retorno.getClave().equalsIgnoreCase(us.getClave()));
	}

}
