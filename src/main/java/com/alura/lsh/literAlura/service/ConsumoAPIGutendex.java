package com.alura.lsh.literAlura.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConsumoAPIGutendex extends ConsumoAPI {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String obtenerDatos(String url) {
        String json = super.obtenerDatos(url);
        String jsonLibro = "";
        try {
            JsonNode jsonNode = objectMapper.readTree(json);
            JsonNode jsonLibros = jsonNode.get("results");
            jsonLibro = String.valueOf(jsonLibros.get(0));
            System.out.println(jsonLibro);
        } catch (JsonProcessingException e){
            throw new RuntimeException(e);
        }
        return jsonLibro;
    }
}
