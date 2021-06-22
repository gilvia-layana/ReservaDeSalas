package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ALUNOTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ALUNO.class);
        ALUNO aLUNO1 = new ALUNO();
        aLUNO1.setId(1L);
        ALUNO aLUNO2 = new ALUNO();
        aLUNO2.setId(aLUNO1.getId());
        assertThat(aLUNO1).isEqualTo(aLUNO2);
        aLUNO2.setId(2L);
        assertThat(aLUNO1).isNotEqualTo(aLUNO2);
        aLUNO1.setId(null);
        assertThat(aLUNO1).isNotEqualTo(aLUNO2);
    }
}
