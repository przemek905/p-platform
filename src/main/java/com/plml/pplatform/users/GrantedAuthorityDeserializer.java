package com.plml.pplatform.users;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class GrantedAuthorityDeserializer extends JsonDeserializer<Collection<GrantedAuthority>> {

    @Override
    public Collection<GrantedAuthority> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException, JsonProcessingException {

        JsonNode jsonNode = jsonParser.getCodec().readTree(jsonParser);
        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        if (jsonNode.isArray()) {
            for (JsonNode node : jsonNode) {
                authorities.add(new SimpleGrantedAuthority(node.get("authority").asText()));
            }
        }

        return authorities;
    }
}
