package com.affinion.gce.model;

import com.affinion.gce.model.asset.Asset;
import com.affinion.gce.model.asset.AssetId;
import com.affinion.gce.model.asset.AssetType;
import com.affinion.gce.model.asset.type.PhoneNumber;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import static com.shazam.shazamcrest.matcher.Matchers.*;
import static org.junit.Assert.*;

public class AssetTest {

    private final ObjectMapper mapper = new ObjectMapper();
    private final String phoneNumberAsset = "{\"id\":{\"id\":1234,\"type\":\"CSS_PhoneNumberMobile\"}," +
            "\"memberId\":12345,\"tenantId\":213232,\"active\":true,\"number\":\"1234567890\"}";

    @Test
    public void testPhoneNumberAssetDeserialization() throws IOException {
        Asset deserialized = mapper.readValue(phoneNumberAsset, Asset.class);
        Asset expected = new PhoneNumber(new AssetId(1234L, AssetType.MOBILE_NUMBER),
                12345L, 213232L, true, "1234567890");

        assertEquals(PhoneNumber.class, deserialized.getClass());
        assertThat(expected, sameBeanAs(expected));
    }

    @Test
    public void testPhoneNumberAssetSerialization() throws IOException {
        Asset phoneNumber = new PhoneNumber(new AssetId(1234L, AssetType.MOBILE_NUMBER),
                12345L, 213232L, true, "1234567890");
        String serialized = mapper.writeValueAsString(phoneNumber);
        assertEquals(phoneNumberAsset.trim(), serialized.trim());
    }

    @Test
    public void testFormatter(){
        String request = "{\"attributeName\": \"asset_type\", \"attributeValue\":'{}'}";
        String format = String.format(request, AssetType.DRIVERS_LICENSE.getTypeKey());
        System.out.println(format);
    }
}
