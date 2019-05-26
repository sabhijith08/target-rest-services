package com.redsky.client.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import com.redsky.client.exception.ResourceValidationException;
import com.redsky.client.fixture.RequestPayloadFixture;
import com.redsky.client.pojo.RequestPayload;
import com.redsky.client.pojo.Response;
import com.redsky.client.repository.RedskyPriceRepository;
import com.redsky.client.repository.RedskyProductRepository;
import com.redsky.client.validator.InputRequestValidator;

@RunWith(PowerMockRunner.class)
public class ProductServiceTest {

    @Mock
    RedskyProductRepository productRepo;

    @InjectMocks
    RedskyPriceRepository priceRepo;

    @InjectMocks
    InputRequestValidator validator;

    RequestPayloadFixture requestPayloadFixture;

    @Before
    public void setUp() {
        requestPayloadFixture = new RequestPayloadFixture();
    }

    @After
    public void tearDown() {
        requestPayloadFixture = null;
    }

    @Ignore
    @Test
    public void createProduct() throws ResourceValidationException {
        // data
        final RequestPayload payload = requestPayloadFixture.getRequestPayload();
        final Response response = new Response();

        // setup
        when(productRepo.store(ArgumentMatchers.any())).thenReturn(response);

        // execution
        priceRepo.store(payload.getProduct().getId(), payload.getCurrent_price());

        // verify
        verify(validator, times(1)).validate(ArgumentMatchers.any());

    }

}
