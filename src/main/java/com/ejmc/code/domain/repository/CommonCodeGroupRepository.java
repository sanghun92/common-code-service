package com.ejmc.code.domain.repository;

import com.ejmc.code.domain.CommonCodeGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CommonCodeGroupRepository extends JpaRepository<CommonCodeGroup, Long> {

    boolean existsByDetailsName(String name);

    @Query("select distinct grp " +
            "from CommonCodeGroup grp " +
            "left join fetch grp.codes codes " +
            "where grp.id = :id ")
    Optional<CommonCodeGroup> findByIdWithCodes(@Param("id") Long id);
}
