package com.mitocode.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mitocode.exception.ModeloNotFoundException;
import com.mitocode.model.Pelicula;
import com.mitocode.service.IPeliculaService;


@RestController
@RequestMapping("/peliculas")
public class PeliculaController {

	@Autowired
	private IPeliculaService service;

	/*@PostMapping
	public ResponseEntity<Pelicula> registrar(@Valid @RequestBody Pelicula obj) {
		Pelicula pel = service.registrar(obj);
		return new ResponseEntity<Pelicula>(pel, HttpStatus.CREATED);
	}*/
	
	@PostMapping
	public ResponseEntity<Object> registrar(@Valid @RequestBody Pelicula obj) {
		Pelicula pel = service.registrar(obj);
		
		// localhost:8080/peliculas/2
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(pel.getIdPelicula()).toUri();
		return ResponseEntity.created(location).build();
	}

	@PutMapping
	public ResponseEntity<Pelicula> modificar(@Valid @RequestBody Pelicula obj) {
		Pelicula pel = service.modificar(obj);
		return new ResponseEntity<Pelicula>(pel, HttpStatus.OK);
	}

	/*@GetMapping("/{id}")
	public ResponseEntity<Pelicula> listarPorId(@PathVariable("id") Integer id) {
		Pelicula pel = service.listarPorId(id);
		if(pel.getIdPelicula() == null) {
			throw new ModeloNotFoundException("ID no existe: " + id);
		}
		return new ResponseEntity<Pelicula>(pel, HttpStatus.OK); 
	}*/
	
	@GetMapping(value = "/{id}")
	public Resource<Pelicula> listarPorId(@PathVariable("id") Integer id){
		
		Pelicula pel = service.listarPorId(id);
		if(pel.getIdPelicula() == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO : " + id);
		}
		
		Resource<Pelicula> resource = new Resource<Pelicula>(pel);
		// /peliculas/{4}
		ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).listarPorId(id));
		resource.add(linkTo.withRel("pelicula-resource"));
		
		return resource;
	}

	@PreAuthorize("@restAuthService.hasAccess('listar')")
	//@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping
	public ResponseEntity<List<Pelicula>> listar() {
		List<Pelicula> lista = service.listar();
		return new ResponseEntity<List<Pelicula>>(lista, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> eliminar(@PathVariable("id") Integer id) {
		service.eliminar(id);
		return new ResponseEntity<Object>(HttpStatus.OK);
	}
}
