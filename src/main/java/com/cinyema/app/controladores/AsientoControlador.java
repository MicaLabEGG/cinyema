package com.cinyema.app.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cinyema.app.entidades.Asiento;
import com.cinyema.app.servicios.AsientoServicio;

@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping("/asiento")
public class AsientoControlador {

	
	@Autowired
	private AsientoServicio asientoServicio;
	
	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@GetMapping("")
	public String listarAsiento(ModelMap modelo) {
		try {
		    modelo.addAttribute("listar", "Lista de Asientos");
		    modelo.addAttribute("asientos", asientoServicio.listar());
		    return "vistas/admin/asiento";
		}catch(Exception e){
			e.printStackTrace();
            modelo.addAttribute("listar", "Lista de Asientos");
            modelo.put("error", e.getMessage());
			return "redirect:/asiento";
			}
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@GetMapping("/registrar")
	public String registrarAsiento(ModelMap modelo) {
		try {
		modelo.addAttribute("registrar", "Registrar Asientos");
	    modelo.addAttribute("asientos", asientoServicio.registrarVacio());
	    return "vistas/admin/asiento";
		}catch(Exception e) {
			e.printStackTrace();
			modelo.addAttribute("registrar", "Registrar Asientos");
			modelo.put("error", e.getMessage());
			return "redirect:/asiento";
		}
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@PostMapping("/registrar")
	public String registrarAsiento(ModelMap modelo, Asiento asiento ) throws Exception {
		try {
			asientoServicio.registrar(asiento);
			return "redirect:/asiento";
		}catch(Exception e) {
			e.printStackTrace();
			modelo.addAttribute("registrar", "Registrar Asiento");
			modelo.addAttribute("asiento", asiento);
			modelo.put("error", e.getMessage());
			return "redirect:/asiento";
		}
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@GetMapping("/editar/{id}")
	public String editarAsiento(ModelMap modelo, @PathVariable Long idAsiento) {
		try {
		    modelo.addAttribute("editar", "Editar Asientos");	   
		    modelo.addAttribute("asientos", asientoServicio.listar());
		    return "vistas/admin/asiento";
		}catch (Exception e) {
			e.printStackTrace();
			modelo.addAttribute("editar", "Editar Asientos");
			modelo.put("error", e.getMessage());
			return "redirect:/asiento" ;
		}
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@PostMapping("/editar")
	public String editarAsiento(ModelMap modelo, Asiento asiento ) throws Exception {
		try {
			asientoServicio.editar(asiento);
			return "redirect:/asiento";
		} catch (Exception e) {
			e.printStackTrace();
			modelo.addAttribute("editar", "Editar Asiento");
			modelo.addAttribute("asiento", asiento);
			modelo.put("error", e.getMessage());
			return "redirect:/asiento";
		}
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@GetMapping("/eliminar/{id}")
	public String eliminarAsiento(@PathVariable Long idAsiento) {
		try {
			asientoServicio.eliminar(idAsiento);
			return "redirect:/asiento";
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
			return "redirect:/asiento";
		}
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@GetMapping("/baja/{idAsiento}")
	public String ocuparAsiento(@PathVariable Long idAsiento) throws Exception {
		asientoServicio.ocuparAsiento(idAsiento);
		return "redirect:/asiento";
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@GetMapping("/alta/{idAsiento}")
	public String liberarAsiento(@PathVariable Long idAsiento) throws Exception {
		asientoServicio.liberarAsiento(idAsiento);
		return "redirect:/asiento";
	}
	
}
