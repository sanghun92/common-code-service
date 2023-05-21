package com.ejmc.code.application;

import com.ejmc.code.application.dto.CommonCodeRegistrationRequest;
import com.ejmc.code.application.dto.CommonCodeResponse;
import com.ejmc.code.domain.CommonCode;
import com.ejmc.code.domain.CommonCodeDetails;
import com.ejmc.code.domain.CommonCodeGroup;
import com.ejmc.code.domain.repository.CommonCodeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class CommonCodeServiceTest {

    private CommonCodeGroup codeGroup;
    private CommonCodeDetails commonCodeDetails;

    @Mock
    private CommonCodeValidator commonCodeValidator;

    @Mock
    private CommonCodeGroupService commonCodeGroupService;

    @Mock
    private CommonCodeRepository commonCodeRepository;

    @InjectMocks
    private CommonCodeService commonCodeService;

    @BeforeEach
    void setUp() {
        this.codeGroup = new CommonCodeGroup("ZP", "진행단계 구분 코드");
        this.commonCodeDetails = new CommonCodeDetails("ZP1", "기본계획수립", "진행단계 기본계획수립 코드");

        this.codeGroup.addCommonCode(commonCodeDetails);
        this.codeGroup.addCommonCode(new CommonCodeDetails("ZP2", "안전진단", "진행단계 안전진단 코드"));
        this.codeGroup.addCommonCode(new CommonCodeDetails("ZP3", "정비구역지정", "진행단계 정비구역지정 코드"));
        this.codeGroup.addCommonCode(new CommonCodeDetails("ZP4", "조합설립 추진위원회 승인", "진행단계 조합설립 추진위원회 승인 코드"));
    }

    @Test
    @DisplayName("특정 그룹에 공통 코드 추가에 성공한다")
    void registerCodeTest() {
        // given
        CommonCodeRegistrationRequest request = new CommonCodeRegistrationRequest("5", "조합설립인가", "진행단계 기본계획수립 코드");
        willDoNothing().given(commonCodeValidator).validateNewCode(request);
        given(commonCodeGroupService.findCodeGroupEntityBy(codeGroup.getName())).willReturn(codeGroup);

        // when
        commonCodeService.registerCode(codeGroup.getName(), request);

        // then
        then(commonCodeValidator).should(times(1)).validateNewCode(request);
        then(commonCodeGroupService).should(times(1)).findCodeGroupEntityBy(codeGroup.getName());
    }

    @Test
    @DisplayName("특정 그룹에 속한 공통 코드를 조회하는데 성공한다")
    void retrieveCodeByTest() {
        // given
        String codeName = commonCodeDetails.getName();
        CommonCode commonCode = new CommonCode(codeGroup, commonCodeDetails, 1);
        given(commonCodeRepository.findByDetailsName(codeName)).willReturn(Optional.of(commonCode));

        // when
        CommonCodeResponse response = commonCodeService.retrieveCodeBy(codeName);

        // then
        assertAll(
                () -> assertThat(response.getName()).isEqualTo(commonCodeDetails.getName()),
                () -> assertThat(response.getLabel()).isEqualTo(commonCodeDetails.getLabel())
        );
        then(commonCodeRepository).should(times(1)).findByDetailsName(codeName);
    }
}
