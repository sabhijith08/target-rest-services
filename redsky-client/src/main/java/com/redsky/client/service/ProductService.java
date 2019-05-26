package com.redsky.client.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.redsky.client.exception.ResourceValidationException;
import com.redsky.client.pojo.Product;
import com.redsky.client.pojo.RequestPayload;
import com.redsky.client.pojo.Response;
import com.redsky.client.repository.RedskyPriceRepository;
import com.redsky.client.repository.RedskyProductRepository;
import com.redsky.client.validator.InputRequestValidator;

@Component
public class ProductService {

    private static Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private InputRequestValidator validator;

    @Autowired
    private RedskyProductRepository productRepository;

    @Autowired
    private RedskyPriceRepository priceRepository;

    @Value("${redsky.producturl}")
    private String productUrl;

    @Value("${redsky.priceurl}")
    private String priceUrl;

    public Response create(final RequestPayload request) throws Exception {

        if (validator.validate(request)) {
            // String productPayload = JacksonJsonHelper.serialize(request.getProduct());
            // httpClientManager.doHttpPost(productUrl, productPayload, new Header[1], new ResponseHandler<String>() {
            //
            // @Override
            // public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
            // // TODO Auto-generated method stub
            // return null;
            // }
            // });
            //
            // String pricePayload = JacksonJsonHelper.serialize(request.getCurrent_price());
            // httpClientManager.doHttpPost(productUrl, pricePayload, new Header[1], new ResponseHandler<String>() {
            //
            // @Override
            // public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
            // // TODO Auto-generated method stub
            // return null;
            // }
            // });

            // The above two methods can be called when the URL is valid. Now the below two methods are used
            final Response response = new Response();
            final Response productResponse = productRepository.store(request.getProduct());
            if (productResponse.getCode() == 202) {
                final Response priceResponse = priceRepository.store(request.getProduct().getId(),
                        request.getCurrent_price());
                response.setProduct(productResponse.getProduct());
                response.setCurrent_price(priceResponse.getCurrent_price());
                response.setCode(HttpStatus.CREATED.value());
                response.setMessage(priceResponse.getMessage());
                return response;
            } else {
                return productResponse;
            }

        } else {
            throw new ResourceValidationException("Bad Request", HttpStatus.BAD_REQUEST.value());
        }

    }

    public Response getById(final Integer id) {
        final Product product = productRepository.getById(id);
        final Map<String, Object> price = priceRepository.getById(id);
        final Response response = new Response();
        if (product != null && price != null && price.size() > 0) {
            response.setCode(200);
            response.setMessage("Product Retrieved Successfully");
            response.setCurrent_price(price);
            response.setProduct(product);
            return response;
        } else {
            response.setCode(404);
            response.setMessage("No product exists for this ID");
        }
        return response;
    }

    public Response update(final RequestPayload request, final String id) throws Exception {
        if (validator.validate(request)) {
            final Response response = productRepository.updateStore(request.getProduct());
            if (response.getCode() == 200) {
                return priceRepository.store(request.getProduct().getId(), request.getCurrent_price());
            } else {
                LOGGER.error("Error occured while saving the pruduct .");
                throw new Exception("Error occured while saving the pruduct .");
            }

        } else {
            throw new ResourceValidationException("Bad Request", HttpStatus.BAD_REQUEST.value());
        }

    }

}
