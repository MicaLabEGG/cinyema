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
		modelo.addAttribute("directores", directorServicio.listarDirectores());
		return "vistas/director";

	}

	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@GetMapping("/registrar")
	public String guardar(ModelMap modelo) {
		modelo.addAttribute("registrar", "Registrar Director");
		return "vistas/director";
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@PostMapping("/registrar")
	public String registrar(ModelMap modelo, @RequestParam("nombre") String nombre, @RequestParam Pais pais)
			throws Exception {
		try {
			modelo.put("director", directorServicio.crearDirector(nombre, pais));
			return "redirect:/director";
		} catch (Exception e) {
			e.printStackTrace();
			modelo.put("error", "Error al ingresar datos");
			return "redirect:/director";
		}
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@GetMapping("/editar/{id}")
	public String modificar(@PathVariable Long id, ModelMap modelo) {
		try {
			modelo.addAttribute("editar", "Editar Directores");
			modelo.addAttribute("director", directorServicio.obtenerDirectorPorId(id));
			return "vistas/director";
		} catch (Exception e) {
			e.printStackTrace();
			modelo.put("error", "Error al ingresar datos");
			return "vistas/director";
		}

	}

	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@PostMapping("/editar/{id}")
	public String modificarDirector(ModelMap modelo, @PathVariable Long id, @RequestParam String nombre,
			@RequestParam Pais pais) throws Exception {

		try {
			directorServicio.modificarDirector(id, nombre, pais);
			modelo.put("exito", "Modificacion exitosa");
			return "redirect:/director";
		} catch (Exception e) {
			System.out.println(e.getMessage());
			modelo.put("error", "Error al ingresar datos");
			return "redirect:/director";
		}

	}

	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable Long id) {
		try {
			directorServicio.eliminarDirector(id);
			return "redirect:/director";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/director";
		}

	}

}
