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

@Controller
@RequestMapping("/cine")
public class CineControlador {
	
	@Autowired
	private CineServicio cineServicio;
	
	//@Autowired
	//private SalaServicio salaServicio;
	
	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@GetMapping("")
	public String listarCines(ModelMap modelo) {
		List<Cine> cines = cineServicio.listar();
		modelo.addAttribute("cines", cines);
		//List<Sala> salas = salaServicio.listarSalas();
		//modelo.addAttribute("salas", salas);
		modelo.addAttribute("listar", "Lista de Cines");

		return "admin/vistas/cine";
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@PostMapping("/buscarCine")
	public String listarCinePorNombre(ModelMap modelo, @RequestParam String nombre) throws Exception {

		try {
			List<Cine> cines = cineServicio.listarCinePorNombre(nombre);
			modelo.addAttribute("cines", cines);

			return "/cine/cine";

		} catch (Exception e) {
			modelo.put("ErrorBuscar", e.getMessage());

			modelo.put("nombre", nombre);

			return "/cine/cine";
		}
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@GetMapping("/agregarCine")
	public String agregarCine(ModelMap modelo) {
		//List<Sala> salas = salaServicio.listarSalas();
		Cine cine = new Cine();
		//modelo.addAttribute("salas", salas);
		modelo.addAttribute("registro", "Registro de Cines");
		modelo.addAttribute("cine", cine);

		return "admin/vistas/cine";
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@PostMapping("/agregarCine")
	public String guardarCine(ModelMap modelo, Cine cine) throws Exception {
		try {
			cineServicio.crear(cine);
			
			return "redirect:/cine";

		} catch (Exception e) {
			modelo.put("error", e.getMessage());
			// devolvemos los valores ingresados al formulario
			modelo.addAttribute(cine);
			modelo.addAttribute("registro", "Registro de Cines");

			return "admin/vistas/cine";
		}

	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@GetMapping("/modificarCine/{id}")
	public String modificarPelicula(ModelMap modelo, @PathVariable Long idCine) {

		Optional<Cine> cine = cineServicio.buscarCinePorId(idCine);
		//@SuppressWarnings("unused")
		//List<Sala> salas = salaServicio.listarSalas();

		modelo.addAttribute("nombre", cine.get().getNombre());
		modelo.addAttribute("direccion", cine.get().getDireccion());
		modelo.addAttribute("telefono", cine.get().getTelefono());

		return "/cine/editarCine";
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@PostMapping("/modificarCine")
	public String modificarCine(ModelMap modelo, Cine cine ) throws Exception {

		try {
			cineServicio.modificar(cine);

			return "redirect:/cine";

		} catch (Exception e) {
			modelo.put("Error", e.getMessage());
			// devolvemos los valores ingresados al formulario

			return "/pelicula/editarCine";
		}
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@GetMapping("/eliminarCine/{id}")
	public String eliminarCinePorId(@PathVariable Long idCine) {

		try {
			cineServicio.eliminarCinePorId(idCine);

			return "redirect:/cine";

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "redirect:/cine";
		}

	}

}