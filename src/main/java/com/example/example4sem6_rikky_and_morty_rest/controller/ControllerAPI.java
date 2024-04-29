package com.example.example4sem6_rikky_and_morty_rest.controller;

import com.example.example4sem6_rikky_and_morty_rest.domain.Result;
import com.example.example4sem6_rikky_and_morty_rest.service.ServiceApi;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ControllerAPI {

    @Autowired
    private ServiceApi serviceApi;

    @GetMapping("/charactersPage") // Маппинг URL для страницы персонажей
    public String getCharacters(@RequestParam(name = "page", required = false) String page,
                                @RequestParam(name="sort", required = false, defaultValue = "false") Boolean sort,
                                Model model)
    {
        List<Result> charList;
        if(StringUtils.isEmpty(page)){
            charList = serviceApi.getAllCharacters().getResults();
        } else {
            charList = serviceApi.getAllCharactersAtPage(page).getResults();
        }

        // Часть отвечающая за переход между страницами prev и next

        String nextPageURL = serviceApi.getAllCharactersAtPage(page).getInfo().getNext();

        String prevPageURL = serviceApi.getAllCharactersAtPage(page).getInfo().getPrev();


        if (prevPageURL != null) {
            String prevPageNumber = UriComponentsBuilder.fromUriString(prevPageURL).build().getQueryParams().get("page").get(0);
            model.addAttribute("prevPage", prevPageNumber);
        } else {
            String prevPageNumber = serviceApi.getAllCharacters().getInfo().getPages().toString();
            model.addAttribute("prevPage", prevPageNumber);
        }

        if (nextPageURL != null) {
            String nextPageNumber = UriComponentsBuilder.fromUriString(nextPageURL).build().getQueryParams().get("page").get(0);
            model.addAttribute("nextPage", nextPageNumber);
        } else {
            model.addAttribute("prevPage", "1");
        }

        if(sort){
            charList = charList.stream()
                    .sorted(Comparator.comparing(Result::getName))
                    .collect(Collectors.toList());
        }

        model.addAttribute("characters", charList); // Добавление в модель всех пермонажей

        return "characters"; // Имя представления
    }

    @GetMapping("/characters/{id}") // Обновленный шаблон маршрута
    public String getCharacterById(@PathVariable Integer id, Model model) {
        Result character = serviceApi.getCharacterById(id);
        model.addAttribute("character", character);

        return "character";
    }
}
