package com.cinyema.app.controladores;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.cinyema.app.entidades.Cine;
import com.cinyema.app.entidades.Sala;
import com.cinyema.app.servicios.CineServicio;
import com.cinyema.app.servicios.SalaServicio;

@Controller
@RequestMapping("/cine")
public class CineControlador {
	
	@Autowired
	private CineServicio cineServicio;
	
	@Autowired
	private SalaServicio salaServicio;
	
	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@GetMapping("")
	public String listar(ModelMap modelo) {
		try {
			modelo.addAttribute("listar", "Lista de Cines");
		    modelo.addAttribute("cines", cineServicio.listar());
		    return "vistas/admin/cine";
		}catch (Exception e) {
			e.printStackTrace();
            modelo.addAttribute("listar", "Lista de Cines");
            modelo.put("error", e.getMessage());
			return "vistas/admin/actor";
		}

	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@PostMapping("/buscarCine")
	public String listarCinePorNombre(ModelMap modelo, @RequestParam String nombre) throws Exception {
		try {
			modelo.addAttribute("cines", cineServicio.listarCinePorNombre(nombre));
			return "/cine/cine";
		} catch (Exception e) {
			modelo.put("ErrorBuscar", e.getMessage());
			modelo.put("nombre", nombre);
			return "/cine/cine";
		}
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@GetMapping("/registrar")
	public String registrar(ModelMap modelo) {
		try {
			modelo.addAttribute("registrar", "Registrar Cine");
			modelo.addAttribute("cine", cineServicio.registrarVacio());
		    modelo.addAttribute("salas", salaServicio.listar());
            return "vistas/admin/cine";
		}catch (Exception e) {
			e.printStackTrace();
			modelo.addAttribute("registrar", "Registrar Cine");
			modelo.put("error", e.getMessage());
			return "vistas/admin/cine";
		}
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@PostMapping("/registrar")
	public String registrar(ModelMap modelo, Cine cine) throws Exception {
		try {
			cineServicio.registrar(cine);
			return "redirect:/cine";
		} catch (Exception e) {
			e.printStackTrace();
			modelo.addAttribute("registrar", "Registrar Cine");
			modelo.addAttribute("cine", cine);
			modelo.put("error", e.getMessage());
			return "redirect:/actor";
		}

	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@GetMapping("/editar/{id}")
	public String editar(ModelMap modelo, @PathVariable Long idCine) throws Exception {
        try {
        	modelo.addAttribute("editar", "Editar Cine");
 			modelo.addAttribute("cine", cineServicio.obtenerPorId(idCine));
 			return "vistas/admin/cine";
 		}catch (Exception e) {
 			e.printStackTrace();
 			modelo.addAttribute("editar", "Editar Cine");
 			modelo.put("error", e.getMessage());
 			return "vistas/admin/cine";
 		}
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@PostMapping("/editar/{id}")
	public String editar(ModelMap modelo, Cine cine ) throws Exception {
		try {
			cineServicio.editar(cine);
			return "redirect:/cine";
		} catch (Exception e) {
			e.printStackTrace();
			modelo.addAttribute("editar", "Editar Cine");
			modelo.addAttribute("cine", cine);
			modelo.put("error", e.getMessage());
			return "redirect:/cine" ;
		}
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@GetMapping("/eliminar/{id}")
	public String eliminar(ModelMap modelo, @PathVariable Long idCine) {
		try {
			cineServicio.eliminar(idCine);
			return "redirect:/cine";
		}catch (Exception e) {
			e.printStackTrace();
			modelo.put("error", e.getMessage());
			return "redirect:/cine";
		}
	}
}