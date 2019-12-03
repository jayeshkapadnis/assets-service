package com.affinion.gce.jackson;

import com.affinion.gce.model.asset.AssetType;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import com.fasterxml.jackson.databind.jsontype.impl.AsPropertyTypeDeserializer;
import com.fasterxml.jackson.databind.node.TreeTraversingParser;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.IOException;

public class AssetTypeDeserializer extends AsPropertyTypeDeserializer {
    public AssetTypeDeserializer(JavaType bt, TypeIdResolver idRes,
                                 String typePropertyName, boolean typeIdVisible,
                                 JavaType defaultImpl) {
        super(bt, idRes, typePropertyName, typeIdVisible, defaultImpl);
    }

    public AssetTypeDeserializer(JavaType bt, TypeIdResolver idRes, String typePropertyName, boolean typeIdVisible, JavaType defaultImpl, JsonTypeInfo.As inclusion) {
        super(bt, idRes, typePropertyName, typeIdVisible, defaultImpl, inclusion);
    }

    public AssetTypeDeserializer(AsPropertyTypeDeserializer src, BeanProperty property) {
        super(src, property);
    }

    @Override
    public TypeDeserializer forProperty(
            final BeanProperty prop) {
        return (prop == _property) ? this : new AssetTypeDeserializer(this, prop);
    }

    @Override
    public Object deserializeTypedFromObject(
            final JsonParser jp, final DeserializationContext ctxt) throws IOException {
        JsonNode node = jp.readValueAsTree();
        Class<?> subType = findSubType(node);
        JavaType type = TypeFactory.defaultInstance().constructSimpleType(subType, null);

        JsonParser jsonParser = new TreeTraversingParser(node, jp.getCodec());
        if (jsonParser.getCurrentToken() == null) {
            jsonParser.nextToken();
        }
        JsonDeserializer<Object> deser = ctxt.findContextualValueDeserializer(type, _property);
        return deser.deserialize(jsonParser, ctxt);
    }

    private Class<?> findSubType(JsonNode node) {
        String assetType = node.get("id").get("type").asText();
        AssetType type = AssetType.fromValue(assetType);
        if(type != null){
            return type.domain();
        }
        return null;
    }
}
