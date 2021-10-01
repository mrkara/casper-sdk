package com.syntifi.casper.sdk.jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import com.fasterxml.jackson.databind.jsontype.impl.AsPropertyTypeDeserializer;
import com.fasterxml.jackson.databind.node.TreeTraversingParser;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.syntifi.casper.sdk.exception.NoSuchTypeException;
import com.syntifi.casper.sdk.model.deploy.transform.TypeData;

/**
 * Core Deserializer for the CLValue property. This deserializer is used by the {@link CLTypeResolver} 
 * to return the correct CLType object in Java depending on the cl_type sent over json
 * 
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @since 0.0.1
 * @see CLValue
 */
public class TransformDeserializer extends AsPropertyTypeDeserializer {

    public TransformDeserializer(final JavaType bt, final TypeIdResolver idRes, final String typePropertyName,
            final boolean typeIdVisible, JavaType defaultImpl) {
        super(bt, idRes, typePropertyName, typeIdVisible, defaultImpl);
    }

    public TransformDeserializer(final AsPropertyTypeDeserializer src, final BeanProperty property) {
        super(src, property);
    }

    @Override
    public TypeDeserializer forProperty(final BeanProperty prop) {
        return (prop == _property) ? this : new TransformDeserializer(this, prop);
    }

    @Override
    public Object deserializeTypedFromObject(final JsonParser jp, final DeserializationContext ctxt)
            throws IOException {
        JsonNode node = jp.readValueAsTree();
        Class<?> subType;
        try {
            String transformType = node.isObject() ? node.fieldNames().next() : node.asText();
            subType = TypeData.getClassByName(transformType);
        } catch (NoSuchTypeException e) {
            throw new IOException("Parse error", e);
        }
        TypeFactory factory = new ObjectMapper().getTypeFactory();
        JavaType type = factory.constructType(subType);

        try (JsonParser jsonParser = new TreeTraversingParser(node, jp.getCodec())) {
            if (jsonParser.getCurrentToken() == null) {
                jsonParser.nextToken();
            }
            JsonDeserializer<Object> deser = ctxt.findContextualValueDeserializer(type, _property);
            return deser.deserialize(jsonParser, ctxt);
        }
    }

}
