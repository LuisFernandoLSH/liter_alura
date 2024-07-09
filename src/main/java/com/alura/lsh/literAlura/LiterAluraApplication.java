package com.alura.lsh.literAlura;

import com.alura.lsh.literAlura.principal.Principal;
import com.alura.lsh.literAlura.repository.LibroRepository;
import com.alura.lsh.literAlura.service.ConsumoAPIGutendex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiterAluraApplication implements CommandLineRunner {

	@Autowired
	LibroRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(LiterAluraApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(new ConsumoAPIGutendex(), repository);
		principal.mostrarMenu();
	}
}
