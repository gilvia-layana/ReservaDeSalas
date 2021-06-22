package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ALUNO;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ALUNO entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ALUNORepository extends JpaRepository<ALUNO, Long> {}
