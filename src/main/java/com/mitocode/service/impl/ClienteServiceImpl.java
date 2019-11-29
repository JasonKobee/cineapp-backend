package com.mitocode.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mitocode.model.Cliente;
import com.mitocode.model.Usuario;
import com.mitocode.repo.IClienteRepo;
import com.mitocode.service.IClienteService;

@Service
public class ClienteServiceImpl implements IClienteService {

	@Autowired
	private IClienteRepo repo;
	
	@Value("${mitocine.default-rol}")
	private Integer DEFAULT_ROL;

	@Transactional
	@Override
	public Cliente registrar(Cliente obj) {
		Usuario u = new Usuario();
		u.setCliente(obj);
		u.setNombre(obj.getNombres());
		u.setEstado(true);
		u.setClave("");
		
		obj.setUsuario(u);		
		repo.save(obj);
		if(obj.getFoto().length > 0) {
			repo.modificarFoto(obj.getIdCliente(), obj.getFoto());			
		}
		return obj;
	}

	@Transactional
	@Override
	public Cliente modificar(Cliente obj) {
		if(obj.getFoto().length > 0) {
			repo.modificarFoto(obj.getIdCliente(), obj.getFoto());			
		}
		return repo.save(obj);
	}

	@Override
	public List<Cliente> listar() {
		return repo.findAll();
	}

	@Override
	public Cliente listarPorId(Integer id) {
		Optional<Cliente> op = repo.findById(id);
		return op.isPresent() ? op.get() : new Cliente();
	}

	@Override
	public void eliminar(Integer id) {
		repo.deleteById(id);
	}

	@Override
	public Page<Cliente> listarPageable(Pageable pageable) {
		return repo.findAll(pageable);
	}

}
