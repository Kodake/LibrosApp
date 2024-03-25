package com.librosapp;

import com.librosapp.presentacion.LibroForm;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.awt.*;

@SpringBootApplication
public class LibrosApp {

	public static void main(String[] args) {
		ConfigurableApplicationContext contexto = new SpringApplicationBuilder(LibrosApp.class)
				.headless(false)
				.web(WebApplicationType.NONE)
				.run(args);
		// Ejecutar formulario
		EventQueue.invokeLater(() -> {
			LibroForm libroForm = contexto.getBean(LibroForm.class);
			libroForm.setVisible(true);
		});
	}

}
