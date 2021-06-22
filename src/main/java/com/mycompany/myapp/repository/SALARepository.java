package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.SALA;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the SALA entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SALARepository extends JpaRepository<SALA, Long> {}
