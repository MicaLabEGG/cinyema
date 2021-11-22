package com.cinyema.app.servicios;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.cinyema.app.entidades.Pelicula;
import com.cinyema.app.entidades.Sala;
import com.cinyema.app.enumeraciones.Formato;
import com.cinyema.app.enumeraciones.Genero;
import com.cinyema.app.repositorios.PeliculaRepositorio;
import com.cinyema.app.repositorios.SalaRepositorio;

@Service
public class PeliculaServicio implements ServicioBase<Pelicula>{

	@Autowired
	private PeliculaRepositorio repositorioPelicula;
	
	@Autowired
	private SalaRepositorio salaRepositorio;
	

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void registrar(Pelicula pelicula, MultipartFile archivo, String archivoVideo) throws Exception {
		
		if (repositorioPelicula.validarTituloPelicula(pelicula.getTitulo()) != null) {
			throw new Exception("Ya existe una película con el mismo título");
		}
		String fileName = StringUtils.cleanPath(archivo.getOriginalFilename());
		validar(pelicula, archivo, archivoVideo, fileName);
		pelicula.setImagen(Base64.getEncoder().encodeToString(archivo.getBytes()));
		pelicula.setTrailer(archivoVideo);
		pelicula.setAlta(true);
		repositorioPelicula.save(pelicula);
	}
	
	@Transactional
	public Pelicula registrarVacio() {
		return new Pelicula();
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void editar(Pelicula pelicula, MultipartFile archivo, String archivoVideo) throws Exception {
		String fileName = StringUtils.cleanPath(archivo.getOriginalFilename());
		validar(pelicula, archivo, archivoVideo, fileName);
		pelicula.setImagen(Base64.getEncoder().encodeToString(archivo.getBytes()));
		pelicula.setTrailer(archivoVideo);
		pelicula.setAlta(true);
		repositorioPelicula.save(pelicula);
	}

	@Transactional(readOnly = true)
	public List<Pelicula> listar() {
		return repositorioPelicula.findAll();
	}

	@Transactional(readOnly = true)
	public List<Pelicula> listarPeliculasActivas() {
		return repositorioPelicula.buscarPeliculasActivas();
	}

	@Transactional(readOnly = true)
	public List<Pelicula> obtenerPeliculaPorTitulo(String titulo) throws Exception {
		List<Pelicula> peliculas = repositorioPelicula.buscarPeliculaPorTitulo(titulo);
		if (!peliculas.isEmpty()) {
			return peliculas;
		} else {
			throw new Exception("No se encontró el título de la película");
		}
	}

	@Transactional(readOnly = true)
	public List<Pelicula> obtenerPeliculaPorGenero(Genero genero) throws Exception {
		System.err.println(genero);
		List<Pelicula> peliculas = repositorioPelicula.buscarPeliculaPorGenero(genero);
		if (!peliculas.isEmpty()) {
			return peliculas;
		} else {
			throw new Exception("No hay película de este genero");
		}
	}

	@Transactional(readOnly = true)
	public List<Pelicula> obtenerPeliculaPorFormato(Formato formato) throws Exception {
		List<Pelicula> peliculas = repositorioPelicula.buscarPeliculaPorFormato(formato);
		if (!peliculas.isEmpty()) {
			return peliculas;
		} else {
			throw new Exception("No hay película de este formato");
		}
	}
	
	public Sala obtenerSalaPorFuncion(Long idFuncion) throws Exception{
		Sala sala = salaRepositorio.buscarSalaPorIdFuncion(idFuncion);
		return sala;
	}
    
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Pelicula obtenerPorId(Long idPelicula) {
		return repositorioPelicula.getById(idPelicula);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Pelicula alta(Long idPelicula) throws Exception {
		Optional<Pelicula> pelicula = repositorioPelicula.findById(idPelicula);
		if (pelicula.isPresent()) {
			Pelicula peliculaPresente = pelicula.get();
			peliculaPresente.setAlta(true);
			return repositorioPelicula.save(peliculaPresente);
		} else {
			throw new Exception("No se encontró la película solicitada");
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Pelicula baja(Long id) throws Exception {
		Optional<Pelicula> pelicula = repositorioPelicula.findById(id);
		if (pelicula.isPresent()) {
			Pelicula peliculaPresente = pelicula.get();
			peliculaPresente.setAlta(false);
			return repositorioPelicula.save(peliculaPresente);
		} else {
			throw new Exception("No se encontro la película solicitada");
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void eliminarPorEntidad(Pelicula pelicula) {
		repositorioPelicula.delete(pelicula);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void eliminar(Long idPelicula) throws Exception {
		repositorioPelicula.deleteById(idPelicula);
	}
	
	public long cantidadPeliculaTotal() {
		return repositorioPelicula.cantidadTotal();
	}
	
	public int cantidadPeliculaAlta() {
		if(repositorioPelicula.cantidadAlta() == 0 ) {
			return 0;
		}else{
		    double alta = repositorioPelicula.cantidadAlta() * 100 / repositorioPelicula.cantidadTotal();
		    return (int) Math.round(alta);
	    }
	}
	
	public int cantidadPeliculaBaja() {
		int baja = 100 - cantidadPeliculaAlta();
		return baja;
	}
	
	public void validar(Pelicula pelicula, MultipartFile archivo, String archivoVideo ,String filename) throws Exception {

		if (pelicula.getTitulo() == null || pelicula.getTitulo().isEmpty() ) {
			throw new Exception("Nombre de película inválido");
		}

		if (pelicula.getAnio() == null || pelicula.getAnio().isEmpty()) {
			throw new Exception("Año de película inválido");
		}

		if (pelicula.getDescripcion() == null || pelicula.getDescripcion().isEmpty()) {
			throw new Exception("Descripción de película inválido");
		}

		if (pelicula.getDuracion() == null || pelicula.getDuracion().isEmpty()) {
			throw new Exception("Duración de película inválido");
		}

		if (pelicula.getGenero() == null) {
			throw new Exception("Género de película inválido");
		}

		if (pelicula.getPais() == null) {
			throw new Exception("País de película inválido");
		}

		if (pelicula.getIdioma() == null) {
			throw new Exception("Idioma de película inválido");
		}

		if (pelicula.getSubtitulo() == null) {
			throw new Exception("Subtítulo de película inválido");
		}

		if (archivo == null) {
			throw new Exception("Imagen de película inválido");
		}
		
		if (archivoVideo == null) {
			throw new Exception("Trailer de película inválido");
		}

		if (filename.contains("..")) {
			throw new Exception("Archivo inválido");
		}
		

		if (pelicula.getDirector() == null || pelicula.getDirector().getNombre().isEmpty()) {
			throw new Exception("Director de película inválido");
		}

		if (pelicula.getActores() == null || pelicula.getActores().isEmpty()) {
			throw new Exception("Actores de película inválido");
		}

	}

	@Override
	public Pelicula registrar(Pelicula entity) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pelicula editar(Pelicula entity) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
