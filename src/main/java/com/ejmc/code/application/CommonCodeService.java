package com.ejmc.code.application;

import com.ejmc.code.application.dto.CommonCodeRegistrationRequest;
import com.ejmc.code.application.dto.CommonCodeResponse;
import com.ejmc.code.domain.CommonCode;
import com.ejmc.code.domain.CommonCodeDetails;
import com.ejmc.code.domain.CommonCodeGroup;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommonCodeService {

    private final CommonCodeGroupService commonCodeGroupService;

    @Transactional
    public void registerCode(Long groupId, CommonCodeRegistrationRequest request) {
        CommonCodeGroup codeGroup = commonCodeGroupService.findCodeGroupBy(groupId);
        CommonCodeDetails codeDetails = request.toCodeDetails();
        codeGroup.addCommonCode(codeDetails);
    }

    public CommonCodeResponse retrieveCodeBy(Long groupId, String code) {
        CommonCodeGroup codeGroup = commonCodeGroupService.findCodeGroupBy(groupId);
        CommonCode commonCode = codeGroup.getCommonCodeBy(code);
        return CommonCodeResponse.of(commonCode);
    }
}
