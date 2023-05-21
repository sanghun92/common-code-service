package com.ejmc.code.application.dto;

import com.ejmc.code.domain.CommonCode;
import com.ejmc.code.domain.CommonCodeDetails;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Getter
public class CommonCodeResponse {

    @Schema(description = "공통 코드명")
    private final String name;

    @Schema(description = "공통 코드 라벨")
    private final String label;

    public static List<CommonCodeResponse> of(Collection<CommonCode> codes) {
        return codes.stream()
                .map(CommonCodeResponse::of)
                .collect(Collectors.toList());
    }

    public static CommonCodeResponse of(CommonCode code) {
        return new CommonCodeResponse(code.getName(), code.getLabel());
    }
}
