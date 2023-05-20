package com.ejmc.code.domain;

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

    @Column(nullable = false, length = 30)
    private final String name;

    @Column(nullable = false, length = 20)
    private final String prefix;

    @Column(nullable = false, length = 200)
    private final String description;

    protected CommonCodeGroupDetails() {
        this.name = null;
        this.prefix = null;
        this.description = null;
    }

    public CommonCodeGroupDetails(String name, String prefix, String description) {
        validateCommonCodeGroupDetails(name, prefix, description);

        this.name = name;
        this.prefix = prefix;
        this.description = description;
    }

    private void validateCommonCodeGroupDetails(String name, String prefix, String description) {
        if(!StringUtils.hasText(name)) {
            throw new IllegalArgumentException("공통 코드 그룹명은 필수값입니다.");
        }

        if(!StringUtils.hasText(prefix)) {
            throw new IllegalArgumentException("공통 코드 그룹 prefix값은 필수값입니다.");
        }

        if(!StringUtils.hasText(description)) {
            throw new IllegalArgumentException("공통 코드 그룹 설명은 필수값입니다.");
        }
    }
}


