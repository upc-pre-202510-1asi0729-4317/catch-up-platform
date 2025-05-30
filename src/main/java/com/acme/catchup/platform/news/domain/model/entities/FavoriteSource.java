package com.acme.catchup.platform.news.domain.model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class FavoriteSource {

    @Id
    private Long id;

    private String name;
}
