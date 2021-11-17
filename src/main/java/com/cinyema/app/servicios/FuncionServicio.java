package com.cinyema.app.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cinyema.app.entidades.Actor;
import com.cinyema.app.entidades.Funcion;
import com.cinyema.app.repositorios.ActorRepositorio;
import com.cinyema.app.repositorios.FuncionRepositorio;

@Service
public class FuncionServicio implements ServicioBase<Funcion>{
	
	@Autowired
	private FuncionRepositorio funcionRepositorio;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Funcion registrar(Funcion funcion) throws Exception {
		//validar(funcion);
		return funcionRepositorio.save(funcion);
	}
	
	@Transactional
	public Funcion registrarVacio() {
		return new Funcion();
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Funcion editar(Funcion funcion) throws Exception {
		//validar(funcion);
		return funcionRepositorio.save(funcion);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Funcion> listar() {
		return funcionRepositorio.findAll();
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Funcion obtenerPorId(Long id) throws Exception {
		Optional<Funcion> result = funcionRepositorio.findById(id);
		if (!result.isPresent()) {
			throw new Exception("No se encontró");
		} else {
			return result.get();
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void eliminar(Long id) throws Exception {
		funcionRepositorio.deleteById(id);
			
	}
	
}
