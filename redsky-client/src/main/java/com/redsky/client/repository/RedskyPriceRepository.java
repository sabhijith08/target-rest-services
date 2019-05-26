package com.redsky.client.repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.redsky.client.pojo.Response;

@Component
public class RedskyPriceRepository {

    Map<Integer, Map<String, Object>> priceMap = new HashMap<>();

    public Response store(final Integer id, final Map<String, Object> current_price) {

        priceMap.put(id, current_price);
        final Response response = new Response("Price Added to price repo tagged with product",
                HttpStatus.ACCEPTED.value());
        response.setCurrent_price(current_price);
        return response;

    }

    public Map<String, Object> getById(final Integer id) {

        return priceMap.get(id);

    }

}
