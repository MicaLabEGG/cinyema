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

import com.cinyema.app.entidades.Actor;
import com.cinyema.app.entidades.Director;
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

	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void crearPelicula(Pelicula pelicula, MultipartFile archivo) throws Exception{
		
//		@SuppressWarnings("deprecation")
//		Director d = repositorioDirector.getOne(idDirector);
//		@SuppressWarnings({ "deprecation", "unchecked" })
//		List<Actor> a = (List<Actor>) repositorioActor.getOne(idActor);
		
		String fileName = StringUtils.cleanPath(archivo.getOriginalFilename());
		
		validarPelicula(pelicula.getTitulo(),pelicula.getAnio(),pelicula.getDescripcion(), pelicula.getDuracion(), pelicula.getGenero(), pelicula.getPais(), pelicula.getIdioma(), pelicula.getSubtitulo(),pelicula.getDirector(),pelicula.getActores(), archivo, fileName);
				
//		Pelicula p = new Pelicula();
//		System.out.println("Id es "+p.getIdPelicula());
//		p.setTitulo(titulo);
//		p.setAnio(anio);
//		p.setDescripcion(descripcion);
//		p.setDuracion(duracion);
//		p.setGenero(genero);
//		p.setPais(pais);
//		p.setIdioma(idioma);
//		p.setSubtitulo(subtitulo);
//		p.setDirector(d);
//		p.setActores(a);
		
		pelicula.setImagen(Base64.getEncoder().encodeToString(archivo.getBytes()));
		
		pelicula.setAlta(true);
		
		repositorioPelicula.save(pelicula);
		
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void modificarPelicula(Long idPelicula, String titulo, String anio, String descripcion, String duracion, Genero genero,
			Pais pais, Idioma idioma, Subtitulo subtitulo, Long idDirector, Long idActor, MultipartFile archivo) throws Exception{
		
		@SuppressWarnings("deprecation")
		Director d = repositorioDirector.getOne(idDirector);
		@SuppressWarnings({ "deprecation", "unchecked" })
		List<Actor> a = (List<Actor>) repositorioActor.getOne(idActor);
		
		String fileName = StringUtils.cleanPath(archivo.getOriginalFilename());
		
		validarPelicula(titulo, anio, descripcion, duracion, genero, pais, idioma, subtitulo, d, a, archivo, fileName);
		
		Optional<Pelicula> respuesta = repositorioPelicula.findById(idPelicula);
		
		if (respuesta.isPresent()) {
			Pelicula p = respuesta.get();
			if (p.getIdPelicula().equals(idPelicula)) {
				
				p.setTitulo(titulo);
				p.setAnio(anio);
				p.setDescripcion(descripcion);
				p.setDuracion(duracion);
				p.setGenero(genero);
				p.setPais(pais);
				p.setIdioma(idioma);
				p.setSubtitulo(subtitulo);
				p.setDirector(d);
				p.setActores(a);
				
				p.setImagen(Base64.getEncoder().encodeToString(archivo.getBytes()));
				
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
	
	public void validarPelicula(String titulo, String anio, String descripcion, String duracion, Genero genero,
			Pais pais, Idioma idioma, Subtitulo subtitulo, Director d, List<Actor> a, MultipartFile archivo, String fileName) throws Exception {

		if (titulo == null || titulo.isEmpty() || titulo.contains("  ")) {
			throw new Exception("*Nombre de Película inválido");
		}
		
		if(repositorioPelicula.validarTituloPelicula(titulo) != null) {
			throw new Exception("*Ya existe una Película con el mismo título");
		}
		
		if (anio == null || anio.isEmpty() || anio.contains("  ")) {
			throw new Exception("*Año de Película inválido");
		}
		
		if (descripcion == null || descripcion.isEmpty() || descripcion.contains("  ")) {
			throw new Exception("*Descripción de Película inválido");
		}
		
		if (duracion == null || duracion.isEmpty() || duracion.contains("  ")) {
			throw new Exception("*Duración de Película inválido");
		}
		
		if (genero == null) {
			throw new Exception("*Genero de Película inválido");
		}
		
		if (pais == null) {
			throw new Exception("País de Película inválido");
		}
		
		if (idioma == null) {
			throw new Exception("Idioma de Película inválido");
		}
		
		if (subtitulo == null) {
			throw new Exception("Subtítulo de Película inválido");
		}
		
		if (archivo == null) {
			throw new Exception("Imagen de Película inválido");
		}
		
		if(fileName.contains("..")) {
			throw new Exception("No es un archivo valdio");
		}
		
		if (d == null || d.getNombre().isEmpty()) {
			throw new Exception("Director de Película inválido");
		}
		
		if (a == null || a.isEmpty()) {
			throw new Exception("Actores de Película inválido");
		}
		
	}
	
}
