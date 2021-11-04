package com.cinyema.app.servicios;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.cinyema.app.entidades.Asiento;
import com.cinyema.app.repositorios.AsientoRepositorio;

public class AsientoServicio {
	
	@Autowired
	AsientoRepositorio AsientoRep;
	
	public void crearAsiento(Asiento asiento) throws Exception {
		
		validar(asiento);
		
		AsientoRep.save(asiento);
	}
	
	

	
	public void validar(Asiento asiento) throws Exception {

		if (asiento.getSala() == null) {
			throw new Exception("*El asiento no pertenece a ninguna sala");
		}
		
		if (asiento.getNumeroDeAsiento() == null || asiento.getNumeroDeAsiento().isEmpty() || asiento.getNumeroDeAsiento().contains("  ")) {
			throw new Exception("*El número de asiento es inválido");
		}
		
		if (asiento.getLibre() == null) {
			throw new Exception("*Error en la disponibilidad del asiento");
		}
		
	}	
}
