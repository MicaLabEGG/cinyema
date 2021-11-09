package com.cinyema.app.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.cinyema.app.entidades.Director;
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
		try {
		    modelo.addAttribute("listar", "Lista Directores");
		    modelo.addAttribute("directores", directorServicio.listar());
		    return "vistas/admin/director";
		}catch(Exception e) {
			e.printStackTrace();
            modelo.addAttribute("listar", "Lista Directores");
            modelo.put("error", e.getMessage());
            return "vistas/admin/director";
		}
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@GetMapping("/registrar")
	public String registrar(ModelMap modelo) {
		try {
		    modelo.addAttribute("registrar", "Registrar Director");
		    modelo.addAttribute(directorServicio.registrarVacio());
		    return "vistas/admin/director";
		}catch(Exception e) {
			e.printStackTrace();
			modelo.addAttribute("registrar", "Registrar Director");
			modelo.put("error", e.getMessage());
			return "vistas/admin/director";
		}
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
			modelo.put("error", e.getMessage());
			modelo.put("director", director);
			return "redirect:/director";
		}
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@GetMapping("/editar/{idDirector}")
	public String editar(ModelMap modelo, @PathVariable Long idDirector) {
		try {
			modelo.addAttribute("editar", "Editar Directores");
			modelo.addAttribute("director", directorServicio.obtenerPorId(idDirector));
			return "vistas/admin/director";
		}catch (Exception e) {
			e.printStackTrace();
			modelo.addAttribute("editar", "Editar Director");
			modelo.put("error", e.getMessage());
			return "vistas/admin/director";
		}
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@PostMapping("/editar/{idDirector}")
	public String editar(ModelMap modelo, Director director) throws Exception {
		try {
			directorServicio.editar(director);
			return "redirect:/director";
		} catch (Exception e) {
			e.printStackTrace();
			modelo.addAttribute("editar", "Editar Directores");
			modelo.put("error", e.getMessage());
			directorServicio.editar(director);
			return "redirect:/director";
		}

	}

	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@GetMapping("/eliminar/{id}")
	public String eliminar(ModelMap modelo, @PathVariable Long id) {
		try {
			directorServicio.eliminar(id);
			return "redirect:/director";
		}catch (Exception e) {
			e.printStackTrace();
			modelo.put("error", e.getMessage());
			return "redirect:/director";
		}
	}

}
