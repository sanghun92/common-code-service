package com.ejmc.code.application;

import com.ejmc.code.application.dto.CommonCodeGroupRegistrationRequest;
import com.ejmc.code.application.dto.CommonCodeGroupResponse;
import com.ejmc.code.domain.CommonCodeGroup;
import com.ejmc.code.domain.repository.CommonCodeGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommonCodeGroupService {

    private final CommonCodeGroupValidator commonCodeGroupValidator;
    private final CommonCodeGroupRepository commonCodeGroupRepository;

    @Transactional
    public void registerCodeGroup(CommonCodeGroupRegistrationRequest request) {
        commonCodeGroupValidator.validateNewCodeGroup(request);
        commonCodeGroupRepository.save(request.toCodeGroup());
    }

    public CommonCodeGroupResponse retrieveCodeGroupBy(String name) {
        CommonCodeGroup codeGroup = findCodeGroupBy(name);
        return CommonCodeGroupResponse.of(codeGroup);
    }

    public CommonCodeGroup findCodeGroupBy(String name) {
        return commonCodeGroupRepository.findByNameWithCodes(name)
                .orElseThrow(() -> new EntityNotFoundException(String.format("공통 그룹 코드를 찾을 수 없습니다. - name : %s", name)));
    }
}
