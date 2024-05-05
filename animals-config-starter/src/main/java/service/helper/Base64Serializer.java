package service.helper;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.Base64;

public class Base64Serializer extends JsonSerializer<String> {
    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        byte[] encodedBytes = Base64.getEncoder().encode(value.getBytes());
        gen.writeString(new String(encodedBytes));
    }
}
