package com.alura.lsh.literAlura.repository;

import com.alura.lsh.literAlura.model.Autor;
import com.alura.lsh.literAlura.model.Idioma;
import com.alura.lsh.literAlura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro,Long> {
    Optional<Libro> findByTituloContainsIgnoreCase(String tituloLibro);

    @Query("SELECT a FROM Libro l JOIN l.autor a")
    List<Autor> obtenerAutores();

    List<Libro> findByIdioma(Idioma idioma);

    @Query("SELECT a FROM Libro l JOIN l.autor a WHERE a.nombre = :nombre")
    Autor obtenerAutorPorNombre(String nombre);
}
