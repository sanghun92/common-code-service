package com.ejmc;

import com.ejmc.code.domain.CommonCodeDetails;
import com.ejmc.code.domain.CommonCodeGroup;
import com.ejmc.code.domain.repository.CommonCodeGroupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Configuration
@Profile("!test")
public class DataLoaderApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    private final CommonCodeGroupRepository commonCodeGroupRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        loadSamples();
    }

    private void loadSamples() {
        log.debug("샘플 데이터 생성 시작");

        Map<CommonCodeGroupSample, CommonCodeGroup> codeGroups = loadCommonCodeGroupSample();
        loadCommonCodeSample(codeGroups);

        log.debug("샘플 데이터 생성 완료");
    }

    private Map<CommonCodeGroupSample, CommonCodeGroup> loadCommonCodeGroupSample() {
        Function<CommonCodeGroupSample, CommonCodeGroup> createCodeGroup = it -> commonCodeGroupRepository.save(
                new CommonCodeGroup(it.name, it.prefix, it.description));

        return Arrays.stream(CommonCodeGroupSample.values())
                .collect(Collectors.toMap(Function.identity(), createCodeGroup));
    }

    private void loadCommonCodeSample(Map<CommonCodeGroupSample, CommonCodeGroup> codeGroups) {
        Arrays.stream(CommonCodeSample.values())
                .forEach(it -> {
                    CommonCodeGroup commonCodeGroup = codeGroups.get(it.group);
                    CommonCodeDetails commonCodeDetails = new CommonCodeDetails(it.name, it.label, it.description);
                    commonCodeGroup.addCommonCode(commonCodeDetails);
                });
        commonCodeGroupRepository.saveAll(codeGroups.values());
    }

    private enum CommonCodeGroupSample {
        사업구분("BUSINESS_TYPE", "BN", "사업구분 코드"),
        진행단계("ZONE_PROGRESS_TYPE", "ZP", "진행단계 구분 코드");

        private final String name;
        private final String prefix;
        private final String description;

        CommonCodeGroupSample(String name, String prefix, String description) {
            this.name = name;
            this.prefix = prefix;
            this.description = description;
        }
    }

    private enum CommonCodeSample {
        사업구분_재개발(CommonCodeGroupSample.사업구분, "1", "재개발", "사업구분 재개발 코드"),
        사업구분_가로주택(CommonCodeGroupSample.사업구분, "2", "가로주택", "사업구분 가로주택 코드"),
        사업구분_재건축(CommonCodeGroupSample.사업구분, "3", "재건축", "사업구분 재건축 코드"),
        사업구분_소규모_재건축(CommonCodeGroupSample.사업구분, "4", "소규모 재건축", "사업구분 소규모 재건축 코드"),
        진행단계_기본계획수립(CommonCodeGroupSample.진행단계, "1", "기본계획수립", "진행단계 기본계획수립 코드"),
        진행단계_안전진단(CommonCodeGroupSample.진행단계, "2", "안전진단", "진행단계 안전진단 코드"),
        진행단계_정비구역지정(CommonCodeGroupSample.진행단계, "3", "정비구역지정", "진행단계 정비구역지정 코드"),
        진행단계_조합설립_추진위원회_승인(CommonCodeGroupSample.진행단계, "4", "조합설립 추진위원회 승인", "진행단계 조합설립 추진위원회 승인 코드"),
        진행단계_조합설립인가(CommonCodeGroupSample.진행단계, "5", "조합설립인가", "진행단계 조합설립인가 코드"),
        진행단계_사업시행인가(CommonCodeGroupSample.진행단계, "6", "사업시행인가", "진행단계 사업시행인가 코드"),
        진행단계_관리처분인가(CommonCodeGroupSample.진행단계, "7", "관리처분인가", "진행단계 관리처분인가 코드");

        private final CommonCodeGroupSample group;
        private final String name;
        private final String label;
        private final String description;

        CommonCodeSample(CommonCodeGroupSample group, String name, String label, String description) {
            this.group = group;
            this.name = name;
            this.label = label;
            this.description = description;
        }
    }
}
