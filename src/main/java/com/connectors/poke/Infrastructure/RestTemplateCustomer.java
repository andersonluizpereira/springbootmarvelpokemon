package com.connectors.poke.Infrastructure;

import com.connectors.poke.Domain.Interfaces.IRestTemplateCustomer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Slf4j
public abstract class RestTemplateCustomer implements IRestTemplateCustomer {

    private RestTemplate restTemplate;
    HttpEntity<String> entity = null;


    public RestTemplateCustomer() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
        entity = new HttpEntity<String>(headers);

        ResponseEntity<String> responseEntity = null;
    }

    @Override
    public String getRestTemplateHttp(String url) {

        restTemplate = new RestTemplate();

        log.info("###### Custom PokeApi REST call ######");

        log.info("Url: {}", url);

        try {

            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                return responseEntity.getBody();
            } else {
                return null;
            }
        } catch (Throwable t) {
            if (t instanceof HttpClientErrorException) {
                HttpClientErrorException e = (HttpClientErrorException) t;
                log.error(e.getResponseBodyAsString());
            }
            log.error(t.getMessage());
            return null;
        }

    }
}
