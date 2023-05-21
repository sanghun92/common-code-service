package com.ejmc.common.presentation;

import com.ejmc.common.application.dto.CommonApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;

@Target({TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "잘못 된 요청",
                content = @Content(schema = @Schema(implementation = CommonApiResponse.class))
        ),
        @ApiResponse(responseCode = "500", description = "서버 에러",
                content = @Content(schema = @Schema(implementation = CommonApiResponse.class))
        )
})
public @interface SpecificationCommonErrorResponses {
}
