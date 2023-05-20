package com.ejmc.code.presentation;

import com.ejmc.code.application.CommonCodeService;
import com.ejmc.code.application.dto.CommonCodeRegistrationRequest;
import com.ejmc.code.application.dto.CommonCodeResponse;
import com.ejmc.common.application.dto.CommonApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CommonCodeApi implements CommonCodeApiSpecification {

    private final CommonCodeService commonCodeService;

    @Override
    public void registerCommonCode(Long groupId, CommonCodeRegistrationRequest request) {
        log.info("공통 코드 등록 요청 - groupId : {}, {}", groupId, request);
        commonCodeService.registerCode(groupId, request);
    }

    @Override
    public CommonApiResponse<CommonCodeResponse> showCommonCode(Long groupId, String code) {
        log.info("공통 코드 조회 요청 - groupId : {}, code : {}", groupId, code);

        CommonCodeResponse response = commonCodeService.retrieveCodeBy(groupId, code);
        return CommonApiResponse.success(response);
    }
}
