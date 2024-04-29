package com.example.example4sem6_rikky_and_morty_rest.service;

import com.example.example4sem6_rikky_and_morty_rest.domain.Characters;
import com.example.example4sem6_rikky_and_morty_rest.domain.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
public class ServiceApiImpl implements ServiceApi{

    @Autowired
    private RestTemplate template;

    @Autowired
    private HttpHeaders headers;

    @Value("${external.api.url}")
    private String apiUrl;
    @Override
    public Characters getAllCharacters() {
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Characters> response = template.exchange(apiUrl, HttpMethod.GET, entity, Characters.class);

        return response.getBody();
    }

    @Override
    public Result getCharacterById(Integer id) {
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);

        String path = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .pathSegment(id.toString())
                .toUriString();

        ResponseEntity<Result> response = template.exchange(path, HttpMethod.GET, httpEntity, Result.class);
        return response.getBody();
    }

    @Override
    public Characters getAllCharactersAtPage(String page) {
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);

        String path = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("page", page)
                .toUriString();

        ResponseEntity<Characters> response = template.exchange(path, HttpMethod.GET, httpEntity, Characters.class);
        return response.getBody();
    }

}
