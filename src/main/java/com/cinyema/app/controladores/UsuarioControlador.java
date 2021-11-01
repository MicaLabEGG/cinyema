package com.cinyema.app.controladores;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cinyema.app.entidades.Usuario;
import com.cinyema.app.enumeraciones.Rol;
import com.cinyema.app.servicios.UsuarioServicio;

@Controller
@RequestMapping("/usuario")
public class UsuarioControlador {

	@Autowired
	UsuarioServicio usuarioServicio;
	
	@SuppressWarnings("finally")
	@GetMapping("")
	public String mostrarUsuarios(ModelMap modelo) throws Exception {
		
		try {
			List<Usuario> listaUsuario = usuarioServicio.buscarUsuarios();
			modelo.addAttribute("usuario", listaUsuario);
		} 
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			return "lista_usuarios";
		}
	}
	
	@GetMapping("/guardar")
	public String guardar() {
		return "guardar_usuario";
	}
	
	@SuppressWarnings("finally")
	@PostMapping("/guardar")
	public String guardarUsuario(ModelMap modelo, @RequestParam("nombre") String nombre, @RequestParam("mail") String mail, @RequestParam("nombreDeUsuario") String nombreDeUsuario, @RequestParam("contrasenia") String contrasenia, @RequestParam("alta") Boolean alta, @RequestParam("fechaNacimiento") Date fechaNacimiento, @RequestParam Rol rol)
			throws Exception{
		
		try {
			usuarioServicio.registroUsuario(nombre, mail, nombreDeUsuario, contrasenia, alta, fechaNacimiento, rol);
			modelo.put("exito", "ingreso exitoso");
		}
		catch (Exception e){
			System.out.println(e.getMessage());
			modelo.put("error", "Error al ingresar los datos del usuario");
			return "guardar_director";
		}
		finally {
			return "redirect:/lista_usuarios";
		}
	}
	
	@SuppressWarnings("finally")
	@GetMapping("/modificar/{id}")
	public String modificar(ModelMap modelo, @PathVariable Long id) throws Exception{
		try {
			Usuario usuario = usuarioServicio.obtenerUsuario(id);
			modelo.addAttribute("usuario", usuario);
		}
		catch (Exception e){
			System.out.println(e.getMessage());
			modelo.put("error", "Falta algun dato");
			return "modificar_usuario";
		}
		finally {
			return "redirect:/lista_usuarios";
		}
	}
	
	@SuppressWarnings("finally")
	@PostMapping("/modificar/{id}")
	public String modificarUsuario(ModelMap modelo, @PathVariable Long id, @RequestParam String nombre, @RequestParam String mail, @RequestParam String nombreDeUsuario, @RequestParam String contrasenia, @RequestParam Boolean alta, @RequestParam Date fechaNacimiento, @RequestParam Rol rol)
			throws Exception{
		
		try {
			usuarioServicio.modificarUsuario(id, nombre, mail, nombreDeUsuario, contrasenia, alta, fechaNacimiento, rol);
			modelo.put("exito", "Modificacion exitosa");
		}
		catch(Exception e){
			System.out.println(e.getMessage());
			modelo.put("error", "Falta algun dato al ingresar usuario");
			return "modificar_usuarios";
		}
		finally {
			return "redirect:/lista_usuarios";
		}
	}
	
	
	
}
