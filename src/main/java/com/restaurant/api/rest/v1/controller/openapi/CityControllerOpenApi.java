package com.restaurant.api.rest.v1.controller.openapi;

import com.restaurant.api.rest.v1.exception.handler.ProblemDetail;
import com.restaurant.api.rest.v1.vo.CityRequestVO;
import com.restaurant.api.rest.v1.vo.CityResponseVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

@Tag(name = "Cities")
public interface CityControllerOpenApi {

    @Operation(summary = "Save a new city")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "City saved successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))})
    })
    ResponseEntity<CityResponseVO> save(CityRequestVO cityRequestVO);

    @Operation(summary = "Find all cities")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cities paginated"),
            @ApiResponse(responseCode = "404", description = "Cities not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))})
    })
    ResponseEntity<Page<CityResponseVO>> findAll(Pageable pageable);

    @Operation(summary = "Find city by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "City found successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))}),
            @ApiResponse(responseCode = "404", description = "City not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))})
    })
    ResponseEntity<CityResponseVO> findById(Long id);

    @Operation(summary = "Update city by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "City updated successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))}),
            @ApiResponse(responseCode = "404", description = "City not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))})
    })
    ResponseEntity<CityResponseVO> update(Long id, CityRequestVO cityRequestVO);

    @Operation(summary = "Delete city by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "City deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))}),
            @ApiResponse(responseCode = "404", description = "City not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))})
    })
    ResponseEntity<Void> delete(Long id);

}
