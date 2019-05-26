package com.redsky.client.pojo;

import java.util.Map;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestPayload {

    @NotNull(message = "Tenant Name shouldn't be null")
    private Product product;

    @NotNull(message = "Tenant Name shouldn't be null")
    private Map<String, Object> current_price;

    public Product getProduct() {
        return product;
    }

    public void setProduct(final Product product) {
        this.product = product;
    }

    public Map<String, Object> getCurrent_price() {
        return current_price;
    }

    public void setCurrent_price(final Map<String, Object> current_price) {
        this.current_price = current_price;
    }

}
