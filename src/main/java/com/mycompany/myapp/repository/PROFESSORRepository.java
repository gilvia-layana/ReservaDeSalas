package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.PROFESSOR;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PROFESSOR entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PROFESSORRepository extends JpaRepository<PROFESSOR, Long> {}
