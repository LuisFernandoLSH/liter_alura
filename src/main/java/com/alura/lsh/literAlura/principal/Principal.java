package com.alura.lsh.literAlura.principal;

import com.alura.lsh.literAlura.model.Autor;
import com.alura.lsh.literAlura.model.DatosLibro;
import com.alura.lsh.literAlura.model.Idioma;
import com.alura.lsh.literAlura.model.Libro;
import com.alura.lsh.literAlura.repository.LibroRepository;
import com.alura.lsh.literAlura.service.ConvertidorDatos;
import com.alura.lsh.literAlura.service.IConsumoAPI;

import java.util.*;

public class Principal {
    private final String URL_BASE = "https://gutendex.com/books/?search=";
    private final String ESPACIO_URL = "%20";

    private LibroRepository repository;
    private Scanner teclado = new Scanner(System.in);
    private IConsumoAPI consumoAPI;
    private ConvertidorDatos convertidorDatos = new ConvertidorDatos();


    public Principal(IConsumoAPI consumoAPI, LibroRepository repository){
        this.consumoAPI = consumoAPI;
        this.repository = repository;
    }

    public void mostrarMenu() {
        var opcion = -1;
        while (opcion != 0) {
            opcion = -1;
            var menu = """
                    
                    ----------------Bienvenido a LiterAlura----------------
                    Selecciona la opción por su número:
                    1 - Buscar libro por titulo
                    2 - Lista de libros registrados
                    3 - Lista de autores registrados
                    4 - Lista de autores vivos en determinado año
                    5 - Lista de libros por idioma
                                  
                    0 - Salir
                    """;
            System.out.println(menu);
            try {
                opcion = teclado.nextInt();
            } catch (InputMismatchException ignored){}
            teclado.nextLine();

            switch (opcion){
                case 1:
                    buscarLibro();
                    break;
                case 2:
                    listaDeLibrosRegistrados();
                    break;
                case 3:
                    listaDeAutoresRegistrados();
                    break;
                case 4:
                    listaDeAutoresVivosEnDeterminadoAnno();
                    break;
                case 5:
                    listaLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Selecciona una opción valida");
            }
        }
        teclado.close();
    }

    private DatosLibro getDatosLibros(){
        //Solicitamos el titulo del libro que se desea buscar en la API
        System.out.println("Escribe el titulo del libro que deseas buscar");
        var tituloLibro = teclado.nextLine();

        //Comprobamos si ya existe en la base de datos
        Optional<Libro> libroBuscado = repository.findByTituloContainsIgnoreCase(tituloLibro);
        if (libroBuscado.isEmpty()){
            //Solicitamos respuesta de la API y traemos el Json
            var json = consumoAPI.obtenerDatos(URL_BASE+tituloLibro.replace(" ",ESPACIO_URL).toLowerCase());
            System.out.println(json);


            //Transformamos el Json a objeto Java
            return convertidorDatos.obtenerDatos(json, DatosLibro.class);
        }
        return null;
    }

    public void buscarLibro(){
        DatosLibro datosLibro = getDatosLibros();
        Libro libro = null;

        if(datosLibro != null){
            libro = new Libro(datosLibro);
            repository.save(libro);
            System.out.println("\n"+libro+"\n");
        } else{
            System.out.println("\nEl libro buscado ya esta registrado...");
        }
    }

    public void listaDeLibrosRegistrados(){
        List<Libro> libros = repository.findAll();
        System.out.println("\n\nLibros registrados:");
        libros.forEach(System.out::println);
    }

    public void listaDeAutoresRegistrados(){
        List<Autor> autores = Autor.combinarAutoresRepetidos(repository.obtenerAutores());
        System.out.println("\n\nAutores registrados:");
        autores.forEach(System.out::println);
    }

    public void listaDeAutoresVivosEnDeterminadoAnno(){
        List<Autor> autores = Autor.combinarAutoresRepetidos(repository.obtenerAutores());
        List<Autor> autoresVivos = null;
        int fecha = 0;
        while (true) {
            System.out.println("\nEscribe el titulo del libro que deseas buscar");
            try {
                fecha = teclado.nextInt();
                if (fecha == 0)
                    throw new InputMismatchException();
                else
                    break;
            } catch (InputMismatchException ignored) {
                System.out.println("Introduce un año valido");
            }
            teclado.nextLine();
        }

        int finalFecha = fecha;
        autoresVivos = autores.stream()
                .filter(a -> a.getFechaNacimiento() != null )
                .filter(a -> a.getFechaNacimiento() <= finalFecha)
                .filter(a -> a.getFechaFallecimiento() != null)
                .filter(a -> a.getFechaFallecimiento() >= finalFecha)
                .toList();
        if(!autoresVivos.isEmpty()) {
            System.out.println("\nAutores vivos en el año "+fecha);
            autoresVivos.forEach(System.out::println);
        } else
            System.out.println("No se encontraron autores vivos en el año "+fecha);
    }

    public void listaLibrosPorIdioma(){
        String opcion = "";
        boolean flag = true;
        while (flag){
            String menuIdiomas = """
                    en - Inglés
                    es - Español
                    fr - Francés
                    pt - Portugués
                    """;
            System.out.println("\nIngresa la contracción del idioma para buscar los libros:");
            System.out.println(menuIdiomas);
            opcion = teclado.nextLine();

            switch (opcion){
                case "en":
                case "es":
                case "fr":
                case "pt":
                    flag = false;
                    break;
                default:
                    System.out.println("Ingresa una opción valida");
            }
        }

        Idioma idioma = Idioma.formString(opcion);
        var libros = repository.findByIdioma(idioma);
        if(!libros.isEmpty()) {
            System.out.println("\nLibros escritos en el idioma "+idioma.getIdioma());
            System.out.println("Total de libros encontrados: "+libros.size()+"\n");
            libros.forEach(System.out::println);
        } else {
            System.out.println("\nNo se encontraron libros escritos en el idioma "+idioma.getIdioma()+" registrados");
        }
    }
}
