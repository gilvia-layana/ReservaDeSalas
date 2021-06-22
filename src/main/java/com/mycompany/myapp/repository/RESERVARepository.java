package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.RESERVA;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the RESERVA entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RESERVARepository extends JpaRepository<RESERVA, Long> {}
