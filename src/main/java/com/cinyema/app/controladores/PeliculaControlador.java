package com.cinyema.app.controladores;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cinyema.app.entidades.Pelicula;
import com.cinyema.app.servicios.ActorServicio;
import com.cinyema.app.servicios.PeliculaServicio;



@Controller
@RequestMapping("/pelicula")
public class PeliculaControlador {
	
	@Autowired
	private PeliculaServicio servicioPelicula;
	
	@Autowired
	private DirectorServicio servicioDirector;
	
	@Autowired
	private ActorServicio servicioActor;
	
	
	
	@GetMapping()
	public String listarPeliculas(ModelMap modelo) {
		List<Pelicula> peliculas = servicioPelicula.listarPeliculas();
		modelo.addAttribute("peliculas", peliculas);
		
		return "/pelicula/pelicula";
	}
	
	
	@PostMapping("/buscarPelicula")
	public String listarPeliculasPorTitulo(ModelMap modelo, @RequestParam String titulo) throws Exception{
		
		try {
			List<Pelicula> peliculas =  servicioPelicula.buscarPeliculaPorTitulo(titulo);
			modelo.addAttribute("peliculas", peliculas);
			
			return "/pelicula/pelicula";
			
		} catch (Exception e) {
			modelo.put("ErrorBuscar", e.getMessage());

		    modelo.put("titulo", titulo);
			
		    return "/pelicula/pelicula";
		}		
	}	
	
	@GetMapping("/agregarPelicula")
	public String agregarPelicula(ModelMap modelo) {
		List<Director> directores = servicioDirector.listarDirectores();
		List<Editorial> actores = servicioActor.listarActores();
		modelo.addAttribute("directores",directores);
		modelo.addAttribute("actores",actores);
		
		return "/pelicula/formAgregarPelicula";
	}
	
	@PostMapping("/agregarPelicula") 
	public String guardarPelicula(ModelMap modelo, @RequestParam String titulo, @RequestParam String anio, @RequestParam String descripcion, @RequestParam String duracion, @RequestParam String genero, @RequestParam String pais, @RequestParam String idioma, @RequestParam String subtitulo, @RequestParam String imagen, @RequestParam String idDirector, @RequestParam String idActor) throws Exception{
		
		try {
			servicioPelicula.crearPelicula(titulo, anio, descripcion, duracion, genero, pais, idioma, subtitulo, imagen, idDirector, idActor);
			
			return "redirect:/pelicula";	
			
		} catch (Exception e) {
			modelo.put("Error", e.getMessage());
			// devolvemos los valores ingresados al formulario
			modelo.put("titulo", titulo);
			modelo.put("anio", anio);
			modelo.put("descripcion", descripcion);
			
			
			return "/pelicula/formAgregarPelicula";
		}
			
	}
	
	@GetMapping("/modificarPelicula/{id}")
	public String modificarPelicula(ModelMap modelo, @PathVariable Long idPelicula) {
		
		Optional<Pelicula> pelicula = servicioPelicula.buscarPeliculaPorId(idPelicula);		
		List<Director> directores = servicioDirector.listarDirectores();
		List<Actor> actores = servicioActor.listarActores();
		
		modelo.addAttribute("titulo", pelicula.get().getTitulo());
		modelo.addAttribute("anio", pelicula.get().getAnio());
		modelo.addAttribute("descripcion", pelicula.get().getDescripcion());
		
				
		return "/pelicula/formModificarPelicula";
	}
	
	@PostMapping("/modificarPelicula") 
	public String editarPelicula(ModelMap modelo, @RequestParam Long idPelicula, @RequestParam String titulo, @RequestParam String anio, @RequestParam String descripcion, @RequestParam String duracion, @RequestParam String genero, @RequestParam String pais, @RequestParam String idioma, @RequestParam String subtitulo, @RequestParam String imagen, @RequestParam String idDirector, @RequestParam String idActor) throws Exception{
		
		try {
			servicioPelicula.modificarPelicula(idPelicula, titulo, anio, descripcion, duracion, genero, pais, idioma, subtitulo, imagen, idDirector, idActor);
			
			return "redirect:/pelicula";
			
		} catch (Exception e) {
			modelo.put("Error", e.getMessage());
			// devolvemos los valores ingresados al formulario
			modelo.put("titulo", titulo);
			modelo.put("anio", anio);
			modelo.put("descripcion", descripcion);
			
			return "/pelicula/formModificarPelicula";
		}
	}
	
	@GetMapping("/altaPelicula/{id}")
	public String altaPelicula(@PathVariable Long idPelicula) {
		
		try {
			servicioPelicula.peliculaAlta(idPelicula);
			
			return "redirect:/pelicula";	
			
		} catch (Exception e) {
			
			return "redirect:/pelicula";	
		}
	}
	
	@GetMapping("/bajaPelicula/{id}")
	public String bajaPelicula(@PathVariable Long idPelicula) {
				
		try {
			servicioPelicula.peliculaBaja(idPelicula);
			
			return "redirect:/pelicula";	
			
		} catch (Exception e) {
			
			return "redirect:/pelicula";	
		}
				
	}
	
	@GetMapping("/eliminarPeliculaPorId/{id}")
	public String eliminarPeliculaPorId(@PathVariable Long idPelicula) {
				
		try {
			servicioPelicula.eliminarPeliculaPorId(idPelicula);
			
			return "redirect:/pelicula";		
			
		} catch (Exception e) {
			
			return "redirect:/pelicula";	
		}
		
	}
	
	
	
}
