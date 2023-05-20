package com.ejmc.code.application.dto;

import com.ejmc.code.domain.CommonCodeGroup;
import com.ejmc.code.domain.CommonCodeGroupDetails;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class CommonCodeGroupResponse {

    private final Long id;

    private final String name;

    private final String description;

    private final List<CommonCodeResponse> codes;

    public static CommonCodeGroupResponse of(CommonCodeGroup commonCodeGroup) {
        CommonCodeGroupDetails codeGroupDetails = commonCodeGroup.getDetails();
        return new CommonCodeGroupResponse(
                commonCodeGroup.getId(),
                codeGroupDetails.getName(),
                codeGroupDetails.getDescription(),
                CommonCodeResponse.of(commonCodeGroup.getCodes())
        );
    }
}
