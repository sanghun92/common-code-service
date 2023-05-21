package com.ejmc.code.application;

import com.ejmc.code.application.dto.CommonCodeRegistrationRequest;
import com.ejmc.code.domain.repository.CommonCodeRepository;
import com.ejmc.common.exception.ValidationException;
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
class CommonCodeValidatorTest {

    @Mock
    private CommonCodeRepository commonCodeRepository;

    @InjectMocks
    private CommonCodeValidator commonCodeValidator;

    @Test
    @DisplayName("중복 된 공통 코드 추가시 예외처리한다")
    void validateNewCodeGroupTest() {
        // given
        CommonCodeRegistrationRequest request = new CommonCodeRegistrationRequest("ZP7", "관리처분인가", "진행단계 관리처분인가 코드");
        given(commonCodeRepository.existsByDetailsName(request.getName())).willReturn(Boolean.TRUE);

        // when
        assertThatThrownBy(() -> commonCodeValidator.validateNewCode(request))
                .isInstanceOf(ValidationException.class)
                .hasMessage("공통 코드명은 중복될 수 없습니다. - codeName : %s", request.getName());

        // then
        then(commonCodeRepository).should(times(1)).existsByDetailsName(request.getName());
    }
}
