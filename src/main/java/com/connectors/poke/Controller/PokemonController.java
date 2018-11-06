package com.connectors.poke.Controller;

import com.connectors.poke.Infrastructure.DataAgents.PokeDataAgent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RestController
public class PokemonController {
	
	private RestTemplate restTemplate;
	private PokeDataAgent pokeDataAgent;

	@Value("${server.urlpoke}")
	public String server_url;




	@RequestMapping("/api/home")
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
			   pokeDataAgent = new PokeDataAgent();


		        log.info("###### Custom PokeApi REST call ######");

		        String url = server_url+pokeId+"/";
		        log.info("Url: {}", url);

		        try {
					return pokeDataAgent.getRestTemplateHttp(url);
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

