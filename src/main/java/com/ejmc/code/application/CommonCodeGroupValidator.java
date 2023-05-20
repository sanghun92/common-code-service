package com.ejmc.code.application;

import com.ejmc.code.application.dto.CommonCodeGroupRegistrationRequest;
import com.ejmc.code.domain.repository.CommonCodeGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommonCodeGroupValidator {

    private final CommonCodeGroupRepository commonCodeGroupRepository;

    public void validateNewCodeGroup(CommonCodeGroupRegistrationRequest request) {
        if(commonCodeGroupRepository.existsByDetailsName(request.getName())) {
            throw new IllegalArgumentException(String.format("공통 코드 그룹명은 중복될 수 없습니다. - groupName : %s", request.getName()));
        }
    }
}
