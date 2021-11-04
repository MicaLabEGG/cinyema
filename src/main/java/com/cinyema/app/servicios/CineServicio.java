package com.cinyema.app.servicios;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.cinyema.app.entidades.Cine;
import com.cinyema.app.entidades.Sala;
import com.cinyema.app.repositorios.CineRepositorio;

@Service
public class CineServicio {
	
	@Autowired
	private CineRepositorio cineRepositorio;
	
	//@Autowired
	//private SalaRepositorio salaRepositorio;
	
	@Transactional
	public Cine crear(Cine cine) throws Exception{
		
		validar(cine);
		
		return cineRepositorio.save(cine);
	}
	
	//Arme el modificar segun la nueva manera, que recibe el cine completo, por la vista con th:object para hacer los cambios.
	//No se, si esta bien ??
	@Transactional
	public Cine modificar(Cine cine) throws Exception{
		
		//@SuppressWarnings({ "deprecation", "unchecked" })
		//List<Sala> salas = (List<Sala>) salaRepositorio.getOne(cine.getSalas().get(0).getIdSala());
		
		validar(cine);
		
		Optional<Cine> respuesta = cineRepositorio.findById(cine.getIdCine());
		
		if (respuesta.isPresent()) {
			Cine cine1 = respuesta.get();
			if (cine1.getIdCine().equals(cine.getIdCine())) {
				return cineRepositorio.save(cine1);
			}else {
				throw new Exception("*No puede realizar la modificacion");
			}		
		}else {
			throw new Exception("No se encontró el Cine solicitado");
		}
	}	
	
	@Transactional
	public List<Cine> listar() {	
		return cineRepositorio.findAll();
	}
	
	@Transactional
	public List<Cine> listarCinePorNombre(String nombre) throws Exception {
        List<Cine> cines = cineRepositorio.buscarCinePorNombre(nombre);
		
		if(!cines.isEmpty()) {
			return cines;
		}else {
			throw new Exception("*No se encontró el nombre del Cine");
		}	
	}
	
	@Transactional
	public Optional<Cine> buscarCinePorId(Long idCine) {
		return cineRepositorio.findById(idCine);
	}
	
	@Transactional
	public void eliminarCine(Cine cine) {
		cineRepositorio.delete(cine);
	}
	
	@Transactional
	public void eliminarCinePorId(Long idCine) throws Exception {
		cineRepositorio.deleteById(idCine);
	}
	
	public void validar(Cine cine) throws Exception {

		if (cine.getSalas() == null) {
			throw new Exception("*Salas de cine es inválido");
		}
		
		if (cine.getNombre() == null || cine.getNombre().isEmpty() || cine.getNombre().contains("  ")) {
			throw new Exception("*Nombre de cine es inválido");
		}
		
		if (cine.getDireccion() == null || cine.getDireccion().isEmpty() || cine.getDireccion().contains("  ")) {
			throw new Exception("*Direccion de cine es inválido");
		}
		
		if (cine.getMail() == null || cine.getMail().isEmpty() || cine.getMail().contains("  ")) {
			throw new Exception("*Mail de cine es inválido");
		}
		
		if (cine.getTelefono() == null || cine.getTelefono().isEmpty() || cine.getNombre().contains("  ")) {
			throw new Exception("*Telefono de cine es inválido");
		}
	}	

}
