package com.bulish.marvel_characters_comics.controllers;

import com.bulish.marvel_characters_comics.entities.Comic;
import com.bulish.marvel_characters_comics.entities.MarvelCharacter;
import com.bulish.marvel_characters_comics.entities.exeptions.CharacterNotFoundException;
import com.bulish.marvel_characters_comics.entities.exeptions.ComicIsAlreadyAssignedException;
import com.bulish.marvel_characters_comics.entities.exeptions.ComicNotFoundException;
import com.bulish.marvel_characters_comics.services.CharacterService;
import com.bulish.marvel_characters_comics.services.ComicService;
import com.bulish.marvel_characters_comics.specifications.ComicPage;
import com.bulish.marvel_characters_comics.specifications.ComicSearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/v1/public/comics")
public class ComicController {

    private final ComicService comicService;
    private final CharacterService characterService;

    public ComicController(ComicService comicService, CharacterService characterService) {
        this.comicService = comicService;
        this.characterService = characterService;
    }

    @GetMapping
    public ResponseEntity<Page<Comic>> getAllComics(ComicPage comicPage,
                                                    ComicSearchCriteria comicSearchCriteria){
       return new ResponseEntity<>(comicService.getComicsSortedFilteredPaginated(comicPage,comicSearchCriteria), HttpStatus.OK);

    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Comic> getComic(@PathVariable final Long id){
        Comic comic = comicService.getComicById(id);
        return new ResponseEntity<>(comic, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}/characters")
    public ResponseEntity<List<MarvelCharacter>> getAllCharacters(@PathVariable final Long id){
        Comic comic = comicService.getComicById(id);
        List<MarvelCharacter> characters = comic.getCharacters();
        return new ResponseEntity<>(characters, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Comic> saveComic(@RequestBody final Comic comic){
        Comic comic1 = comicService.save(comic);
        return new ResponseEntity<>(comic1, HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Comic> editComic(@PathVariable final Long id,
                                              @RequestBody final Comic comic){
        Comic editedComic = comicService.editComic(id, comic);
        return new ResponseEntity<>(editedComic, HttpStatus.OK);
    }
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Comic> deleteComic(@PathVariable final Long id){
        Comic comic = comicService.deleteComicById(id);
       return new ResponseEntity<>(comic, HttpStatus.OK);
    }


    @PostMapping(path = "/{comicId}/characters/{characterId}/add")
    public ResponseEntity<Comic> addCharacterToComic(@PathVariable final Long comicId,
                                                        @PathVariable final Long characterId){
        MarvelCharacter character = characterService.getCharacterById(characterId);
        Comic comic = comicService.getComicById(comicId);
        if (comic.getCharacters().contains(character)){
            throw new ComicIsAlreadyAssignedException(comicId, characterId);
        }
        comic.addCharacter(character);
       comicService.saveOrUpdate(comic);
        return new ResponseEntity<>(comic, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{comicId}/characters/{characterId}/delete")
    private ResponseEntity<Comic> deleteCharacterFromComic(@PathVariable final Long comicId,
                                                              @PathVariable final Long characterId){
        MarvelCharacter character = characterService.getCharacterById(characterId);
        Comic comic = comicService.getComicById(comicId);

        if (!comic.getCharacters().contains(character)){
            throw new CharacterNotFoundException(characterId);
        }
        comic.deleteCharacter(character);
        comicService.saveOrUpdate(comic);
        return new ResponseEntity<>(comic, HttpStatus.OK);
    }

}
