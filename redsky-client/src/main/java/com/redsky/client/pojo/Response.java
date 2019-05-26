package com.redsky.client.pojo;

import java.util.Map;

public class Response {

    private Product product;

    private Map<String, Object> current_price;

    private Integer code;

    private String message;

    public Response(final String message, final int code) {
        this.message = message;
        this.code = code;
    }

    public Response() {
        super();
    }

    public Map<String, Object> getCurrent_price() {
        return current_price;
    }

    public void setCurrent_price(final Map<String, Object> current_price) {
        this.current_price = current_price;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(final Product product) {
        this.product = product;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(final Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

}
