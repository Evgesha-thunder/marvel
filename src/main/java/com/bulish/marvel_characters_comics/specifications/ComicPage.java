package com.bulish.marvel_characters_comics.specifications;

import lombok.Data;
import org.springframework.data.domain.Sort;

@Data
public class ComicPage {
    private int pageNumber = 0;
    private int pageSize = 5;
    private Sort.Direction sortDirection = Sort.Direction.ASC;
    private String sortBy = "id";
}
