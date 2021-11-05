package com.cinyema.app.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.cinyema.app.entidades.Pelicula;
import com.cinyema.app.enumeraciones.Genero;
import com.cinyema.app.enumeraciones.Idioma;
import com.cinyema.app.enumeraciones.Pais;
import com.cinyema.app.enumeraciones.Subtitulo;
import com.cinyema.app.servicios.ActorServicio;
import com.cinyema.app.servicios.DirectorServicio;
import com.cinyema.app.servicios.PeliculaServicio;

@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping("/pelicula")
public class PeliculaControlador {

	@Autowired
	private PeliculaServicio servicioPelicula;

	@Autowired
	private DirectorServicio servicioDirector;

	@Autowired
	private ActorServicio servicioActor;

	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@GetMapping("")
	public String listar(ModelMap modelo) {
		modelo.addAttribute("listar", "Lista de Peliculas");
		modelo.addAttribute("peliculas", servicioPelicula.listarPeliculas());
		modelo.addAttribute("actores", servicioActor.listar());
		return "vistas/pelicula";
	}

	// Buscador de peliculas por título: futuro filtro
	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@PostMapping("/buscarPeliculaPorTitulo")
	public String buscarPeliculaPorTitulo(ModelMap modelo, @RequestParam String titulo) throws Exception {
		try {
			modelo.addAttribute("buscador", "Buscador de Películas por Título");
			modelo.addAttribute("peliculas", servicioPelicula.buscarPeliculaPorTitulo(titulo));
			return "vistas/pelicula";
		} catch (Exception e) {
			modelo.put("ErrorBuscar", e.getMessage());
			modelo.put("titulo", titulo);
			return "vistas/pelicula";
		}
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@GetMapping("/registrar")
	public String registrar(ModelMap modelo) {
		modelo.addAttribute("registro", "Registro de Peliculas");
		modelo.addAttribute("directores", servicioDirector.listar());
		modelo.addAttribute("actores", servicioActor.listar());
		modelo.addAttribute("pelicula", new Pelicula());
		return "vistas/pelicula";
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@PostMapping("/registrar")
	public String registrar(ModelMap modelo, Pelicula pelicula, @RequestParam MultipartFile archivo) throws Exception {
		try {
			servicioPelicula.crearPelicula(pelicula, archivo);
			return "redirect:/pelicula";
		} catch (Exception e) {
			e.printStackTrace();
			modelo.put("error", "Error al ingresar datos");
			return "redirect:/pelicula";
		}

	}

	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@GetMapping("/editar/{id}")
	public String editar(ModelMap modelo, @PathVariable Long idPelicula) {
		try {
			modelo.addAttribute("editar", "Editar Peliculas");
			modelo.addAttribute("titulo", servicioPelicula.buscarPeliculaPorId(idPelicula).get().getTitulo());
			modelo.addAttribute("anio", servicioPelicula.buscarPeliculaPorId(idPelicula).get().getAnio());
			modelo.addAttribute("descripcion", servicioPelicula.buscarPeliculaPorId(idPelicula).get().getDescripcion());
			return "vistas/pelicula";
		} catch (Exception e) {
			e.printStackTrace();
			modelo.put("error", "Error al ingresar datos");
			return "vistas/director";
		}
		
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@PostMapping("/editar")
	public String editar(ModelMap modelo, @RequestParam Long idPelicula, @RequestParam String titulo,
			@RequestParam String anio, @RequestParam String descripcion, @RequestParam String duracion,
			@RequestParam Genero genero, @RequestParam Pais pais, @RequestParam Idioma idioma,
			@RequestParam Subtitulo subtitulo, @RequestParam Long idDirector, @RequestParam Long idActor,
			MultipartFile archivo) throws Exception {
		try {
			servicioPelicula.modificarPelicula(idPelicula, titulo, anio, descripcion, duracion, genero, pais, idioma,
					subtitulo, idDirector, idActor, archivo);
			return "redirect:/pelicula";
		} catch (Exception e) {
			e.printStackTrace();
			modelo.put("error", "Error al ingresar datos");
			return "redirect:/pelicula";
		}
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@GetMapping("/alta/{id}")
	public String alta(@PathVariable Long idPelicula) {
		try {
			servicioPelicula.peliculaAlta(idPelicula);
			return "redirect:/pelicula";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/pelicula";
		}
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@GetMapping("/baja/{id}")
	public String baja(@PathVariable Long idPelicula) {
		try {
			servicioPelicula.peliculaBaja(idPelicula);
			return "redirect:/pelicula";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/pelicula";
		}

	}

	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable Long idPelicula) {
		try {
			servicioPelicula.eliminarPeliculaPorId(idPelicula);
			return "redirect:/pelicula";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/pelicula";
		}

	}

}
