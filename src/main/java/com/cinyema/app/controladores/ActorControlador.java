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
import com.cinyema.app.servicios.ActorServicio;

@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping("/actor")
public class ActorControlador {

	@Autowired
	private ActorServicio actorServicio;

	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@GetMapping("")
	public String lista(ModelMap modelo) {
		modelo.addAttribute("listar", "Lista Actores");
		modelo.addAttribute("actores", actorServicio.buscarActores());
		return "vistas/actor";
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@GetMapping("/registrar")
	public String registrar(ModelMap modelo) {
		modelo.addAttribute("registrar", "Registrar Actor");
		modelo.addAttribute(actorServicio.registrarVacio());
		return "vistas/actor";
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@PostMapping("/registrar")
	public String registrarActor(ModelMap modelo, Actor actor)
			throws Exception {
		try {
			actorServicio.registrarVacio(actor);
			return "redirect:/actor";
		} catch (Exception e) {
			e.printStackTrace();
			modelo.put("error", "Error al ingresar datos");
			return "redirect:/actor";
		}
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@GetMapping("/editar/{id}")
	public String editar(ModelMap modelo) throws Exception {
		try {
			modelo.addAttribute("editar", "Editar Actor");
			modelo.addAttribute("actor", actorServicio.registrarVacio();
			return "vistas/actor";
		} catch (Exception e) {
			e.printStackTrace();
			modelo.put("error", "Error al ingresar datos");
			return "vistas/actor";
		}
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@PostMapping("/editar/{id}")
	public String editar(ModelMap modelo, @PathVariable Long id, @RequestParam String nombreCompleto,
			@RequestParam Pais pais) throws Exception {
		try {
			actorServicio.modificarActor(id, nombreCompleto, pais);
			return "redirect:/actor";
		} catch (Exception e) {
			e.printStackTrace();
			modelo.addAttribute("error", "Error al ingresar datos");
			return "redirect:/actor";
		}
		
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable Long id) {
		try {
			actorServicio.eliminarDirector(id);
			return "redirect:/actor";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/actor";
		}

	}

}
