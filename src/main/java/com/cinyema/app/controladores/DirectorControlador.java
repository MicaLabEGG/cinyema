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

import com.cinyema.app.entidades.Director;
import com.cinyema.app.enumeraciones.Pais;
import com.cinyema.app.servicios.DirectorServicio;

@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping("/director")
public class DirectorControlador {

	@Autowired
	private DirectorServicio directorServicio;

	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@GetMapping("")
	public String listar(ModelMap modelo) throws Exception {
		modelo.addAttribute("listar", "Lista Directores");
		modelo.addAttribute("directores", directorServicio.listar());
		return "vistas/director";
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@GetMapping("/registrar")
	public String guardar(ModelMap modelo) {
		modelo.addAttribute("registrar", "Registrar Director");
		modelo.addAttribute(directorServicio.registrarVacio());
		return "vistas/director";
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@PostMapping("/registrar")
	public String registrar(ModelMap modelo, Director director)
			throws Exception {
		try {
			modelo.addAttribute("registrar", "Registrar Director");
			modelo.addAttribute("director", directorServicio.registrar(director));
			return "redirect:/director";
		} catch (Exception e) {
			e.printStackTrace();
			modelo.addAttribute("registrar", "Registrar Director");
			modelo.put("error", "Error al ingresar datos");
			modelo.put("director", directorServicio.registrar(director));
			return "redirect:/director";
		}
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@GetMapping("/editar/{id}")
	public String editar(ModelMap modelo, @PathVariable Long id) {
			modelo.addAttribute("editar", "Editar Directores");
			modelo.addAttribute("director", directorServicio.obtenerDirectorPorId(id));
			return "vistas/director";
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@PostMapping("/editar/{id}")
	public String editar(ModelMap modelo, Director director) throws Exception {
		try {
			directorServicio.editar(director);
			return "redirect:/director";
		} catch (Exception e) {
			e.printStackTrace();
			modelo.addAttribute("editar", "Editar Directores");
			modelo.put("error", "Error al ingresar datos");
			directorServicio.editar(director);
			return "redirect:/director";
		}

	}

	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable Long id) {
			directorServicio.eliminar(id);
			return "redirect:/director";
	}

}
