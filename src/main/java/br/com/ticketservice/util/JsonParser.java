package br.com.ticketservice.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonParser {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static <T> T fromJsonToEntity(String json, Class<T> entity) {
        return mapper.convertValue(json, entity);
    }

    public static String toJson(Object obj) throws JsonProcessingException {
        return mapper.writeValueAsString(obj);
    }


}