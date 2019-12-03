package com.affinion.gce.model.asset.type;

import com.affinion.gce.jpa.entity.AssetAttributeEntity;
import com.affinion.gce.model.asset.Asset;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public abstract class BaseAssetTest<D extends Asset> {
    private final ObjectMapper mapper = new ObjectMapper();

    public abstract Class<D> assetClazz();

    public abstract D newAsset();

    public abstract List<AssetAttributeEntity> expectedAttributes();

    public abstract String serialized();

    @Test
    public void testAssetSerialization() throws JsonProcessingException {
        D asset = newAsset();

        String actual = mapper.writeValueAsString(asset);

        assertEquals(serialized(), actual);
    }

    @Test
    public void testAssetDeserialization() throws IOException {
        D actual = mapper.readValue(serialized(), assetClazz());

        assertThat(actual, sameBeanAs(newAsset()));
    }

    @Test
    public void testAssetAttributes(){
        List<AssetAttributeEntity> expected = expectedAttributes();

        List<AssetAttributeEntity> actual = newAsset().attributes();

        assertThat(actual, sameBeanAs(expected));
    }
}
