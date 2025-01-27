package com.restaurant.api.rest.v1.controller.openapi;

import com.restaurant.api.rest.v1.exception.handler.ProblemDetail;
import com.restaurant.api.rest.v1.vo.RestaurantRequestVO;
import com.restaurant.api.rest.v1.vo.RestaurantResponseVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

@Tag(name = "Restaurants")
public interface RestaurantControllerOpenApi {

    @Operation(summary = "Save a new restaurant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurant saved successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))})
    })
    ResponseEntity<RestaurantResponseVO> save(RestaurantRequestVO restaurantRequestVO);

    @Operation(summary = "Find all restaurants")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurants paginated"),
            @ApiResponse(responseCode = "404", description = "Restaurants not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))})
    })
    ResponseEntity<Page<RestaurantResponseVO>> findAll(Pageable pageable);

    @Operation(summary = "Find restaurant by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurant found successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))}),
            @ApiResponse(responseCode = "404", description = "Restaurant not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))})
    })
    ResponseEntity<RestaurantResponseVO> findById(Long id);

    @Operation(summary = "Update restaurant by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurant updated successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))}),
            @ApiResponse(responseCode = "404", description = "Restaurant not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))})
    })
    ResponseEntity<RestaurantResponseVO> update(Long id, RestaurantRequestVO restaurantRequestVO);

    @Operation(summary = "Delete restaurant by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurant deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))}),
            @ApiResponse(responseCode = "404", description = "Restaurant not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))})
    })
    ResponseEntity<Void> delete(Long id);

}
