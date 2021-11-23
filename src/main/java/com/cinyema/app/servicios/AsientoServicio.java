package com.cinyema.app.servicios;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.cinyema.app.entidades.Asiento;
import com.cinyema.app.entidades.Pelicula;
import com.cinyema.app.repositorios.AsientoRepositorio;
import com.cinyema.app.repositorios.TicketRepositorio;

@Service
public class AsientoServicio implements ServicioBase<Asiento> {
	
	@Autowired
	private AsientoRepositorio asientoRepositorio;
	
	@Autowired
	private TicketRepositorio ticketRepositorio;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Asiento registrar(Asiento asiento) throws Exception {
		validar(asiento);
		return asientoRepositorio.save(asiento);
	}
	
	@Transactional
	public Asiento registrarVacio() {
		return new Asiento();
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Asiento editar(Asiento asiento) throws Exception{
		validar(asiento);
		Optional<Asiento> respuesta = asientoRepositorio.findById(asiento.getIdAsiento());
		if(respuesta.isPresent()) {
			Asiento asiento2 = respuesta.get();
			if(asiento.getIdAsiento().equals(asiento2.getIdAsiento())) {
				return asientoRepositorio.save(asiento2);
			}else {
				throw new Exception("No se puede realizar la modificación");
			}
		}else {
			throw new Exception("No se encontró el asiento solicitado");
		}	
	}
	
	@Transactional(readOnly = true)
	public List<Asiento> listar(Long idFuncion){
		List<Asiento> asiLibres = new ArrayList<Asiento>();
		List<Asiento> listaAsientos = asientoRepositorio.findAll();
		Collections.sort(listaAsientos, (o1, o2) -> o1.getNumeroDeAsiento().compareTo(o2.getNumeroDeAsiento()));
		List<Long> ocupados = ticketRepositorio.listaAsientosOcupados(idFuncion);
		for (Asiento asiento1 : listaAsientos) {
			for (long long1 : ocupados) {
				if (asiento1.getIdAsiento() == long1) {
					asiento1.setLibre(false);
				}
			}
		}
		
		for (Asiento asiento : listaAsientos) {
			if(asiento.getLibre()==true) {
				asiLibres.add(asiento);
			}
		}
		
		return asiLibres ;
	}
	
	public long totalAsiento() throws Exception {
		return asientoRepositorio.count();
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Asiento obtenerPorId(Long idAsiento) {
		return asientoRepositorio.getById(idAsiento);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Asiento ocuparAsiento(Long id) throws Exception {
		Optional<Asiento> asiento = asientoRepositorio.findById(id);
		if (asiento.isPresent()) {
			Asiento asientoPresente = asiento.get();
			asientoPresente.setLibre(false);
			return asientoRepositorio.save(asientoPresente);
		} else {
			throw new Exception("No se encontro el asiento solicitado");
		}
	}

	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Asiento liberarAsiento(Long id) throws Exception {
		Optional<Asiento> asiento = asientoRepositorio.findById(id);
		if (asiento.isPresent()) {
			Asiento asientoPresente = asiento.get();
			asientoPresente.setLibre(true);
			return asientoRepositorio.save(asientoPresente);
		} else {
			throw new Exception("No se encontró el asiento solicitado");
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void eliminar(Long idAsiento) throws Exception {
		asientoRepositorio.deleteById(idAsiento);
	}
	
	public void validar(Asiento asiento) throws Exception {		
		
		if (asiento.getNumeroDeAsiento() == null || asiento.getNumeroDeAsiento().isEmpty() || asiento.getNumeroDeAsiento().contains("  ")) {
			throw new Exception("*El número de asiento es inválido");
		}
		
		if (asiento.getLibre() == null) {
			throw new Exception("*Error en la disponibilidad del asiento");
		}
		
	}

	@Override
	public List<Asiento> listar() throws Exception {
		List<Asiento> listaAsientos = asientoRepositorio.findAll();
		return listaAsientos;
	}	
}
