package com.connectors.poke.Controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

@Slf4j
@RestController
public class MarvelController {

    private RestTemplate restTemplate;
    @Value("${server.urlmarvel}")
    private static String server_url;

    @RequestMapping("/marvel")
    public String hello() {
        return "Its Working Marvel!";
    }

    @RequestMapping("/api/v1/marvel/ping")
    public String gethello() {
        return "Pong Marvel!";
    }

    @RequestMapping(value="/api/v1/marvel/limit/{limit}", method = RequestMethod.GET)
    @ResponseBody
    public String getMarvel(@PathVariable(value="limit") Integer limit) throws UnsupportedEncodingException {

        Instant instant = Instant.now();
        long timeStampMillis = instant.toEpochMilli();

        String ts = converterLongToString(timeStampMillis);
        String apiKey = "f2aef569ee6f38276c60b9264d1dec2b";
        String HashKeys ="0126a49f00e1063a8f441de67dfd56090cb24be6";

        String textoMd5 = new StringBuilder(ts).append(HashKeys).append(apiKey).toString();

        String hash = converterParaMd5(textoMd5);

        restTemplate = new RestTemplate();
        System.out.println(limit);
        log.info("###### Custom PokeApi REST call ######");

        String url = "https://gateway.marvel.com/v1/public/comics?ts="+ts+ "&apikey=" + apiKey + "&hash=" +hash + "&limit=" + limit;
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

    private String converterParaMd5(String converterLongToString) throws UnsupportedEncodingException {
        log.info("MD5: {}", converterLongToString);


        MessageDigest algorithm = null;
        try {
            algorithm = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte messageDigest[] = algorithm.digest(converterLongToString.getBytes("UTF-8"));

        StringBuilder hexString = new StringBuilder();
        for (byte b : messageDigest) {
            hexString.append(String.format("%02X", 0xFF & b));
        }
        String senha = hexString.toString();
        System.out.println(senha.toLowerCase());

        return  senha.toLowerCase();
    }

    private String converterLongToString(long millis) {
        return Long.toString(millis);
    }

}
