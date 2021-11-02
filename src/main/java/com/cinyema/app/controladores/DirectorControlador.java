package com.cinyema.app.controladores;

import java.util.List;

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
@RequestMapping("/director")
public class DirectorControlador {

	@Autowired
	DirectorServicio directorServicio;


	@GetMapping("")
	public String mostrarDirectores(ModelMap modelo) throws Exception {

		try {
			List<Director> listaDirector = directorServicio.listarDirectores();
			modelo.addAttribute("listar", "Lista Directores");
			modelo.addAttribute("directores", listaDirector);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "admin/vistas/director";
		} 
			return "admin/vistas/director";
		

	}

	@GetMapping("/registrar")
	public String guardar(ModelMap modelo) {
		modelo.addAttribute("registrar", "Registrar Director");
		return "admin/vistas/director";
	}

	
	@PostMapping("/registrar")
	public String guardarDirector(ModelMap modelo, @RequestParam("nombre") String nombre, @RequestParam Pais pais)
			throws Exception {

		try {
			Director director = directorServicio.crearDirector(nombre, pais);
			modelo.put("director", director);
			modelo.put("exito", "Ingreso exitoso");
			return "redirect:/director";
		} catch (Exception e) {
			System.out.println(e.getMessage());
			modelo.put("error", "Error al ingresar los datos del director");
			return "redirect:/director";
		} 
	}

	
	@GetMapping("/editar/{id}")
	public String modificar(@PathVariable Long id, ModelMap modelo) {

		try {
			Director director = directorServicio.obtenerDirectorPorId(id);
			modelo.addAttribute("editar", "Editar Directores");
			modelo.addAttribute("director", director);
			return "admin/vistas/director";
		} catch (Exception e) {
			System.out.println(e.getMessage());
			modelo.put("error", "Falta algun dato");
			return "modificar_director";
		} 

	}

	
	@PostMapping("/editar/{id}")
	public String modificarDirector(ModelMap modelo, @PathVariable Long id, @RequestParam String nombre, @RequestParam Pais pais) throws Exception {

		try {
			directorServicio.modificarDirector(id, nombre, pais);
			modelo.put("exito", "Modificacion exitosa");
			return "redirect:/director";
		} catch (Exception e) {
			System.out.println(e.getMessage());
			modelo.put("error", "Falta algun dato al ingresar un Director");
			return "redirect:/director";
		} 

	}
	
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable Long id) {
		try {
			directorServicio.eliminarDirector(id);
			return "redirect:/director";		
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "redirect:/director";	
		}
		
	}

}
