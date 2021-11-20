package com.cinyema.app.servicios;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.cinyema.app.entidades.Cine;
import com.cinyema.app.repositorios.CineRepositorio;
import com.cinyema.app.repositorios.SalaRepositorio;

@Service
public class CineServicio implements ServicioBase<Cine>{
	
	@Autowired
	private CineRepositorio cineRepositorio;
	
	@Autowired
	private SalaRepositorio salaRepositorio;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Cine registrar(Cine cine) throws Exception{
		validar(cine);
		return cineRepositorio.save(cine);
	}
	
	@Transactional
	public Cine registrarVacio() {
		return new Cine();
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Cine editar(Cine cine) throws Exception{
		validar(cine);
		return cineRepositorio.save(cine);
	}	
	
	@Override
	@Transactional(readOnly = true)
	public List<Cine> listar() {	
		return cineRepositorio.findAll();
	}
	
	public long totalCine() throws Exception {
		return cineRepositorio.count();
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public List<Cine> listarCinePorNombre(String nombre) throws Exception {
        List<Cine> cines = cineRepositorio.buscarCinePorNombre(nombre);
		if(!cines.isEmpty()) {
			return cines;
		}else {
			throw new Exception("*No se encontró el nombre del Cine");
		}	
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Cine obtenerPorId(Long idCine) throws Exception {
		Optional<Cine> result = cineRepositorio.findById(idCine);
		if (!result.isPresent()) {
			throw new Exception("No se encontró");
		} else {
			return result.get();
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void eliminar(Long idCine) throws Exception {
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
		if (cine.getPrecio() == null ){
			throw new Exception("*Precio de cine es inválido");
		}
		
	}	

}
