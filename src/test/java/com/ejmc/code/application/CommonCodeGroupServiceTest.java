package com.ejmc.code.application;

import com.ejmc.code.application.dto.CommonCodeGroupRegistrationRequest;
import com.ejmc.code.application.dto.CommonCodeGroupResponse;
import com.ejmc.code.domain.CommonCodeGroup;
import com.ejmc.code.domain.CommonCodeGroupDetails;
import com.ejmc.code.domain.repository.CommonCodeGroupRepository;
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

@ExtendWith(MockitoExtension.class)
class CommonCodeGroupServiceTest {

    @Mock
    private CommonCodeGroupValidator commonCodeGroupValidator;

    @Mock
    private CommonCodeGroupRepository commonCodeGroupRepository;

    @InjectMocks
    private CommonCodeGroupService commonCodeGroupService;

    @Test
    @DisplayName("공통 코드 그룹 등록에 성공한다")
    void registerCodeGroupTest() {
        // given
        CommonCodeGroupRegistrationRequest request = new CommonCodeGroupRegistrationRequest("ZONE_PROGRESS_TYPE", "ZP", "진행단계 구분 코드");
        willDoNothing().given(commonCodeGroupValidator).validateNewCodeGroup(request);
        given(commonCodeGroupRepository.save(request.toCodeGroup())).willReturn(request.toCodeGroup());

        // when
        commonCodeGroupService.registerCodeGroup(request);

        // then
        then(commonCodeGroupValidator).should(times(1)).validateNewCodeGroup(request);
        then(commonCodeGroupRepository).should(times(1)).save(request.toCodeGroup());
    }

    @Test
    @DisplayName("공통 코드 그룹 조회에 성공한다")
    void retrieveCodeGroupByIdTest() {
        // given
        long groupId = 1L;
        CommonCodeGroup codeGroup = new CommonCodeGroup("ZONE_PROGRESS_TYPE", "ZP", "진행단계 구분 코드");
        given(commonCodeGroupRepository.findByIdWithCodes(groupId)).willReturn(Optional.of(codeGroup));

        // when
        CommonCodeGroupResponse response = commonCodeGroupService.retrieveCodeGroupBy(groupId);

        // then
        CommonCodeGroupDetails codeGroupDetails = codeGroup.getDetails();
        assertAll(
                () -> assertThat(response.getName()).isEqualTo(codeGroupDetails.getName()),
                () -> assertThat(response.getDescription()).isEqualTo(codeGroupDetails.getDescription())
        );
        then(commonCodeGroupRepository).should(times(1)).findByIdWithCodes(groupId);
    }
}
