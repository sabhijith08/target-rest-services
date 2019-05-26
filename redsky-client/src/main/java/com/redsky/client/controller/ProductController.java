package com.redsky.client.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.redsky.client.pojo.RequestPayload;
import com.redsky.client.pojo.Response;
import com.redsky.client.service.ProductService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("products")
public class ProductController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    ProductService productService;

    @ApiOperation(value = "Create Product & price")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "search records for tenants", response = Response.class),
            @ApiResponse(code = 500, message = "Internal server error", response = Response.class),
            @ApiResponse(code = 400, message = "Bad Request", response = Response.class) })
    @PostMapping()
    public ResponseEntity<Response> createById(@RequestBody final RequestPayload request) throws Exception {

        LOGGER.debug("Got a request to create the product price details for id {} ", request);
        final Response response = productService.create(request);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @ApiOperation(value = "Get Product & price by ID")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "search records for tenants", response = Response.class),
            @ApiResponse(code = 500, message = "Internal server error", response = Response.class),
            @ApiResponse(code = 400, message = "Bad Request", response = Response.class),
            @ApiResponse(code = 404, message = "Not Found", response = Response.class) })
    @GetMapping("{id}")
    public ResponseEntity<Response> getById(@PathVariable final Integer id) {

        LOGGER.debug("Got a request to get the product & price details for id {} ", id);

        final Response response = productService.getById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @ApiOperation(value = "Update Product & price by ID")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "search records for tenants", response = Response.class),
            @ApiResponse(code = 500, message = "Internal server error", response = Response.class),
            @ApiResponse(code = 400, message = "Bad Request", response = Response.class),
            @ApiResponse(code = 404, message = "Not Found", response = Response.class) })
    @PutMapping("{id}")
    public ResponseEntity<Response> updateById(@PathVariable final String id, @RequestBody final RequestPayload request)
            throws Exception {

        LOGGER.debug("Got a request to update the product price details for id {} ", id);

        final Response response = productService.update(request, id);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

}
