package com.ejmc.code.presentation;

import com.ejmc.code.application.CommonCodeGroupService;
import com.ejmc.code.application.dto.CommonCodeGroupRegistrationRequest;
import com.ejmc.code.application.dto.CommonCodeGroupResponse;
import com.ejmc.common.application.dto.CommonApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CommonCodeGroupApi implements CommonCodeGroupApiSpecification {

    private final CommonCodeGroupService commonCodeGroupService;

    @Override
    public void registerCommonCodeGroup(CommonCodeGroupRegistrationRequest request) {
        log.info("공통 코드 그룹 등록 요청 - {}", request);
        commonCodeGroupService.registerCodeGroup(request);
    }

    @Override
    public CommonApiResponse<CommonCodeGroupResponse> showCommonCodeGroup(Long groupId) {
        log.info("공통 코드 그룹 조회 요청 - groupId : {}", groupId);

        CommonCodeGroupResponse response = commonCodeGroupService.retrieveCodeGroupBy(groupId);
        return CommonApiResponse.success(response);
    }
}
