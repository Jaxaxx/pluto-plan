package com.mine.common.security.exception;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import lombok.SneakyThrows;

/**
 * @Description
 * @Author
 * @Date
 */
public class MyAuth2ExceptionSerializer extends StdSerializer<MyAuth2Exception> {

    public MyAuth2ExceptionSerializer() {
        super(MyAuth2Exception.class);
    }

    @Override
    @SneakyThrows
    public void serialize(MyAuth2Exception value, JsonGenerator gen, SerializerProvider provider) {
        gen.writeStartObject();
        gen.writeObjectField("code", 1);
        gen.writeStringField("msg", value.getMessage());
//        gen.writeStringField("data", value.getErrorCode());
        gen.writeEndObject();
    }
}