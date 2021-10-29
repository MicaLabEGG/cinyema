package com.cinyema.app.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cinyema.app.entidades.Actor;
import com.cinyema.app.entidades.Director;
import com.cinyema.app.entidades.Imagen;
import com.cinyema.app.entidades.Pelicula;
import com.cinyema.app.enumeraciones.Genero;
import com.cinyema.app.enumeraciones.Idioma;
import com.cinyema.app.enumeraciones.Pais;
import com.cinyema.app.enumeraciones.Subtitulo;
import com.cinyema.app.repositorios.ActorRepositorio;
import com.cinyema.app.repositorios.DirectorRepositorio;
import com.cinyema.app.repositorios.PeliculaRepositorio;


@Service
public class PeliculaServicio {

	@Autowired
	private PeliculaRepositorio repositorioPelicula;
	
	@Autowired
	private DirectorRepositorio repositorioDirector;
	
	@Autowired
	private ActorRepositorio repositorioActor;
	
	@Autowired
	private ImagenServicio imagenServicio;
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void crearPelicula(String titulo, String anio, String descripcion, String duracion, Genero genero,
			Pais pais, Idioma idioma, Subtitulo subtitulo, Long idDirector, Long idActor, MultipartFile archivo) throws Exception{
		
		@SuppressWarnings("deprecation")
		Director d = repositorioDirector.getOne(idDirector);
		@SuppressWarnings({ "deprecation", "unchecked" })
		List<Actor> a = (List<Actor>) repositorioActor.getOne(idActor);
		
		validarCrearPelicula(titulo, anio, descripcion, duracion, genero, pais, idioma, subtitulo, d, a, archivo);
				
		Pelicula p = new Pelicula();
		p.setTitulo(titulo);
		p.setAnio(anio);
		p.setDescripcion(descripcion);
		p.setDuracion(duracion);
		p.setGenero(Genero.AVENTURA);
		p.setPais(Pais.ARGENTINA);
		p.setIdioma(Idioma.ESPAÑOL_LATINO);
		p.setSubtitulo(Subtitulo.SINSUBTITULO);
		p.setDirector(d);
		p.setActores(a);
		
		Imagen imagen = imagenServicio.guardarImagen(archivo);
		p.setImagen(imagen);
		repositorioPelicula.save(p);
		
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void modificarPelicula(Long idPelicula, String titulo, String anio, String descripcion, String duracion, Genero genero,
			Pais pais, Idioma idioma, Subtitulo subtitulo, Long idDirector, Long idActor,MultipartFile archivo ) throws Exception{
		
		@SuppressWarnings("deprecation")
		Director d = repositorioDirector.getOne(idDirector);
		@SuppressWarnings({ "deprecation", "unchecked" })
		List<Actor> a = (List<Actor>) repositorioActor.getOne(idActor);
		
		validarCrearPelicula(titulo, anio, descripcion, duracion, genero, pais, idioma, subtitulo, d, a, archivo);
		
		Optional<Pelicula> respuesta = repositorioPelicula.findById(idPelicula);
		
		if (respuesta.isPresent()) {
			Pelicula p = respuesta.get();
			if (p.getIdPelicula().equals(idPelicula)) {
				
				p.setTitulo(titulo);
				p.setAnio(anio);
				p.setDescripcion(descripcion);
				p.setDuracion(duracion);
				p.setGenero(Genero.AVENTURA);
				p.setPais(Pais.ARGENTINA);
				p.setIdioma(Idioma.ESPAÑOL_LATINO);
				p.setSubtitulo(Subtitulo.SINSUBTITULO);
				p.setDirector(d);
				p.setActores(a);
				
				Long idImagen = null;
				if(p.getImagen() != null) {
					idImagen = p.getImagen().getId();
				}
				
				Imagen imagen = imagenServicio.actualizarImagen(idImagen, archivo);
				p.setImagen(imagen);
				
				repositorioPelicula.save(p);
			}else {
				throw new Exception("*No puede realizar la modificacion");
			}		
		}else {
			throw new Exception("No se encontró la pelicula solicitada");
		}
	}	
	
	@Transactional(readOnly = true)
	public List<Pelicula> listarPeliculas() {	
		return repositorioPelicula.findAll();
	}
	
	@Transactional(readOnly = true)
	public List<Pelicula> listarPeliculasActivos() {
		return repositorioPelicula.buscarPeliculasActivas();
	}
	
	@Transactional(readOnly = true)
	public List<Pelicula> buscarPeliculaPorTitulo(String titulo) throws Exception {
		
		List<Pelicula> peliculas = repositorioPelicula.buscarPeliculaPorTitulo(titulo);
		
		if(!peliculas.isEmpty()) {
			return peliculas;
		}else {
			throw new Exception("*No se encontró el título de la película");
		}	
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Optional<Pelicula> buscarPeliculaPorId(Long idPelicula) {
		return repositorioPelicula.findById(idPelicula);
	}	
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Pelicula peliculaAlta(Long idPelicula) throws Exception{

		Optional<Pelicula> pelicula = repositorioPelicula.findById(idPelicula);	
		
		if (pelicula.isPresent()) {
			Pelicula p = pelicula.get();

			p.setAlta(true);
			
			return repositorioPelicula.save(p);
		}else {
			throw new Exception("No se encontro la pelicula solicitada");
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Pelicula peliculaBaja(Long id) throws Exception{

		Optional<Pelicula> pelicula = repositorioPelicula.findById(id);	
		
		if (pelicula.isPresent()) {
			Pelicula p = pelicula.get();

			p.setAlta(false);
			
			return repositorioPelicula.save(p);
		}else {
			throw new Exception("No se encontro la pelicula solicitada");
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void eliminarPelicula(Pelicula pelicula) {
		repositorioPelicula.delete(pelicula);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void eliminarPeliculaPorId(Long idPelicula) throws Exception {
		repositorioPelicula.deleteById(idPelicula);
	}
	
	public void validarCrearPelicula(String titulo, String anio, String descripcion, String duracion, Genero genero,
			Pais pais, Idioma idioma, Subtitulo subtitulo, Director d, List<Actor>a, MultipartFile archivo ) throws Exception {

		if (titulo == null || titulo.isEmpty() || titulo.contains("  ")) {
			throw new Exception("Nombre de Pelicula invalido ");
		}
		
		if (anio == null || anio.isEmpty() || anio.contains("  ")) {
			throw new Exception("Año de Pelicula invalido");
		}
		
		if (descripcion == null || descripcion.isEmpty() || descripcion.contains("  ")) {
			throw new Exception("Descripcion de Pelicula invalido");
		}
		
		if (duracion == null || duracion.isEmpty() || duracion.contains("  ")) {
			throw new Exception("Duracion de Pelicula invalido");
		}
		
		if (genero == null) {
			throw new Exception("Genero de Pelicula invalido");
		}
		
		if (pais== null) {
			throw new Exception("Pais de Pelicula invalido");
		}
		
		if (idioma == null) {
			throw new Exception("Idioma de Pelicula invalido");
		}
		
		if (subtitulo == null) {
			throw new Exception("Subtitulo de Pelicula invalido");
		}
		
		if (archivo == null) {
			throw new Exception("Imagen de Pelicula invalido");
		}
		
		if (d == null || d.getNombre().isEmpty()) {
			throw new Exception("Director de Pelicula invalido");
		}
		
		if (a == null || a.isEmpty()) {
			throw new Exception("Actores de Pelicula invalido");
		}
		
	}
	
}
