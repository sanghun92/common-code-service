package com.ejmc.code.application.dto;

import com.ejmc.code.domain.CommonCodeGroup;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@RequiredArgsConstructor
@Getter
@ToString
public class CommonCodeGroupRegistrationRequest {

    @NotBlank(message = "공통 코드 그룹명은 필수값입니다.")
    private final String name;

    @NotBlank(message = "공통 코드 그룹 prefix 값은 필수값입니다.")
    private final String prefix;

    @NotBlank(message = "공통 코드 그룹명 설명은 필수값입니다.")
    private final String description;

    public CommonCodeGroup toCodeGroup() {
        return new CommonCodeGroup(this.name, this.prefix, this.description);
    }
}
