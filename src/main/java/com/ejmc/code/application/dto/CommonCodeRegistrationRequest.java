package com.ejmc.code.application.dto;

import com.ejmc.code.domain.CommonCodeDetails;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@RequiredArgsConstructor
@Getter
@ToString
public class CommonCodeRegistrationRequest {

    @NotBlank(message = "공통 코드명은 필수값입니다.")
    private final String name;

    @NotBlank(message = "공통 코드 라벨값은 필수값입니다.")
    private final String label;

    @NotBlank(message = "공통 코드 설명은 필수값입니다.")
    private final String description;

    public CommonCodeDetails toCodeDetails() {
        return new CommonCodeDetails(this.name, this.label, this.description);
    }
}
