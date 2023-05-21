package com.ejmc.code.application.dto;

import com.ejmc.code.domain.CommonCodeGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class CommonCodeGroupResponse {

    @Schema(description = "공통 코드 그룹명")
    private final String name;

    @Schema(description = "공통 코드 목록")
    private final List<CommonCodeResponse> codes;

    public static CommonCodeGroupResponse of(CommonCodeGroup commonCodeGroup) {
        return new CommonCodeGroupResponse(
                commonCodeGroup.getName(),
                CommonCodeResponse.of(commonCodeGroup.getCodes())
        );
    }
}
