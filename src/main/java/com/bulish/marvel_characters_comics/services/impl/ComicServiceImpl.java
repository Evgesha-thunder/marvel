package com.bulish.marvel_characters_comics.services.impl;

import com.bulish.marvel_characters_comics.entities.MarvelCharacter;
import com.bulish.marvel_characters_comics.entities.Comic;
import com.bulish.marvel_characters_comics.entities.exeptions.CharacterNotFoundException;
import com.bulish.marvel_characters_comics.entities.exeptions.ComicNotFoundException;
import com.bulish.marvel_characters_comics.repositories.CharacterRepository;
import com.bulish.marvel_characters_comics.repositories.ComicCriteriaRepository;
import com.bulish.marvel_characters_comics.repositories.ComicRepository;
import com.bulish.marvel_characters_comics.services.ComicService;
import com.bulish.marvel_characters_comics.specifications.ComicPage;
import com.bulish.marvel_characters_comics.specifications.ComicSearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ComicServiceImpl implements ComicService {

    private final ComicRepository comicRepository;
    private final CharacterRepository characterRepository;
    private final ComicCriteriaRepository comicCriteriaRepository;


    @Autowired
    public ComicServiceImpl(ComicRepository comicRepository, CharacterRepository characterRepository,
                            ComicCriteriaRepository comicCriteriaRepository) {
        this.comicRepository = comicRepository;
        this.characterRepository = characterRepository;
        this.comicCriteriaRepository = comicCriteriaRepository;
    }

    public Page<Comic> getComicsSortedFilteredPaginated(ComicPage comicPage,
                                                        ComicSearchCriteria comicSearchCriteria){
        return comicCriteriaRepository.findAllWithFilters(comicPage,comicSearchCriteria);
    }

    @Override
    public Comic save(Comic comic) {
        return comicRepository.save(comic);
    }

    @Override
    public List<Comic> getAllComic() {
        return comicRepository.findAll();
    }

    @Override
    public Comic getComicById(Long id) {
        return comicRepository.findById(id).orElseThrow(()
                -> new ComicNotFoundException(id));
    }

    @Override
    public Comic deleteComicById(Long id) {
        Comic comic = getComicById(id);
        comicRepository.delete(comic);
        return comic;
    }

    @Transactional
    @Override
    public Comic editComic(Long id, Comic comic) {
        Comic comicTobeUpdated = getComicById(id);
        comicTobeUpdated.setTitle(comic.getTitle());
        comicTobeUpdated.setDescription(comic.getDescription());
        return comicTobeUpdated;
    }

    @Transactional
    @Override
    public Comic addCharacterToComic(Long characterId, Long comicId) {
        Comic comic = getComicById(comicId);
        MarvelCharacter character = characterRepository.findById(characterId).orElseThrow(()->
                new CharacterNotFoundException(characterId));
        comic.addCharacter(character);
        return comic;
    }

    @Transactional
    @Override
    public Comic removeCharacterFromComic(Long characterId, Long comicId) {
        Comic comic = getComicById(comicId);
        MarvelCharacter character = characterRepository.findById(characterId).orElseThrow(()->
                new CharacterNotFoundException(characterId));
        comic.deleteCharacter(character);
        return comic;
    }

    @Override
    public Comic saveOrUpdate(Comic comic) {
        return comicRepository.saveAndFlush(comic);
    }

    @Override
    public Comic saveComicWithImage(MultipartFile file, Comic comic) {
        return null;
    }
}
