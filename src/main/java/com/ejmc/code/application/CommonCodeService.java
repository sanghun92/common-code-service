package com.ejmc.code.application;

import com.ejmc.code.application.dto.CommonCodeRegistrationRequest;
import com.ejmc.code.application.dto.CommonCodeResponse;
import com.ejmc.code.domain.CommonCode;
import com.ejmc.code.domain.CommonCodeGroup;
import com.ejmc.code.domain.repository.CommonCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommonCodeService {

    private final CommonCodeValidator commonCodeValidator;
    private final CommonCodeGroupService commonCodeGroupService;
    private final CommonCodeRepository commonCodeRepository;

    @Transactional
    public void registerCode(String groupName, CommonCodeRegistrationRequest request) {
        commonCodeValidator.validateNewCode(request);
        CommonCodeGroup codeGroup = commonCodeGroupService.findCodeGroupEntityBy(groupName);
        codeGroup.addCommonCode(request.toCodeDetails());
    }

    public CommonCodeResponse retrieveCodeBy(String codeName) {
        CommonCode commonCodes = commonCodeRepository.findByDetailsName(codeName)
                .orElseThrow(() -> new EntityNotFoundException(String.format("공통 코드를 찾을 수 없습니다. - code : %s", codeName)));
        return CommonCodeResponse.of(commonCodes);
    }
}
