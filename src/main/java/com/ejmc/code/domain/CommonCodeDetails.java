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
public class CommonCodeDetails {

    @Column(nullable = false, length = 30)
    private final String name;

    @Column(nullable = false, length = 200)
    private final String label;

    @Column(nullable = false, length = 200)
    private final String description;

    protected CommonCodeDetails() {
        this.name = null;
        this.label = null;
        this.description = null;
    }

    public CommonCodeDetails(String name, String label, String description) {
        validateCommonCodeDetails(name, label, description);

        this.name = name;
        this.label = label;
        this.description = description;
    }

    private void validateCommonCodeDetails(String name, String label, String description) {
        if(!StringUtils.hasText(name)) {
            throw new IllegalArgumentException("공통 코드명은 필수값입니다.");
        }

        if(!StringUtils.hasText(label)) {
            throw new IllegalArgumentException("공통 코드 라벨값은 필수값입니다.");
        }

        if(!StringUtils.hasText(description)) {
            throw new IllegalArgumentException("공통 코드 설명은 필수값입니다.");
        }
    }

    public boolean matchName(String otherName) {
        return this.name.equals(otherName);
    }
}


