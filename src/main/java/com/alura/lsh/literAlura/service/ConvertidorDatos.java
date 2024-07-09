package com.alura.lsh.literAlura.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConvertidorDatos implements IConvertidorDatos {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T obtenerDatos(String json, Class<T> className) {
        try{
            return objectMapper.readValue(json,className);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
