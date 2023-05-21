package com.ejmc.code.presentation;

import com.ejmc.code.application.CommonCodeService;
import com.ejmc.code.application.dto.CommonCodeRegistrationRequest;
import com.ejmc.code.application.dto.CommonCodeResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CommonCodeApi.class)
class CommonCodeApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CommonCodeService commonCodeService;

    @Test
    @DisplayName("공통 코드 등록 요청시 유효성 검사 후 공통 코드 등록에 성공한다")
    void registerCommonCodeTest() throws Exception {
        // given
        String groupName = "ZP";
        CommonCodeRegistrationRequest request = new CommonCodeRegistrationRequest("ZP7", "관리처분인가", "진행단계 관리처분인가 코드");
        willDoNothing().given(commonCodeService).registerCode(eq(groupName), any(CommonCodeRegistrationRequest.class));

        // when
        mockMvc.perform(post("/api/code-group/{groupName}/codes", groupName)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isCreated());

        // then
        then(commonCodeService).should(times(1)).registerCode(eq(groupName), any(CommonCodeRegistrationRequest.class));
    }

    @ParameterizedTest
    @CsvSource({
            "'',관리처분인가,진행단계 관리처분인가 코드",
            "ZP7,'',진행단계 관리처분인가 코드",
            "ZP7,관리처분인가,''",
    })
    @DisplayName("공통 코드 등록 요청시 주어진 정보가 올바르게 입력되었는지 확인한다")
    void registerCommonCodeThrowsTest(String name, String label, String description) throws Exception {
        // given
        String groupName = "ZP";
        CommonCodeRegistrationRequest request = new CommonCodeRegistrationRequest(name, label, description);

        // when && then
        mockMvc.perform(post("/api/code-group/{groupName}/codes", groupName)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error.message").isNotEmpty())
                .andExpect(jsonPath("$.error.fields").isNotEmpty());
    }

    @Test
    @DisplayName("공통 코드 등록 조회시 유효성 검사 후 공통 코드 정보를 반환한다")
    void showCommonCodeTest() throws Exception {
        // given
        String name = "ZP1";
        CommonCodeResponse response = new CommonCodeResponse("ZP1", "기본계획수립");
        given(commonCodeService.retrieveCodeBy(name)).willReturn(response);

        // when
        mockMvc.perform(get("/api/codes/{name}", name))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.body.name").value(response.getName()))
                .andExpect(jsonPath("$.body.label").value(response.getLabel()));

        // then
        then(commonCodeService).should(times(1)).retrieveCodeBy(name);
    }
}
