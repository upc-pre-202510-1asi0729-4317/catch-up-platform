package com.acme.catchup.platform.news.domain.services;

import com.acme.catchup.platform.news.domain.model.commands.CreateFavoriteSourceCommand;
import com.acme.catchup.platform.news.domain.model.entities.FavoriteSource;

import java.util.Optional;

public interface FavoriteSourceCommandService {

    Optional<FavoriteSource> handle(CreateFavoriteSourceCommand command);
}
