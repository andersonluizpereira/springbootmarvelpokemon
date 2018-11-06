package com.connectors.poke.Controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Slf4j
@RestController
public class StarWarsController {


    @RequestMapping("/starwars")
    public String hello() {
        return "Its Working!";
    }

    @RequestMapping("/api/v1/starwars/ping")
    public String gethello() {
        return "Pong!";
    }

    @RequestMapping(value = "/api/v1/starw/getid/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String getPokemon(@PathVariable(value = "id") Integer id) {
        RestTemplate restTemplate = new RestTemplate();

        log.info("###### Custom PokeApi REST call ######");

        String url = "https://swapi.co/api/people/" + id + "/";
        log.info("Url: {}", url);

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
            HttpEntity<String> entity = new HttpEntity<String>(headers);

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
