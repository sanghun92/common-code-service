package com.ejmc.code.presentation;

import com.ejmc.code.application.dto.CommonCodeRegistrationRequest;
import com.ejmc.code.application.dto.CommonCodeResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

public interface CommonCodeApiSpecification {

    @PostMapping(
            path = "/api/common-code-group/{groupId}/codes",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(code = HttpStatus.CREATED)
    void registerCommonCode(@PathVariable Long groupId,
                            @RequestBody @Valid CommonCodeRegistrationRequest request);

    @GetMapping(
            path = "/api/common-code-group/{groupId}/codes",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    CommonCodeResponse showCommonCode(@PathVariable Long groupId,
                                      @RequestParam String code);
}
