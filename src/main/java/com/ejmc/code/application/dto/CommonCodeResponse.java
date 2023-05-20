package com.ejmc.code.application.dto;

import com.ejmc.code.domain.CommonCode;
import com.ejmc.code.domain.CommonCodeDetails;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Getter
public class CommonCodeResponse {

    private final String name;

    private final String label;

    private final String description;

    public static List<CommonCodeResponse> of(Set<CommonCode> codes) {
        return codes.stream()
                .map(CommonCodeResponse::of)
                .collect(Collectors.toList());
    }

    public static CommonCodeResponse of(CommonCode code) {
        CommonCodeDetails codeDetails = code.getDetails();
        return new CommonCodeResponse(
                codeDetails.getName(),
                codeDetails.getLabel(),
                codeDetails.getDescription()
        );
    }
}
