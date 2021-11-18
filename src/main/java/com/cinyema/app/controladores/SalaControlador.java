package com.cinyema.app.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.cinyema.app.entidades.Sala;
import com.cinyema.app.servicios.AsientoServicio;
import com.cinyema.app.servicios.PeliculaServicio;
import com.cinyema.app.servicios.SalaServicio;

@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping("/sala")
public class SalaControlador {

	@Autowired
	private SalaServicio salaServicio;
	
	@Autowired
	private PeliculaServicio peliculaServicio;

	@Autowired
	private AsientoServicio asientoServicio;
	
	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@GetMapping("")
	public String listar(ModelMap modelo) {
		try {
		    modelo.addAttribute("listar", "Lista de Salas");
		    modelo.addAttribute("salas", salaServicio.listar());
		    return "vistas/admin/sala";
		}catch (Exception e) {
			e.printStackTrace();
            modelo.addAttribute("listar", "Lista de Salas");
            modelo.put("error", e.getMessage());
			return "redirect:/sala";
		}
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@GetMapping("/registrar")
	public String registrar(ModelMap modelo) {
		try {
		    modelo.addAttribute("registrar", "Registrar Salas");
		    modelo.addAttribute("sala", salaServicio.registrarVacio());
		    modelo.addAttribute("peliculas", peliculaServicio.listar());
//		    modelo.addAttribute("asientos", asientoServicio.listar());
		    return "vistas/admin/sala";
		}catch(Exception e) {
			e.printStackTrace();
			modelo.addAttribute("registrar", "Registrar Salas");
			modelo.put("error", e.getMessage());
			return "redirect:/sala";
		}
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@PostMapping("/registrar")
	public String registrar(ModelMap modelo, Sala sala ) throws Exception {
		try {
			salaServicio.registrar(sala);
			return "redirect:/sala";
		} catch (Exception e) {
			e.printStackTrace();
			modelo.addAttribute("registrar", "Registrar Sala");
			modelo.addAttribute("sala", sala);
			modelo.put("error", e.getMessage());
			return "redirect:/sala";
		}

	}

	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@GetMapping("/editar/{id}")
	public String editar(ModelMap modelo, @PathVariable Long idSala) {
		try {
		    modelo.addAttribute("editar", "Editar Salas");
		    modelo.addAttribute("sala", salaServicio.obtenerPorId(idSala));
//		    modelo.addAttribute("peliculas", peliculaServicio.listar());
		    modelo.addAttribute("asientos", asientoServicio.listar());
		    return "vistas/admin/sala";
		}catch (Exception e) {
			e.printStackTrace();
			modelo.addAttribute("editar", "Editar Salas");
			modelo.put("error", e.getMessage());
			return "redirect:/sala" ;
		}
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@PostMapping("/editar")
	public String editar(ModelMap modelo, Sala sala ) throws Exception {
		try {
			salaServicio.editar(sala);
			return "redirect:/sala";
		} catch (Exception e) {
			e.printStackTrace();
			modelo.addAttribute("editar", "Editar Sala");
			modelo.addAttribute("sala", sala);
			modelo.put("error", e.getMessage());
			return "redirect:/sala";
		}
	}

//	// Buscador de Sala por título de Pelicula : futuro filtro
//	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
//	@PostMapping("/buscarSalaPorTituloPelicula")
//	public String buscarSalaPorTituloPelicula(ModelMap modelo, Sala sala) throws Exception {
//		try {
//			modelo.addAttribute("buscador", "Buscador de Sala por Titulo Pelicula");
//			modelo.addAttribute("cines", salaServicio.obtenerSalaPorTituloPelicula(sala.getFunciones().getPelicula().getTitulo()));
//			return "vistas/admin/sala";
//		} catch (Exception e) {
//			e.printStackTrace();
//			modelo.addAttribute("editar", "Editar Sala");
//			modelo.addAttribute("sala", salaServicio.obtenerSalaPorTituloPelicula(sala.getPelicula().getTitulo()));
//			modelo.put("error", e.getMessage());
//			return "vistas/admin/sala";
//		}
//	}

	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable Long idSala) {
		try {
			salaServicio.eliminar(idSala);
			return "redirect:/sala";
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
			return "redirect:/sala";
		}

	}
}
