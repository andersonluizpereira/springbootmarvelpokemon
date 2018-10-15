package com.connectors.poke.Controller;

import java.util.Arrays;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class PokemonController {
	
	private RestTemplate restTemplate;
	 
		@RequestMapping("/home")
		public String hello() {
			return "Its Working!";
		}
	 
	    @RequestMapping("/api/v1/pokemon/ping")
		public String gethello() {
			return "Pong!";
		}
	    
	    @RequestMapping(value="/api/v1/pokemon/getid/{id}", method = RequestMethod.GET)
		 @ResponseBody
		 public String getPokemon(@PathVariable(value="id") Integer pokeId) {
	    	restTemplate = new RestTemplate();
			     System.out.println(pokeId);
		        log.info("###### Custom PokeApi REST call ######");

		        String url = "https://pokeapi.co/api/v2/pokemon/"+pokeId+"/";
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
