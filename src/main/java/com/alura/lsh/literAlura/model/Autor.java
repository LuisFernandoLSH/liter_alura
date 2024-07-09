package com.alura.lsh.literAlura.model;

import jakarta.persistence.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private Integer fechaNacimiento;
    private Integer fechaFallecimiento;
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> libros;

    //Constructores
    public Autor(){}

    public Autor(DatosAutor autor) {
        this.nombre = autor.nombre();
        this.fechaNacimiento = autor.fechaNacimiento();
        this.fechaFallecimiento = autor.fechaFallecimiento();
    }


    //Getters and Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Integer fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Integer getFechaFallecimiento() {
        return fechaFallecimiento;
    }

    public void setFechaFallecimiento(Integer fechaFallecimiento) {
        this.fechaFallecimiento = fechaFallecimiento;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        libros.forEach(l -> l.setAutor(this));
        this.libros = libros;
    }

    //Metodos

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("---------------------\nAutor: " + nombre + '\n');
        if(fechaNacimiento != null)
            result.append("Fecha de nacimiento: ").append(fechaNacimiento).append('\n');
        if(fechaFallecimiento != null)
            result.append("Fecha de nacimiento: ").append(fechaFallecimiento).append('\n');
        result.append("Libros: ");
        List<String> libros = this.libros.stream()
                .map(Libro::getTitulo)
                .toList();
        result.append(Arrays.toString(libros.toArray()));
        result.append("\n---------------------\n");
        return result.toString();
    }

    public void añadirLibro(Libro libro){
        this.libros.add(libro);
    }

    public static List<Autor> combinarAutoresRepetidos(List<Autor> autores){
        Map<String,Autor> autoresMap = new HashMap<>();
        for (Autor a: autores){
            if(autoresMap.containsKey(a.nombre)){
                autoresMap.get(a.nombre).añadirLibro(a.getLibros().get(0));
            } else{
                autoresMap.put(a.nombre,a);
            }
        }
        return new ArrayList<>(autoresMap.values());
    }
}
