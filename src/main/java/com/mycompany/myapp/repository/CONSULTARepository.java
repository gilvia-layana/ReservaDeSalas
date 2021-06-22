package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.CONSULTA;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CONSULTA entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CONSULTARepository extends JpaRepository<CONSULTA, Long> {}
