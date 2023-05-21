package com.ejmc.code.domain;

import com.ejmc.common.exception.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CommonCodeGroupTest {

    private CommonCodeGroup codeGroup;
    @BeforeEach
    void setUp() {
        this.codeGroup = new CommonCodeGroup("ZP", "진행단계 구분 코드");
    }

    @Test
    @DisplayName("주어진 공통 코드 정보로 해당 그룹에 공통 코드 추가에 성공한다")
    void addCommonCodeTest() {
        // given
        CommonCodeDetails commonCodeDetails = new CommonCodeDetails("ZP4", "조합설립 추진위원회 승인", "진행단계 조합설립 추진위원회 승인 코드");

        // when && then
        codeGroup.addCommonCode(commonCodeDetails);
    }

    @Test
    @DisplayName("주어진 공통 코드가 중복 되는경우 해당 그룹에 공통 코드 추가시 예외처리한다")
    void addCommonCodeFailTest() {
        // given
        CommonCodeDetails commonCodeDetails = new CommonCodeDetails("ZP4", "조합설립 추진위원회 승인", "진행단계 조합설립 추진위원회 승인 코드");
        codeGroup.addCommonCode(commonCodeDetails);

        // when && then
        assertThatThrownBy(() -> codeGroup.addCommonCode(commonCodeDetails))
                .isInstanceOf(ValidationException.class)
                .hasMessageStartingWith("해당 그룹에 이미 등록 된 공통코드 입니다.");
    }

    @Test
    @DisplayName("해당 그룹에 주어진 공통 코드의 포함 여부를 반환한다")
    void hasCommonCodeTest() {
        // given
        CommonCodeDetails commonCodeDetails = new CommonCodeDetails("ZP4", "조합설립 추진위원회 승인", "진행단계 조합설립 추진위원회 승인 코드");
        codeGroup.addCommonCode(commonCodeDetails);

        // when
        boolean result = codeGroup.hasCommonCode(commonCodeDetails.getName());

        // then
        assertThat(result).isTrue();
    }
}
