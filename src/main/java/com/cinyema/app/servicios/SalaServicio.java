package com.cinyema.app.servicios;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cinyema.app.entidades.Asiento;
import com.cinyema.app.entidades.Funcion;
import com.cinyema.app.entidades.Sala;
import com.cinyema.app.entidades.Usuario;
import com.cinyema.app.repositorios.AsientoRepositorio;
import com.cinyema.app.repositorios.FuncionRepositorio;
import com.cinyema.app.repositorios.SalaRepositorio;

@Service
public class SalaServicio implements ServicioBase<Sala>{

	@Autowired
	SalaRepositorio salaRepositorio;
	
	@Autowired
	AsientoServicio asientoServicio;
	
	@Autowired
	AsientoRepositorio asientoRepositorio;
	
	@Autowired
	FuncionRepositorio funcionRepositorio;

	@Override
	@Transactional
	public Sala registrar(Sala sala) throws Exception {
		validar(sala);
		
		List<Asiento> asientos = new ArrayList<Asiento>();
		
		String nombreSala = sala.getNombreSala();	
		for(int i = 1; i < sala.getCantidadAsientos() + 1; i++) {
				
			Asiento asiento = new Asiento();
			asiento.setNumeroDeAsiento(nombreSala+" Butaca - " + i);
			asiento.setLibre(true);
			asientoServicio.registrar(asiento);
			System.out.println(i);
			asientos.add(asiento);	
		}
		
		Collections.sort(asientos, (o1, o2) -> o1.getNumeroDeAsiento().compareTo(o2.getNumeroDeAsiento()));
		sala.setAsientos(asientos);
		
		
		return salaRepositorio.save(sala);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Sala registrar2() throws Exception {
		Sala sala = new Sala();
		
		List<Asiento> asientos = new ArrayList<Asiento>();
		
		sala.setCantidadAsientos(10);
		//sala.setPelicula(null);
		
		for(int i = 1; i < sala.getCantidadAsientos() + 1; i++) {
				
			Asiento asiento = asientoServicio.registrarVacio();
			asiento.setNumeroDeAsiento("Butaca - " + i);
			asientos.add(asiento);	
		}

		sala.setAsientos(asientos);
		
		
		return salaRepositorio.save(sala);
	}
	
	
	@Transactional
	public Sala registrarVacio() {
		return new Sala();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Sala editar(Sala sala) throws Exception {
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
	public void eliminarPorEntidad(Sala sala) {
		salaRepositorio.delete(sala);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public List<Asiento> obtenerAsientosLibres(Sala sala) {
		List<Asiento> asiLibres = new ArrayList<Asiento>();
		List<Funcion> funcion = sala.getFunciones();
		for (Funcion funcion2 : funcion) {
			List <Asiento> todos = asientoServicio.listar(funcion2.getIdFuncion());
			for (Asiento asiento : todos) {
				if(asiento.getLibre() == true) {
					asiLibres.add(asiento);
		        }
		    }
		}
		return asiLibres;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public List<String> obtenerFuncionesPorPeliculaId(Long idPelicula) {
		List<Funcion> funciones = funcionRepositorio.obtenerFuncionesPorPeliculaId(idPelicula);
		List<String> fechas = new ArrayList();
		for (Funcion funcion : funciones) {
			fechas.add(funcion.getFecha());
		}
		
		Set<String> setCombined = new HashSet<>(fechas);
		List<String> fechasfinal = new ArrayList<>(setCombined);
		
		return fechasfinal;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public List<Funcion> obtenerFuncionesPorPeliculaIdAndFecha(Long idPelicula, String fecha) {
		List<Funcion> funciones = funcionRepositorio.obtenerFuncionesPorPeliculaAndFecha(idPelicula, fecha);
		return funciones;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Funcion obtenerFuncionesPorPeliculaIdAndFechaAndHorario(Long idPelicula, String fecha, String horario) {
		Funcion funcion = funcionRepositorio.obtenerFuncionesPorPeliculaAndFechaAndHorario(idPelicula, fecha, horario);
		return funcion;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void eliminar(Long id) {
		salaRepositorio.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Sala> listar() {
		return salaRepositorio.findAll();
	}

	public long totalSala() throws Exception {
		return salaRepositorio.count();
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Sala obtenerPorId(Long id) {
		return salaRepositorio.getById(id);
	}
	
//	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
//	public Sala obtenerSalaPorTituloPelicula(String titulo) {
//		return salaRepositorio.buscarSalaPorPelicula(titulo);
//	}

	public void validar(Sala sala) throws Exception {
//		if (sala.getAsientos() == null) {
//			throw new Exception("El asiento es inexistente");
//		}
		
		if(sala.getCantidadAsientos() == null) {
			throw new Exception("La cantidad de asientos es invalida");
		}
		
//		if (sala.getPelicula() == null) {
//			throw new Exception("La Pelicula es inexistente");
//		}
	}
}