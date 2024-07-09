package com.alura.lsh.literAlura.model;

public enum Idioma {
    EN("en", "Inglés"),
    ES("es", "Español"),
    FR("fr", "Francés"),
    PT("pt", "Portugués");

    private String contracción;
    private String idioma;

    Idioma(String contraccion, String idioma) {
        this.contracción = contraccion;
        this.idioma = idioma;
    }

    public String getIdioma(){return idioma;}

    public static Idioma formString(String text){
        for (Idioma i: Idioma.values()){
            if(i.contracción.equalsIgnoreCase(text))
                return i;
        }
        throw new IllegalArgumentException("Ningún idioma encontrado: "+text);
    }
}