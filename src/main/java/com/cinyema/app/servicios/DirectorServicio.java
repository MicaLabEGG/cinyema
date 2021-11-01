package com.cinyema.app.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cinyema.app.entidades.Director;
import com.cinyema.app.enumeraciones.Pais;
import com.cinyema.app.repositorios.DirectorRepositorio;

@Service
public class DirectorServicio {

	@Autowired
	DirectorRepositorio directorRepositorio;

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Director crearDirector(String nombre, Pais pais) throws Exception {
		validar(nombre, pais);
		Director director = verificarDirectorPorNombre(nombre);
		director.setNombre(nombre);
		director.setPais(pais);
		return directorRepositorio.save(director);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Director modificarDirector(Long idDirector, String nombre, Pais pais) {
		Director director = verificarDirectorPorId(idDirector);
		director.setNombre(nombre);
		director.setPais(pais);
		return director;
	}

	public Director verificarDirectorPorId(Long idDirector) {
		Director director = directorRepositorio.getById(idDirector);
		return director;

	}

	@Transactional(readOnly = true)
	public List<Director> listarDirectores() {
		return directorRepositorio.findAll();
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void eliminarDirector(Long idDirector) {
		directorRepositorio.deleteById(idDirector);
	}

	public Director verificarDirectorPorNombre(String nombre) {
		Director director = directorRepositorio.buscarDirectorPorNombre(nombre);
		if (director == null) {
			director = directorRepositorio.save(new Director(nombre));
		}
		return director;
	}

	public void validar(String nombre, Pais pais) throws Exception {

		if (nombre.isEmpty() || nombre == null || nombre.contains("   ")) {
			throw new Exception("Error: Nombre de Director invalido");
		}

		if (pais == null) {
			throw new Exception("Error: Pais de Director invalido");
		}

	}

}
