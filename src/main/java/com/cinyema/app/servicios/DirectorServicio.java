package com.cinyema.app.servicios;

import java.util.List;
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
	@Transactional
	public Director obtenerPorId(Long idDirector) {
		return directorRepositorio.getById(idDirector);
	}

	@Transactional
	public Director obtenerDirectorPorNombre(String nombre) {
		Director director = directorRepositorio.buscarDirectorPorNombre(nombre);
		return director = (director == null) ? directorRepositorio.save(new Director(nombre)) : director;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void eliminarPorId(Long idDirector) {
		directorRepositorio.deleteById(idDirector);
	}

	public void validar(Director director) throws Exception {
		if (director.getNombre().isEmpty() || director.getNombre().isBlank()) {
			throw new Exception("Nombre completo inválido");
		}
		if (director.getPais() == null) {
			throw new Exception("País inválido");
		}

	}

}
