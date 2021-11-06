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
		modelo.addAttribute("listar", "Lista de Películas");
		modelo.addAttribute("peliculas", servicioPelicula.listar());
		modelo.addAttribute("actores", servicioActor.listar());
		return "vistas/admin/pelicula";
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@GetMapping("/registrar")
	public String registrar(ModelMap modelo) {
		modelo.addAttribute("registrar", "Registrar Películas");
		modelo.addAttribute("pelicula", servicioPelicula.registrarVacio());
		return "vistas/admin/pelicula";
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@PostMapping("/registrar")
	public String registrar(ModelMap modelo, Pelicula pelicula, MultipartFile archivo) throws Exception {
		try {
			servicioPelicula.registrar(pelicula, archivo);
			return "redirect:/pelicula";
		} catch (Exception e) {
			e.printStackTrace();
			modelo.addAttribute("registrar", "Registrar Película");
			modelo.addAttribute("pelicula", pelicula);
			modelo.put("error", e.getMessage());
			return "redirect:/pelicula";
		}

	}

	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@GetMapping("/editar/{id}")
	public String editar(ModelMap modelo, @PathVariable Long idPelicula) {
		modelo.addAttribute("editar", "Editar Películas");
		modelo.addAttribute("pelicula", servicioPelicula.obtenerPeliculaPorId(idPelicula));
		return "vistas/admin/pelicula";
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@PostMapping("/editar")
	public String editar(ModelMap modelo, Pelicula pelicula, MultipartFile archivo) throws Exception {
		try {
			servicioPelicula.editar(pelicula, archivo);
			return "redirect:/pelicula";
		} catch (Exception e) {
			e.printStackTrace();
			modelo.addAttribute("editar", "Editar Película");
			modelo.addAttribute("pelicula", pelicula);
			modelo.put("error", e.getMessage());
			return "redirect:/pelicula";
		}
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@GetMapping("/alta/{id}")
	public String alta(@PathVariable Long idPelicula) {
		try {
			servicioPelicula.alta(idPelicula);
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
			servicioPelicula.baja(idPelicula);
			return "redirect:/pelicula";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/pelicula";
		}

	}

	// Buscador de peliculas por título: futuro filtro
	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@PostMapping("/buscarPeliculaPorTitulo")
	public String buscarPeliculaPorTitulo(ModelMap modelo, @RequestParam String titulo) throws Exception {
		try {
			modelo.addAttribute("buscador", "Buscador de películas por Título");
			modelo.addAttribute("peliculas", servicioPelicula.obtenerPeliculaPorTitulo(titulo));
			return "vistas/admin/pelicula";
		} catch (Exception e) {
			e.printStackTrace();
			modelo.addAttribute("editar", "Editar Película");
			modelo.addAttribute("pelicula", servicioPelicula.obtenerPeliculaPorTitulo(titulo));
			modelo.put("error", e.getMessage());
			return "vistas/admin/pelicula";
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
			System.err.println(e.getMessage());
			return "redirect:/pelicula";
		}

	}

}
