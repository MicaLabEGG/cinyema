package com.cinyema.app.servicios;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.cinyema.app.entidades.Director;
import com.cinyema.app.repositorios.DirectorRepositorio;

@Service
public class DirectorServicio implements ServicioBase<Director> {

	@Autowired
	private DirectorRepositorio directorRepositorio;
	
	@Autowired
	SalaServicio salaServicio;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Director registrar(Director director) throws Exception {
		validar(director);
		return directorRepositorio.save(director);
	}

	@Transactional
	public Director registrarVacio() {
		return new Director();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Director editar(Director director) throws Exception {
		validar(director);
		return directorRepositorio.save(director);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Director> listar() {
		return directorRepositorio.findAll();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Director obtenerPorId(Long idDirector) {
		return directorRepositorio.getById(idDirector);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Director obtenerDirectorPorNombre(String nombre) {
		Director director = directorRepositorio.buscarDirectorPorNombre(nombre);
		return director = (director == null) ? directorRepositorio.save(new Director(nombre)) : director;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void eliminar(Long idDirector) {
		directorRepositorio.deleteById(idDirector);
	}
	
	public Long totalDirector() throws Exception{
		return directorRepositorio.count();
	}

	@Transactional(readOnly = true)
	public Long cantidadDirectores() {
		return directorRepositorio.count();
	}

	public void validar(Director director) throws Exception {
		if (director.getNombre().isEmpty() || StringUtils.isBlank (director.getNombre()) ) {
			throw new Exception("Nombre completo inválido");
		}
		if (director.getPais() == null) {
			throw new Exception("País inválido");
		}

	}

}
