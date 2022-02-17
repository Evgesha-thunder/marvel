package com.bulish.marvel_characters_comics.services.impl;

import com.bulish.marvel_characters_comics.entities.MarvelCharacter;
import com.bulish.marvel_characters_comics.entities.Comic;
import com.bulish.marvel_characters_comics.entities.exeptions.CharacterNotFoundException;
import com.bulish.marvel_characters_comics.repositories.CharacterRepository;
import com.bulish.marvel_characters_comics.repositories.ComicRepository;
import com.bulish.marvel_characters_comics.repositories.MarvelCriteriaRepository;
import com.bulish.marvel_characters_comics.services.CharacterService;
import com.bulish.marvel_characters_comics.specifications.CharacterPage;
import com.bulish.marvel_characters_comics.specifications.CharacterSearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Service
public class CharacterServiceImpl implements CharacterService {

    private final CharacterRepository characterRepository;
    private final ComicRepository comicRepository;
    private final MarvelCriteriaRepository marvelCriteriaRepository;


    @Autowired
    public CharacterServiceImpl(CharacterRepository characterRepository, ComicRepository comicRepository,
                                MarvelCriteriaRepository marvelCriteriaRepository) {
        this.characterRepository = characterRepository;
        this.comicRepository = comicRepository;
        this.marvelCriteriaRepository = marvelCriteriaRepository;
    }

    @Override
    public MarvelCharacter save(MarvelCharacter character) {
        return characterRepository.save(character);
    }

    @Override
    public List<MarvelCharacter> getAllCharacters() {
        return characterRepository.findAll();
    }


    @Override
    public MarvelCharacter getCharacterById(Long id) {
        return characterRepository.findById(id).orElseThrow(()
                -> new CharacterNotFoundException(id));
    }

    @Override
    public MarvelCharacter deleteCharacterById(Long id) {
        MarvelCharacter character = getCharacterById(id);
        characterRepository.delete(character);
        return character;
    }

    @Transactional
    @Override
    public MarvelCharacter editCharacter(Long id, MarvelCharacter character) {
        MarvelCharacter characterTobeUpdated = getCharacterById(id);
        characterTobeUpdated.setName(character.getName());
        characterTobeUpdated.setDescription(character.getDescription());
        return characterTobeUpdated;
    }

    @Transactional
    @Override
    public MarvelCharacter addComicToCharacter(Long characterId, Long comicId) {
        MarvelCharacter character = getCharacterById(characterId);
        Comic comic = comicRepository.findById(comicId).orElseThrow();
        character.addComic(comic);
        return character;
    }

    @Transactional
    @Override
    public MarvelCharacter removeComicFromCharacter(Long characterId, Long comicId) {
        MarvelCharacter character = getCharacterById(characterId);
        Comic comic = comicRepository.findById(comicId).orElseThrow();
        character.deleteComic(comic);
        return character;

    }

    @Override
    public MarvelCharacter saveOrUpdate(MarvelCharacter character) {
        return characterRepository.saveAndFlush(character);
    }

    @Override
    public Page<MarvelCharacter> getCharactersSortedFilteredPaginated(CharacterPage characterPage, CharacterSearchCriteria characterSearchCriteria) {
        return marvelCriteriaRepository.findAllWithFilters(characterPage,characterSearchCriteria);
    }

    @Override
    @Transactional
    public MarvelCharacter saveCharacterWithImage(MultipartFile file, MarvelCharacter marvelCharacter) {
            if (file != null && !file.isEmpty()) {
                String fileName = StringUtils.cleanPath(file.getOriginalFilename());
                try {
                    marvelCharacter.setImageLink(Base64.getEncoder().encodeToString(file.getBytes()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

           characterRepository.saveAndFlush(marvelCharacter);
            return marvelCharacter;
        }

    }

