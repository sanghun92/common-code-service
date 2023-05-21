package com.ejmc.code.application;

import com.ejmc.code.application.dto.CommonCodeRegistrationRequest;
import com.ejmc.code.domain.repository.CommonCodeRepository;
import com.ejmc.common.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommonCodeValidator {

    private final CommonCodeRepository commonCodeRepository;

    public void validateNewCode(CommonCodeRegistrationRequest request) {
        if(commonCodeRepository.existsByDetailsName(request.getName())) {
            throw new ValidationException("공통 코드명은 중복될 수 없습니다. - codeName : %s", request.getName());
        }
    }
}
