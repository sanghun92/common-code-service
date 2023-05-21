package com.ejmc.code.domain.repository;

import com.ejmc.code.domain.CommonCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommonCodeRepository extends JpaRepository<CommonCode, Long> {
    Optional<CommonCode> findByDetailsName(String codeName);

    boolean existsByDetailsName(String name);
}
