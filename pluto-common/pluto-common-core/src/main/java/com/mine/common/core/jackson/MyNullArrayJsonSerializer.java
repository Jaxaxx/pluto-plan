//package com.mine.common.core.jackson;
//
//import com.fasterxml.jackson.core.JsonGenerator;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.JsonSerializer;
//import com.fasterxml.jackson.databind.SerializerProvider;
//
//import java.io.IOException;
//
//
///**
// * @Description
// * @Author
// * @Date
// */
//public class MyNullArrayJsonSerializer extends JsonSerializer {
//    @Override
//    public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
//        if (value == null) {
//            jgen.writeStartArray();
//            jgen.writeEndArray();
//        }
//    }
//}
