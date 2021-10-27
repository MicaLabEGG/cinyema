package com.cinyema.app.servicios;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cinyema.app.entidades.Actor;
import com.cinyema.app.enumeraciones.Pais;
import com.cinyema.app.repositorios.ActorRepositorio;

@Service
public class ActorServicio {
	
	@Autowired
	private ActorRepositorio actorRepositorio;
	
	@Transactional
	public Actor registrarActor(String nombreCompleto, Pais pais) throws Exception{
		
		validar(nombreCompleto, pais);
		
		Actor actor = new Actor();
		actor.setNombreCompleto(nombreCompleto);
		actor.setPais(pais);
		return actorRepositorio.save(actor);
		
	}
	
	@Transactional
	public Actor modificarActor(Long id, String nombreCompleto, Pais pais) throws Exception {
		    
		    validar(nombreCompleto, pais);
		    
		    Actor actor = obtenerActor(id);
			actor.setNombreCompleto(nombreCompleto);
			actor.setPais(pais);
	        return actorRepositorio.save(actor);
	}
	
	@Transactional
	public List<Actor> buscarActores() {
		return actorRepositorio.findAll();
	}
	
	@Transactional
	public Actor obtenerActor(Long id) throws Exception{
		Optional<Actor> result = actorRepositorio.findById(id);
	       
	    if(result.isEmpty()) {
	    	throw new Exception("No se encontro");
	    }else {
		Actor actor = result.get();
		return actor;
	}
	}
	
	public Actor obtenerActorPorNombre(String nombreCompleto) throws Exception {
		Optional<Actor> result = actorRepositorio.buscarPorNombre(nombreCompleto);
	       
	    if(result.isEmpty()) {
	    	throw new Exception("No se encontro");
	    }else {
		Actor actor  = result.get();
		return actor;
	}
}
	
	
	public void validar(String nombreCompleto, Pais pais) throws Exception {

		if (nombreCompleto == null || nombreCompleto.isEmpty() || nombreCompleto.contains("  ")) {
			throw new Exception();
		}
		
		if (pais == null ){
			throw new Exception();
		}

}
}
