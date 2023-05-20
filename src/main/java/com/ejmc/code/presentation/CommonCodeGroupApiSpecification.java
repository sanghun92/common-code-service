package com.ejmc.code.presentation;

import com.ejmc.code.application.dto.CommonCodeGroupRegistrationRequest;
import com.ejmc.code.application.dto.CommonCodeGroupResponse;
import com.ejmc.common.application.dto.CommonApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

public interface CommonCodeGroupApiSpecification {

    @PostMapping(
            path = "/api/common-code-group",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(code = HttpStatus.CREATED)
    void registerCommonCodeGroup(@RequestBody @Valid CommonCodeGroupRegistrationRequest request);

    @GetMapping(
            path = "/api/common-code-group/{groupId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    CommonApiResponse<CommonCodeGroupResponse> showCommonCodeGroup(@PathVariable Long groupId);
}
