package com.ejmc.code.application;

import com.ejmc.code.application.dto.CommonCodeGroupRegistrationRequest;
import com.ejmc.code.domain.repository.CommonCodeGroupRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class CommonCodeGroupValidatorTest {

    @Mock
    private CommonCodeGroupRepository commonCodeGroupRepository;

    @InjectMocks
    private CommonCodeGroupValidator commonCodeGroupValidator;

    @Test
    @DisplayName("중복 된 공통 코드 그룹 추가시 예외처리한다")
    void validateNewCodeGroupTest() {
        // given
        CommonCodeGroupRegistrationRequest request = new CommonCodeGroupRegistrationRequest("ZONE_PROGRESS_TYPE", "ZP", "진행단계 구분 코드");
        given(commonCodeGroupRepository.existsByDetailsName(request.getName())).willReturn(Boolean.TRUE);

        // when
        assertThatThrownBy(() -> commonCodeGroupValidator.validateNewCodeGroup(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("공통 코드 그룹명은 중복될 수 없습니다. - groupName : %s", request.getName());

        // then
        then(commonCodeGroupRepository).should(times(1)).existsByDetailsName(request.getName());
    }
}
