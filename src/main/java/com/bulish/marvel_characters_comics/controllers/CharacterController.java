package com.bulish.marvel_characters_comics.controllers;

import com.bulish.marvel_characters_comics.entities.Comic;
import com.bulish.marvel_characters_comics.entities.MarvelCharacter;
import com.bulish.marvel_characters_comics.entities.exeptions.ComicIsAlreadyAssignedException;
import com.bulish.marvel_characters_comics.entities.exeptions.ComicNotFoundException;
import com.bulish.marvel_characters_comics.services.CharacterService;
import com.bulish.marvel_characters_comics.services.ComicService;
import com.bulish.marvel_characters_comics.specifications.CharacterPage;
import com.bulish.marvel_characters_comics.specifications.CharacterSearchCriteria;
import com.bulish.marvel_characters_comics.specifications.ComicPage;
import com.bulish.marvel_characters_comics.specifications.ComicSearchCriteria;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Tag(name = "Mat")
@RestController
@RequestMapping("/v1/public/characters")
public class CharacterController {

    private final CharacterService characterService;
    private final ComicService comicService;

    @Autowired
    public CharacterController(CharacterService characterService, ComicService comicService) {
        this.characterService = characterService;
        this.comicService = comicService;
    }

    @GetMapping
    public ResponseEntity<Page<MarvelCharacter>> getAllCharacters(CharacterPage characterPage,
                                                                  CharacterSearchCriteria characterSearchCriteria){
        return new ResponseEntity<>(characterService.getCharactersSortedFilteredPaginated(characterPage,
                characterSearchCriteria), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<MarvelCharacter> getCharacter(@PathVariable final Long id){
        MarvelCharacter character = characterService.getCharacterById(id);
        return new ResponseEntity<>(character, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}/comics")
    public ResponseEntity<List<Comic>> getAllComics(@PathVariable final Long id){
        MarvelCharacter character = characterService.getCharacterById(id);
        List<Comic> comics = character.getComics();
        return new ResponseEntity<>(comics, HttpStatus.OK);
    }



    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE )
    public ResponseEntity<MarvelCharacter> saveCharacter(@RequestBody final MarvelCharacter character,
                                                         @RequestParam(name = "name") String name,
                                                         @RequestParam(name = "description") String description,
                                                         @RequestParam(name = "image") MultipartFile image){
        MarvelCharacter marvelCharacter = new MarvelCharacter();
        marvelCharacter.setName(name);
        marvelCharacter.setDescription(description);
       // MarvelCharacter character1 = characterService.save(character);
        MarvelCharacter character1 = characterService.saveCharacterWithImage(image,marvelCharacter);
        return new ResponseEntity<>(character1, HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<MarvelCharacter> editCharacter(@PathVariable final Long id,
                                                            @RequestBody final MarvelCharacter character){
        MarvelCharacter updatedCharacter = characterService.editCharacter(id, character);
        return new ResponseEntity<>(updatedCharacter, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<MarvelCharacter> deleteCharacter(@PathVariable final Long id){
        MarvelCharacter character = characterService.deleteCharacterById(id);
        return new ResponseEntity<>(character, HttpStatus.OK);
    }

    @PostMapping(path = "/{characterId}/comics/{comicId}/add")
    public ResponseEntity<MarvelCharacter> addComicToCharacter(@PathVariable final Long comicId,
                                                                  @PathVariable final Long characterId){
        MarvelCharacter character = characterService.getCharacterById(characterId);
        Comic comic = comicService.getComicById(comicId);
        if (character.getComics().contains(comic)){
           throw new ComicIsAlreadyAssignedException(comicId, characterId);
        }
        character.addComic(comic);
        characterService.saveOrUpdate(character);
        return new ResponseEntity<>(character, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{characterId}/comics/{comicId}/delete")
    public ResponseEntity<MarvelCharacter> deleteComicFromCharacter(@PathVariable final Long comicId,
                                                                       @PathVariable final Long characterId){
        MarvelCharacter character = characterService.getCharacterById(characterId);
        Comic comic = comicService.getComicById(comicId);
        if (!character.getComics().contains(comic)){
            throw new ComicNotFoundException(comicId);
        }
        character.deleteComic(comic);
        characterService.saveOrUpdate(character);
       return new ResponseEntity<MarvelCharacter>(character, HttpStatus.OK);
    }
}
