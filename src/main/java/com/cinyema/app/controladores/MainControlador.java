package com.cinyema.app.controladores;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cinyema.app.Utility;
import com.cinyema.app.entidades.Usuario;
import com.cinyema.app.servicios.ActorServicio;
import com.cinyema.app.servicios.DirectorServicio;
import com.cinyema.app.servicios.PeliculaServicio;
import com.cinyema.app.servicios.TicketServicio;
import com.cinyema.app.servicios.UsuarioServicio;

@Controller
@RequestMapping({ "/cinyema", "/" })
public class MainControlador {

	@Autowired
	private UsuarioServicio usuarioServicio;
	
	@Autowired
	private PeliculaServicio peliculaServicio;
	
	@Autowired
	private DirectorServicio directorServicio;
	
	@Autowired
	private ActorServicio actorServicio;
	
	@Autowired
	private TicketServicio ticketServicio;

	@GetMapping()
	public String index(ModelMap modelo) {
		modelo.addAttribute("peliculas", peliculaServicio.listarPeliculasActivas());
		return "index";
	}

	@GetMapping("/login")
	public String login(ModelMap modelo, @RequestParam(required = false) String error,
			@RequestParam(required = false) String nombreDeUsuario, @RequestParam(required = false) String logout) {
		if (error != null) {
			modelo.addAttribute("error", "El usuario o la contraseña son inválidos. Vuelva a intentar");
		}
		if (nombreDeUsuario != null) {
			modelo.addAttribute("nombreDeUsuario", nombreDeUsuario);
		}
		return "vistas/login";
	}

	@GetMapping("/registrar")
	public String registrar(ModelMap modelo) {
		try {
			modelo.addAttribute("registrar", "Registrar Usuario");
			modelo.addAttribute(usuarioServicio.registrarVacio());
			return "vistas/registro";
		} catch (Exception e) {
			e.printStackTrace();
			modelo.addAttribute("registrar", "Registrar Usuario");
			modelo.addAttribute(usuarioServicio.registrarVacio());
			modelo.put("error", e.getMessage());
			return "vistas/registro";
		}
	}

	@PostMapping("/registrar")
	public String registrar(ModelMap modelo, Usuario usuario, HttpServletRequest request) throws Exception {
		try {
			usuarioServicio.registrar(usuario);
			String siteURL = Utility.getSiteURL(request);
			usuarioServicio.enviarMailVerificacion(usuario, siteURL);
			return "vistas/verificarMail";
		} catch (Exception e) {
			e.printStackTrace();
			modelo.addAttribute("registrar", "Registrar Usuario");
			modelo.addAttribute("usuario", usuario);
			modelo.put("error", e.getMessage());
			return "vistas/registro";
		}
	}
	

	@GetMapping("/verify")
	public String verificarCuenta(ModelMap modelo, @Param("code") String code) {		
	    if (usuarioServicio.validarCodigo(code)) {
	    	return "vistas/verificacionRealizada";
	    } else {
	    	return "vistas/verificacionFallida";
	    }	
	}    

	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@GetMapping("/admin")
	public String adminPanel(ModelMap modelo) throws Exception {
		try {
			modelo.put("peliculaTotal", peliculaServicio.cantidadPeliculaTotal());
			modelo.put("peliculaAlta", peliculaServicio.cantidadPeliculaAlta());
			modelo.put("peliculaBaja", peliculaServicio.cantidadPeliculaBaja());
			modelo.put("usuarioTotal", usuarioServicio.totalUsuario());
			modelo.put("usuarioAlta", usuarioServicio.totalAlta());
			modelo.put("usuarioBaja", usuarioServicio.totalBaja());
			modelo.put("ticketTotal", ticketServicio.totalTicket());
			modelo.put("directorTotal", directorServicio.totalDirector());
			modelo.put("actorTotal", actorServicio.obtenerCantidadActores());
			return "vistas/admin/panelAdmin";
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return "redirect:/admin";
		}
	}
	
	@PreAuthorize("hasAnyRole('ROLE_USUARIO')")
	@GetMapping("/panelUsuario")
	public String panelUsuario(ModelMap modelo, Authentication user) {
		try {
			Usuario usuario = usuarioServicio.obtenerUsuarioPorNombre(user.getName());
			Date fechaNacimiento = new SimpleDateFormat("yyyy-MM-dd").parse(usuario.getFechaNacimiento());
			modelo.put("fechaNacimiento", fechaNacimiento);
			modelo.addAttribute("usuario", usuario);
			return "vistas/usuario/panelUsuario";
		} catch (Exception e) {
			modelo.addAttribute("error", "No se encontro el usuario");
			return "redirect:/";
		}
	}
}
