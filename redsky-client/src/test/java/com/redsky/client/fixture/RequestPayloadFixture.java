package com.redsky.client.fixture;

import java.util.HashMap;
import java.util.Map;

import com.redsky.client.pojo.Product;
import com.redsky.client.pojo.RequestPayload;

public class RequestPayloadFixture {

    public RequestPayloadFixture() {
        getRequestPayload();
    }

    public RequestPayload getRequestPayload() {

        final RequestPayload payload = new RequestPayload();
        final Product product = new Product();
        product.setId(123445);
        product.setName("Ayurvedic Cream");
        final Map<String, Object> current_price = new HashMap<>();
        current_price.put("value", 12.78);
        current_price.put("currency_code", "USA");
        payload.setCurrent_price(current_price);
        return payload;

    }

}
