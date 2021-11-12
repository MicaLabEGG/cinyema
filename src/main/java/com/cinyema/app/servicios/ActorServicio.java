package com.cinyema.app.servicios;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.cinyema.app.entidades.Actor;
import com.cinyema.app.repositorios.ActorRepositorio;

@Service
public class ActorServicio implements ServicioBase<Actor> {

	@Autowired
	private ActorRepositorio actorRepositorio;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Actor registrar(Actor actor) throws Exception {
		validar(actor);
		return actorRepositorio.save(actor);
	}

	@Transactional
	public Actor registrarVacio() {
		return new Actor();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Actor editar(Actor actor) throws Exception {
		validar(actor);
		return actorRepositorio.save(actor);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Actor> listar() {
		return actorRepositorio.findAll();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Actor obtenerPorId(Long id) throws Exception {
		Optional<Actor> result = actorRepositorio.findById(id);
		if (!result.isPresent()) {
			throw new Exception("No se encontró");
		} else {
			return result.get();
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Actor obtenerActorPorNombre(String nombreCompleto) throws Exception {
		Optional<Actor> result = actorRepositorio.buscarPorNombre(nombreCompleto);
		if (!result.isPresent()) {
			throw new Exception("No se encontró");
		} else {
			return result.get();
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Long obtenerCantidadActores() throws Exception {
		return actorRepositorio.buscarCantidadActores();
	}
    
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void eliminar(Long id) throws Exception {
		actorRepositorio.deleteById(id);
		
	}

	public void validar(Actor actor) throws Exception {
		if (actor.getNombreCompleto() == null || StringUtils.isBlank (actor.getNombreCompleto())) {
			throw new Exception("Nombre completo inválido");
		}
		if (actor.getPais() == null) {
			throw new Exception("País inválido");
		}

	}

}
