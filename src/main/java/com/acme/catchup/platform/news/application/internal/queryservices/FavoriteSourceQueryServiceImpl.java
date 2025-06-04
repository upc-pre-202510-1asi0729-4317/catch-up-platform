package com.acme.catchup.platform.news.application.internal.queryservices;

import com.acme.catchup.platform.news.domain.model.entities.FavoriteSource;
import com.acme.catchup.platform.news.domain.model.queries.GetFavoriteSourceByIdQuery;
import com.acme.catchup.platform.news.domain.services.FavoriteSourceQueryService;
import com.acme.catchup.platform.news.infrastructure.persistence.jpa.FavoriteSourceRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FavoriteSourceQueryServiceImpl implements FavoriteSourceQueryService {

    private final FavoriteSourceRepository favoriteSourceRepository;

    public FavoriteSourceQueryServiceImpl(FavoriteSourceRepository favoriteSourceRepository) {
        this.favoriteSourceRepository = favoriteSourceRepository;
    }

    @Override
    public Optional<FavoriteSource> handle(GetFavoriteSourceByIdQuery query) {
        return favoriteSourceRepository.findById(query.id());
    }
}
