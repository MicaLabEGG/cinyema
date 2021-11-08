package com.cinyema.app.servicios;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.cinyema.app.entidades.Asiento;
import com.cinyema.app.repositorios.AsientoRepositorio;

@Service
public class AsientoServicio implements ServicioBase<Asiento> {
	
	@Autowired
	private AsientoRepositorio asientoRepositorio;
	
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
	
	@Override
	@Transactional(readOnly = true)
	public List<Asiento> listar(){
		return asientoRepositorio.findAll();
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Asiento obtenerPorId(Long idAsiento) {
		return asientoRepositorio.getById(idAsiento);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void eliminar(Long idAsiento) throws Exception {
		asientoRepositorio.deleteById(idAsiento);
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
