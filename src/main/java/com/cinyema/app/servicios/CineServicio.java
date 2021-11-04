package com.cinyema.app.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cinyema.app.entidades.Cine;
import com.cinyema.app.repositorios.CineRepositorio;

@Service
public class CineServicio {
	
	@Autowired
	private CineRepositorio cineRepositorio;
	
	@Transactional
	public Cine crearCine(Cine cine) throws Exception{
		
		validar(cine);
		
		return cineRepositorio.save(cine);
		
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
