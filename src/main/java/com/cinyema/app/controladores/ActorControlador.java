package com.cinyema.app.controladores;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.cinyema.app.entidades.Actor;
import com.cinyema.app.enumeraciones.Pais;
import com.cinyema.app.servicios.ActorServicio;

@Controller
@RequestMapping("/actor")
public class ActorControlador {

	@Autowired
	private ActorServicio actorServicio;

	@GetMapping("")
	public String lista(ModelMap modelo) {
		try {
			List<Actor> todosActores = actorServicio.buscarActores();
			modelo.addAttribute("listar", "Lista Actores");
			modelo.addAttribute("actores", todosActores);
			return "admin/vistas/actor";
		} catch (Exception e) {
			e.getMessage();
			return "admin/vistas/actor";
		}

	}

	@GetMapping("/registrar")
	public String ingreso(ModelMap modelo) {
		modelo.addAttribute("registrar", "Registrar Actor");
		return "admin/vistas/actor";
	}

	@PostMapping("/registrar")
	public String registrarActor(ModelMap modelo, @RequestParam String nombreCompleto, @RequestParam Pais pais)
			throws Exception {
		try {
			Actor actor = actorServicio.registrarActor(nombreCompleto, pais);
			modelo.put("actor", actor);
			return "redirect:/actor";
		} catch (Exception e) {
			System.out.println(e.getMessage());
			modelo.put("error", "Falta algun dato o no puede ingresar un Actor con igual nombre a otro");
			return "redirect:/actor";
		}
	}

	
	@GetMapping("/editar/{id}")
	public String modificarActor(ModelMap modelo, @PathVariable Long id) throws Exception {
		try {
			Actor actor = actorServicio.obtenerActor(id);
			modelo.addAttribute("editar", "Editar Actor");
			modelo.addAttribute("actor", actor);
			return "admin/vistas/actor";
		} catch (Exception e) {
			System.out.println(e.getMessage());
			modelo.put("error", "Falta algun dato");
			return "admin/vistas/actor";
		}
	}

	@PostMapping("/editar/{id}")
	public String modificar(ModelMap modelo, @PathVariable Long id, @RequestParam String nombreCompleto,
			@RequestParam Pais pais) throws Exception {
		try {
			actorServicio.modificarActor(id, nombreCompleto, pais);
			modelo.put("exito", "Ingreso exitoso!");
			return "redirect:/actor";
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			modelo.addAttribute("error", e.getMessage());
		}
		return "redirect:/actor";
	}
	
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable Long id) {
		try {
			actorServicio.eliminarDirector(id);
			return "redirect:/actor";		
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "redirect:/actor";	
		}
		
	}
	
}
