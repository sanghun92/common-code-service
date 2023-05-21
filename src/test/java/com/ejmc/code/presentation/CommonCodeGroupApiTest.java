package com.ejmc.code.presentation;

import com.ejmc.code.application.CommonCodeGroupService;
import com.ejmc.code.application.dto.CommonCodeGroupRegistrationRequest;
import com.ejmc.code.application.dto.CommonCodeGroupResponse;
import com.ejmc.code.application.dto.CommonCodeResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CommonCodeGroupApi.class)
class CommonCodeGroupApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CommonCodeGroupService commonCodeGroupService;

    @Test
    @DisplayName("공통 코드 그룹 등록 요청시 유효성 검사 후 공통 코드 그룹 등록에 성공한다")
    void registerCommonCodeGroupTest() throws Exception {
        // given
        CommonCodeGroupRegistrationRequest request = new CommonCodeGroupRegistrationRequest("ZP", "진행단계 구분 코드");
        willDoNothing().given(commonCodeGroupService).registerCodeGroup(any(CommonCodeGroupRegistrationRequest.class));

        // when
        mockMvc.perform(post("/api/code-group")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isCreated());

        // then
        then(commonCodeGroupService).should(times(1)).registerCodeGroup(any(CommonCodeGroupRegistrationRequest.class));
    }

    @ParameterizedTest
    @CsvSource({
            "'',진행단계 관리처분인가 코드",
            "ZP,''",
    })
    @DisplayName("공통 코드 그룹 등록 요청시 주어진 정보가 올바르게 입력되었는지 확인한다")
    void registerCommonCodeGroupThrowsTest(String name, String description) throws Exception {
        // given
        CommonCodeGroupRegistrationRequest request = new CommonCodeGroupRegistrationRequest(name, description);

        // when && then
        mockMvc.perform(post("/api/code-group")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error.message").isNotEmpty())
                .andExpect(jsonPath("$.error.fields").isNotEmpty());
    }

    @Test
    @DisplayName("공통 코드 그룹 조회시 유효성 검사 후 공통 코드 그룹 정보를 반환한다")
    void showCommonCodeGroup() throws Exception {
        // given
        String name = "ZP";
        CommonCodeGroupResponse response = new CommonCodeGroupResponse(name, List.of(
            new CommonCodeResponse("ZP1", "기본계획수립"),
            new CommonCodeResponse("ZP2", "안전진단"),
            new CommonCodeResponse("ZP3", "정비구역지정"),
            new CommonCodeResponse("ZP4", "조합설립 추진위원회 승인")
        ));
        given(commonCodeGroupService.retrieveCodeGroupBy(name)).willReturn(response);

        // when
        mockMvc.perform(get("/api/code-group/{name}", name))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.body.name").value(response.getName()))
                .andExpect(jsonPath("$.body.codes").isArray())
                .andExpect(jsonPath("$.body.codes").isNotEmpty());

        // then
        then(commonCodeGroupService).should(times(1)).retrieveCodeGroupBy(name);
    }
}
