package com.alura.lsh.literAlura.service;

public interface IConvertidorDatos {
    public <T> T obtenerDatos(String json, Class<T> className);
}
