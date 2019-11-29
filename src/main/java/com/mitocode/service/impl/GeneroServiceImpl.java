package com.mitocode.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mitocode.model.Genero;
import com.mitocode.repo.IGeneroRepo;
import com.mitocode.service.IGeneroService;

@Service
public class GeneroServiceImpl implements IGeneroService{

	@Autowired
	private IGeneroRepo repo;	
	
	@Override
	public Genero registrar(Genero g) {
		return repo.save(g);
	}

	@Override
	public Genero modificar(Genero g) {
		return repo.save(g);
	}

	@Override
	public List<Genero> listar() {
		return repo.findAll();
	}

	@Override
	public Genero listarPorId(Integer id) {
		Optional<Genero> op = repo.findById(id);
		return op.isPresent() ? op.get() : new Genero();
	}

	@Override
	public void eliminar(Integer id) {
		repo.deleteById(id);
	}

	@Override
	public Page<Genero> listarPageable(Pageable pageable) {
		return repo.findAll(pageable);
	}

}
