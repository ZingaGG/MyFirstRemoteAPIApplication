package com.example.example4sem6_rikky_and_morty_rest.service;

import com.example.example4sem6_rikky_and_morty_rest.domain.Characters;
import com.example.example4sem6_rikky_and_morty_rest.domain.Result;

public interface ServiceApi {
    public Characters getAllCharacters();

    public Result getCharacterById(Integer id);

    public Characters getAllCharactersAtPage(String page);

}
