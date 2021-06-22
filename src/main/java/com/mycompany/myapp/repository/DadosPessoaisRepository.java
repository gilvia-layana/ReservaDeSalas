package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.DadosPessoais;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DadosPessoais entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DadosPessoaisRepository extends JpaRepository<DadosPessoais, Long> {}
