package com.acme.catchup.platform.news.interfaces.rest;

import com.acme.catchup.platform.news.domain.model.entities.FavoriteSource;
import com.acme.catchup.platform.news.domain.model.queries.GetFavoriteSourceByIdQuery;
import com.acme.catchup.platform.news.domain.services.FavoriteSourceCommandService;
import com.acme.catchup.platform.news.domain.services.FavoriteSourceQueryService;
import com.acme.catchup.platform.news.interfaces.rest.resources.CreateFavoriteSourceResource;
import com.acme.catchup.platform.news.interfaces.rest.resources.FavoriteSourceResource;
import com.acme.catchup.platform.news.interfaces.rest.transform.CreateFavoriteSourceCommandFromResourceAssembler;
import com.acme.catchup.platform.news.interfaces.rest.transform.FavoriteSourceResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping(value = "/api/v1/favorite-source", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Favorite Sources", description = "Endpoints for favorite source")
public class FavoriteSourceController {

    private final FavoriteSourceCommandService favoriteSourceCommandService;
    private final FavoriteSourceQueryService favoriteSourceQueryService;

    public FavoriteSourceController(FavoriteSourceCommandService favoriteSourceCommandService, FavoriteSourceQueryService favoriteSourceQueryService) {
        this.favoriteSourceCommandService = favoriteSourceCommandService;
        this.favoriteSourceQueryService = favoriteSourceQueryService;
    }

    @Operation(
            summary = "Create a Favorite source",
            description = "Creates a favorite source with the provided data"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Favorite source created"),
                    @ApiResponse(responseCode = "400", description = "Bad request"),
            }
    )
    @PostMapping
    public ResponseEntity<FavoriteSourceResource> createFavoriteSource(@RequestBody CreateFavoriteSourceResource resource) {
        var createFavoriteSourceCommand = CreateFavoriteSourceCommandFromResourceAssembler.toCommandFromResource(resource);
        Optional<FavoriteSource> favoriteSource = favoriteSourceCommandService.handle(createFavoriteSourceCommand);

        return favoriteSource
                .map(
                        source ->
                                new ResponseEntity<>(
                                        FavoriteSourceResourceFromEntityAssembler.toResourceFromEntity(source),
                                        CREATED
                                )
                )
                .orElseGet(
                        () -> ResponseEntity.badRequest().build()
                );
    }


    @Operation(
            summary = "Get a Favorite source",
            description = "Gets a favorite source with the provided id"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Favorite source found"),
                    @ApiResponse(responseCode = "404", description = "Favorite source not found"),
            }
    )
    @GetMapping("{id}")
    public ResponseEntity<FavoriteSourceResource> getFavoriteSourceById(@PathVariable Long id) {
        var getFavoriteSourceByIdQuery = new GetFavoriteSourceByIdQuery(id);
        Optional<FavoriteSource> favoriteSource = favoriteSourceQueryService.handle(getFavoriteSourceByIdQuery);
        return favoriteSource.map(
                source ->
                        ResponseEntity.ok(FavoriteSourceResourceFromEntityAssembler.toResourceFromEntity(source))
        ).orElseGet(
                () -> ResponseEntity.notFound().build()
        );
    }
}
