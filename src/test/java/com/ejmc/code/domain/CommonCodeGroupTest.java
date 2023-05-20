package com.ejmc.code.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CommonCodeGroupTest {

    private CommonCodeGroup codeGroup;
    @BeforeEach
    void setUp() {
        this.codeGroup = new CommonCodeGroup("ZONE_PROGRESS_TYPE", "ZP", "진행단계 구분 코드");
    }

    @Test
    @DisplayName("주어진 공통 코드 정보로 해당 그룹에 공통 코드 추가에 성공한다")
    void addCommonCodeTest() {
        // given
        CommonCodeDetails commonCodeDetails = new CommonCodeDetails("4", "조합설립 추진위원회 승인", "진행단계 조합설립 추진위원회 승인 코드");

        // when && then
        codeGroup.addCommonCode(commonCodeDetails);
    }

    @Test
    @DisplayName("주어진 공통 코드가 중복 되는경우 해당 그룹에 공통 코드 추가시 예외처리한다")
    void addCommonCodeFailTest() {
        // given
        CommonCodeDetails commonCodeDetails = new CommonCodeDetails("4", "조합설립 추진위원회 승인", "진행단계 조합설립 추진위원회 승인 코드");
        codeGroup.addCommonCode(commonCodeDetails);

        // when && then
        assertThatThrownBy(() -> codeGroup.addCommonCode(commonCodeDetails))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("공통 코드명은 중복될 수 없습니다.");
    }

    @Test
    @DisplayName("해당 그룹에 주어진 공통 코드의 포함 여부를 반환한다")
    void hasCommonCodeTest() {
        // given
        CommonCodeDetails commonCodeDetails = new CommonCodeDetails("4", "조합설립 추진위원회 승인", "진행단계 조합설립 추진위원회 승인 코드");
        codeGroup.addCommonCode(commonCodeDetails);

        // when
        boolean result = codeGroup.hasCommonCode(commonCodeDetails.getName());

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("해당 그룹에서 주어진 공통 코드에 해당 되는 공통 코드 엔티티를 반환한다")
    void getCommonCodeByCodeNameTest() {
        // given
        CommonCodeDetails codeDetails = new CommonCodeDetails("2", "안전진단", "진행단계 안전진단 코드");
        codeGroup.addCommonCode(codeDetails);
        codeGroup.addCommonCode(new CommonCodeDetails("3", "정비구역지정", "진행단계 정비구역지정 코드"));
        codeGroup.addCommonCode(new CommonCodeDetails("4", "조합설립 추진위원회 승인", "진행단계 조합설립 추진위원회 승인 코드"));

        // when
        CommonCode commonCode = codeGroup.getCommonCodeBy(codeDetails.getName());

        // then
        assertThat(commonCode.getDetails()).isEqualTo(codeDetails);
    }
}
