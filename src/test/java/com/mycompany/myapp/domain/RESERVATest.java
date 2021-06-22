package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RESERVATest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RESERVA.class);
        RESERVA rESERVA1 = new RESERVA();
        rESERVA1.setId(1L);
        RESERVA rESERVA2 = new RESERVA();
        rESERVA2.setId(rESERVA1.getId());
        assertThat(rESERVA1).isEqualTo(rESERVA2);
        rESERVA2.setId(2L);
        assertThat(rESERVA1).isNotEqualTo(rESERVA2);
        rESERVA1.setId(null);
        assertThat(rESERVA1).isNotEqualTo(rESERVA2);
    }
}
