package com.connectors.poke.Domain.Interfaces;

import org.springframework.http.ResponseEntity;

public interface IRestTemplateCustomer {
    String getRestTemplateHttp(String url);
}
