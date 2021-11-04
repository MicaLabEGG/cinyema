package com.cinyema.app.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cinyema.app.entidades.Sala;
import com.cinyema.app.repositorios.SalaRepositorio;

@Service
public class SalaServicio {

	@Autowired
	SalaRepositorio salaRepositorio;

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Sala crearSala(Sala sala) throws Exception {

		validar(sala);

		return salaRepositorio.save(sala);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Sala modificarSala(Sala sala) throws Exception {
		validar(sala);

		Optional<Sala> respuesta = salaRepositorio.findById(sala.getIdSala());
		if (respuesta.isPresent()) {
			Sala sala1 = respuesta.get();
			if (sala1.getIdSala().equals(sala.getIdSala())) {
				return salaRepositorio.save(sala1);
			} else {
				throw new Exception("No se puede modificar la sala");
			}
		} else {
			throw new Exception("No se encontro la sala solicitada");
		}

	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void eliminarSala(Sala sala) {
		salaRepositorio.delete(sala);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void elminarSalaPorId(Long id) {
		salaRepositorio.deleteById(id);
	}

	@Transactional(readOnly = true)
	public List<Sala> listarSalas() {

		return salaRepositorio.findAll();
	}

	@Transactional(readOnly = true)
	public Optional<Sala> buscarSalaPorId(Long id) {
		return salaRepositorio.findById(id);
	}

	public void validar(Sala sala) throws Exception {

		if (sala.getAsientos() == null) {
			throw new Exception("El asiento es inexistente");
		}

		if (sala.getPelicula() == null) {
			throw new Exception("La Pelicula es inexistente");
		}

	}

}
