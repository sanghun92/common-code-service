package com.ejmc.code.application.dto;

import com.ejmc.code.domain.CommonCodeGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@RequiredArgsConstructor
@Getter
@ToString
public class CommonCodeGroupRegistrationRequest {

    @Schema(description = "공통 코드 그룹명", example = "BN")
    @NotBlank(message = "공통 코드 그룹명은 필수값입니다.")
    @Size(min = 1, max = 7, message = "1 ~ 7 자리수까지만 입력할 수 있습니다.")
    private final String name;

    @Schema(description = "공통 코드 그룹 설명", example = "사업구분 코드")
    @NotBlank(message = "공통 코드 그룹명 설명은 필수값입니다.")
    @Size(min = 1, max = 170, message = "1 ~ 170 자리수까지만 입력할 수 있습니다.")
    private final String description;

    public CommonCodeGroup toCodeGroup() {
        return new CommonCodeGroup(this.name, this.description);
    }
}
