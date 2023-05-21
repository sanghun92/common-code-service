package com.ejmc.code.presentation;

import com.ejmc.code.application.CommonCodeService;
import com.ejmc.code.application.dto.CommonCodeRegistrationRequest;
import com.ejmc.code.application.dto.CommonCodeResponse;
import com.ejmc.common.application.dto.CommonApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CommonCodeApi implements CommonCodeApiSpecification {

    private final CommonCodeService commonCodeService;

    @Override
    public void registerCommonCode(String groupName, CommonCodeRegistrationRequest request) {
        log.info("공통 코드 등록 요청 - groupName : {}, {}", groupName, request);
        commonCodeService.registerCode(groupName, request);
    }

    public CommonApiResponse<CommonCodeResponse> showCommonCodes(@RequestParam String name) {
        log.info("공통 코드 조회 요청 - code : {}", name);

        CommonCodeResponse response = commonCodeService.retrieveCodeBy(name);
        return CommonApiResponse.success(response);
    }
}
