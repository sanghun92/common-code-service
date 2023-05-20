package com.ejmc.code.application;

import com.ejmc.code.application.dto.CommonCodeRegistrationRequest;
import com.ejmc.code.application.dto.CommonCodeResponse;
import com.ejmc.code.domain.CommonCodeDetails;
import com.ejmc.code.domain.CommonCodeGroup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class CommonCodeServiceTest {

    private Long groupId;
    private CommonCodeGroup codeGroup;
    private CommonCodeDetails commonCodeDetails;

    @Mock
    private CommonCodeGroupService commonCodeGroupService;

    @InjectMocks
    private CommonCodeService commonCodeService;

    @BeforeEach
    void setUp() {
        this.groupId = 1L;
        this.codeGroup = new CommonCodeGroup("ZONE_PROGRESS_TYPE", "ZP", "진행단계 구분 코드");
        this.commonCodeDetails = new CommonCodeDetails("1", "기본계획수립", "진행단계 기본계획수립 코드");

        this.codeGroup.addCommonCode(commonCodeDetails);
        this.codeGroup.addCommonCode(new CommonCodeDetails("2", "안전진단", "진행단계 안전진단 코드"));
        this.codeGroup.addCommonCode(new CommonCodeDetails("3", "정비구역지정", "진행단계 정비구역지정 코드"));
        this.codeGroup.addCommonCode(new CommonCodeDetails("4", "조합설립 추진위원회 승인", "진행단계 조합설립 추진위원회 승인 코드"));
    }

    @Test
    @DisplayName("특정 그룹에 공통 코드 추가에 성공한다")
    void registerCodeTest() {
        // given
        CommonCodeRegistrationRequest request = new CommonCodeRegistrationRequest("5", "조합설립인가", "진행단계 기본계획수립 코드");
        given(commonCodeGroupService.findCodeGroupBy(groupId)).willReturn(codeGroup);

        // when
        commonCodeService.registerCode(groupId, request);

        // then
        then(commonCodeGroupService).should(times(1)).findCodeGroupBy(groupId);
    }

    @Test
    @DisplayName("특정 그룹에 속한 공통 코드를 조회하는데 성공한다")
    void retrieveCodeByTest() {
        // given
        String codeName = commonCodeDetails.getName();
        given(commonCodeGroupService.findCodeGroupBy(groupId)).willReturn(codeGroup);

        // when
        CommonCodeResponse response = commonCodeService.retrieveCodeBy(groupId, codeName);

        // then
        assertAll(
                () -> assertThat(response.getName()).isEqualTo(commonCodeDetails.getName()),
                () -> assertThat(response.getLabel()).isEqualTo(commonCodeDetails.getLabel()),
                () -> assertThat(response.getDescription()).isEqualTo(commonCodeDetails.getDescription())
        );
        then(commonCodeGroupService).should(times(1)).findCodeGroupBy(groupId);
    }
}
