package com.ejmc.code.application.dto;

import com.ejmc.code.domain.CommonCodeDetails;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@RequiredArgsConstructor
@Getter
@ToString
public class CommonCodeRegistrationRequest {

    @Schema(description = "공통 코드명", example = "ZP8")
    @NotBlank(message = "공통 코드명은 필수값입니다.")
    @Size(min = 1, max = 7, message = "1 ~ 7 자리수까지만 입력할 수 있습니다.")
    private final String name;

    @Schema(description = "공통 코드 라벨", example = "착공신고")
    @NotBlank(message = "공통 코드 라벨값은 필수값입니다.")
    @Size(min = 1, max = 40, message = "1 ~ 40 자리수까지만 입력할 수 있습니다.")
    private final String label;

    @Schema(description = "공통 코드 설명", example = "진행단계 착공신고 코드")
    @NotBlank(message = "공통 코드 설명은 필수값입니다.")
    @Size(min = 1, max = 170, message = "1 ~ 170 자리수까지만 입력할 수 있습니다.")
    private final String description;

    public CommonCodeDetails toCodeDetails() {
        return new CommonCodeDetails(this.name, this.label, this.description);
    }
}
