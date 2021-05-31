package co.ke.mymobi.validations;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class ToLowerCaseDeserializer extends StdDeserializer<String> {

    private static final long serialVersionUID = 7527542687158493910L;

    public ToLowerCaseDeserializer() {
        super(String.class);
    }

    @Override
    public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        return _parseString(p, ctxt).toLowerCase();
    }

}