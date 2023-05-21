package com.ejmc.code.domain;

import com.ejmc.common.domain.BaseTimeEntity;
import com.ejmc.common.exception.ValidationException;
import lombok.*;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode(of = "details", callSuper = false)
@ToString(exclude = "codes")
@Entity
@Table(uniqueConstraints = @UniqueConstraint(name = "uk_code_group_name", columnNames = "name"))
public class CommonCodeGroup extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @Getter(AccessLevel.PRIVATE)
    private CommonCodeGroupDetails details;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("sort")
    private Set<CommonCode> codes = new LinkedHashSet<>();

    public CommonCodeGroup(String name, String description) {
        this.details = new CommonCodeGroupDetails(name, description);
    }

    public String getName() {
        return this.details.getName();
    }

    public boolean hasCommonCode(String codeName) {
        return this.codes.stream().anyMatch(code -> code.match(codeName));
    }

    public void addCommonCode(CommonCodeDetails codeDetails) {
        if(hasCommonCode(codeDetails.getName())) {
            throw new ValidationException("해당 그룹에 이미 등록 된 공통코드 입니다. - group : %s, codeName : %s", this.details.getName(), codeDetails.getName());
        }

        CommonCode commonCode = new CommonCode(this, codeDetails, getNextCodeSort());
        this.codes.add(commonCode);
    }

    private int getNextCodeSort() {
        int currentMaxSort = this.codes.stream()
                .mapToInt(CommonCode::getSort)
                .max()
                .orElse(0);
        return currentMaxSort + 1;
    }
}
