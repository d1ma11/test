package service.helper;

import annotation.Logging;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.util.Base64;

public class Base64Deserializer extends JsonDeserializer<String> {
    @Override
    @Logging(value = "method Deserialize()", entering = true, exiting = true)
    public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String base64String = p.getCodec().readValue(p, String.class);
        byte[] decodedBytes = Base64.getDecoder().decode(base64String);
        return new String(decodedBytes);
    }
}
