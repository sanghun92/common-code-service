package com.ejmc.code.presentation;

import com.ejmc.code.application.dto.CommonCodeRegistrationRequest;
import com.ejmc.code.application.dto.CommonCodeResponse;
import com.ejmc.common.application.dto.CommonApiResponse;
import com.ejmc.common.presentation.SpecificationCommonErrorResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "공통 코드", description = "공통 코드 API")
@SpecificationCommonErrorResponses
public interface CommonCodeApiSpecification {

    @Operation(summary = "공통 코드 등록", description = "공통 코드를 등록하는 API 입니다.")
    @ApiResponse(responseCode = "201", description = "등록 성공")
    @Parameter(name = "groupName", description = "공통 코드 그룹명", example = "ZP")
    @PostMapping(
            path = "/api/code-group/{groupName}/codes",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(code = HttpStatus.CREATED)
    void registerCommonCode(@PathVariable String groupName,
                            @RequestBody @Valid CommonCodeRegistrationRequest request);

    @Operation(summary = "공통 코드 조회", description = "개별로 공통 코드를 조회하는 API 입니다.")
    @Parameter(name = "name", description = "공통 코드명", example = "ZP1")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @GetMapping(
            path = "/api/codes/{name}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    CommonApiResponse<CommonCodeResponse> showCommonCode(@PathVariable String name);
}
