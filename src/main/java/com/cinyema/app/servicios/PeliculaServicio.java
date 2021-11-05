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
import com.cinyema.app.repositorios.PeliculaRepositorio;

@Service
public class PeliculaServicio {

	@Autowired
	private PeliculaRepositorio repositorioPelicula;

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void registrar(Pelicula pelicula, MultipartFile archivo) throws Exception {
		String fileName = StringUtils.cleanPath(archivo.getOriginalFilename());
		validar(pelicula, archivo, fileName);
		pelicula.setImagen(Base64.getEncoder().encodeToString(archivo.getBytes()));
		pelicula.setAlta(true);
		repositorioPelicula.save(pelicula);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void editar(Pelicula pelicula, MultipartFile archivo) throws Exception {
		String fileName = StringUtils.cleanPath(archivo.getOriginalFilename());
		validar(pelicula, archivo, fileName);
		pelicula.setImagen(Base64.getEncoder().encodeToString(archivo.getBytes()));
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

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Optional<Pelicula> obtenerPeliculaPorId(Long idPelicula) {
		return repositorioPelicula.findById(idPelicula);
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
	public void eliminar(Pelicula pelicula) {
		repositorioPelicula.delete(pelicula);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void eliminarPeliculaPorId(Long idPelicula) throws Exception {
		repositorioPelicula.deleteById(idPelicula);
	}

	public void validar(Pelicula pelicula, MultipartFile archivo, String filename) throws Exception {

		if (pelicula.getTitulo() == null || pelicula.getTitulo().isBlank()) {
			throw new Exception("Nombre de película inválido");
		}

		if (repositorioPelicula.validarTituloPelicula(pelicula.getTitulo()) != null) {
			throw new Exception("Ya existe una película con el mismo título");
		}

		if (pelicula.getAnio() == null || pelicula.getAnio().isBlank()) {
			throw new Exception("Año de película inválido");
		}

		if (pelicula.getDescripcion() == null || pelicula.getDescripcion().isBlank()) {
			throw new Exception("Descripción de película inválido");
		}

		if (pelicula.getDuracion() == null || pelicula.getDuracion().isBlank()) {
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

}
