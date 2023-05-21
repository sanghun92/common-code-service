package com.ejmc.code.presentation;

import com.ejmc.code.application.dto.CommonCodeGroupRegistrationRequest;
import com.ejmc.code.application.dto.CommonCodeGroupResponse;
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

@Tag(name = "공통 코드 그룹", description = "공통 코드 그룹 PAI")
@SpecificationCommonErrorResponses
public interface CommonCodeGroupApiSpecification {

    @Operation(summary = "공통 코드 그룹 등록", description = "공통 코드 그룹을 등록하는 API 입니다.")
    @ApiResponse(responseCode = "201", description = "등록 성공")
    @PostMapping(
            path = "/api/code-group",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(code = HttpStatus.CREATED)
    void registerCommonCodeGroup(@RequestBody @Valid CommonCodeGroupRegistrationRequest request);

    @Operation(summary = "공통 코드 그룹 조회", description = "공통 코드 그룹을 조회하는 API 입니다.")
    @Parameter(name = "name", description = "공통 코드 그룹명", example = "ZP")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @GetMapping(
            path = "/api/code-group/{name}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    CommonApiResponse<CommonCodeGroupResponse> showCommonCodeGroup(@PathVariable String name);
}

