package com.redsky.client.repository;

import java.util.HashSet;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.redsky.client.pojo.Product;
import com.redsky.client.pojo.Response;

@Component
public class RedskyProductRepository {

    Set<Product> productList = new HashSet<>();

    public Response store(final Product product) {
        boolean productExists = false;

        for (final Product item : productList) {
            final int id = item.getId();
            final int productId = product.getId();
            if (id == productId) {
                productExists = true;
                break;
            }

        }
        if (productExists) {
            final Response response = new Response("Product-id already exists", HttpStatus.CONFLICT.value());
            return response;
        } else {
            productList.add(product);
            final Response response = new Response("Product-id Added to product repo", HttpStatus.ACCEPTED.value());
            response.setProduct(product);
            return response;
        }

    }

    public Product getById(final Integer id) {
        for (final Product product : productList) {
            final int productId = product.getId();
            if (productId == id) { return product;

            }
        }
        return null;
    }

    public Response updateStore(final Product product) {
        productList.add(product);
        final Response response = new Response("Product-id Updated to product repo", HttpStatus.ACCEPTED.value());
        return response;
    }

}
