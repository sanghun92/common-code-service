package com.ejmc.code.domain;

import com.ejmc.common.exception.ValidationException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@EqualsAndHashCode(of = "name")
@ToString
@Getter
@Embeddable
public class CommonCodeGroupDetails {

    @Column(nullable = false, length = 10)
    private final String name;

    @Column(nullable = false, length = 200)
    private final String description;

    protected CommonCodeGroupDetails() {
        this.name = null;
        this.description = null;
    }

    public CommonCodeGroupDetails(String name, String description) {
        validateCommonCodeGroupDetails(name, description);

        this.name = name;
        this.description = description;
    }

    private void validateCommonCodeGroupDetails(String name, String description) {
        if(!StringUtils.hasText(name)) {
            throw new ValidationException("공통 코드 그룹명은 필수값입니다.");
        }

        if(!StringUtils.hasText(description)) {
            throw new ValidationException("공통 코드 그룹 설명은 필수값입니다.");
        }
    }
}


