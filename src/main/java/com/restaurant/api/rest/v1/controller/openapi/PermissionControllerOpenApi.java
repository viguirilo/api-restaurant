package com.restaurant.api.rest.v1.controller.openapi;

import com.restaurant.api.rest.v1.exception.handler.ProblemDetail;
import com.restaurant.api.rest.v1.vo.PermissionRequestVO;
import com.restaurant.api.rest.v1.vo.PermissionResponseVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

@Tag(name = "Permissions")
public interface PermissionControllerOpenApi {

    @Operation(summary = "Save a new permission")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Permission saved successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))})
    })
    ResponseEntity<PermissionResponseVO> save(PermissionRequestVO permissionRequestVO);

    @Operation(summary = "Find all permissions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Permissions paginated"),
            @ApiResponse(responseCode = "404", description = "Permissions not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))})
    })
    ResponseEntity<Page<PermissionResponseVO>> findAll(Pageable pageable);

    @Operation(summary = "Find permission by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Permission found successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))}),
            @ApiResponse(responseCode = "404", description = "Permission not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))})
    })
    ResponseEntity<PermissionResponseVO> findById(Long id);

    @Operation(summary = "Update permission by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Permission updated successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))}),
            @ApiResponse(responseCode = "404", description = "Permission not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))})
    })
    ResponseEntity<PermissionResponseVO> update(Long id, PermissionRequestVO permissionRequestVO);

    @Operation(summary = "Delete permission by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Permission deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))}),
            @ApiResponse(responseCode = "404", description = "Permission not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))})
    })
    ResponseEntity<Void> delete(Long id);

}
