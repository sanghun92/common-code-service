package com.ejmc.code.domain;

import com.ejmc.common.domain.BaseTimeEntity;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode(of = "details", callSuper = false)
@ToString(exclude = "group")
@Entity
@Table(uniqueConstraints = @UniqueConstraint(name = "uk_code_name", columnNames = "name"))
@Where(clause = "is_deleted = false")
public class CommonCode extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private CommonCodeGroup group;

    @Embedded
    @Getter(AccessLevel.PRIVATE)
    private CommonCodeDetails details;

    @Column(nullable = false)
    private Integer sort;

    private boolean isDeleted;

    public CommonCode(CommonCodeGroup group,
                      String name,
                      String label,
                      String description,
                      Integer sort) {
        this.group = group;
        this.details = new CommonCodeDetails(name, label, description);
        this.sort = sort;
    }

    public CommonCode(CommonCodeGroup group, CommonCodeDetails details, Integer sort) {
        this.group = group;
        this.details = details;
        this.sort = sort;
    }

    public String getName() {
        return this.details.getName();
    }

    public String getLabel() {
        return this.details.getLabel();
    }

    public String getDescription() {
        return this.details.getDescription();
    }

    public boolean match(String otherName) {
        return this.details.matchName(otherName);
    }
}
