package com.ejmc.code.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CommonCodeTest {

    private CommonCodeGroup codeGroup;
    @BeforeEach
    void setUp() {
        this.codeGroup = new CommonCodeGroup("ZONE_PROGRESS_TYPE", "ZP", "진행단계 구분 코드");
    }

    @ParameterizedTest
    @DisplayName("주어진 코드와 해당 공통 코드의 동일 여부를 반환한다")
    @CsvSource({"1,1,true", "1,2,false", "1,3,false"})
    void matchByNameTest(String codeName, String compareCodeName, String expected) {
        // given
        CommonCodeDetails codeDetails = new CommonCodeDetails(codeName, "라벨", "설명");
        CommonCode commonCode = new CommonCode(this.codeGroup, codeDetails, 1);

        // when
        boolean matchResult = commonCode.match(compareCodeName);

        // then
        assertThat(matchResult).isEqualTo(Boolean.valueOf(expected));
    }
}
