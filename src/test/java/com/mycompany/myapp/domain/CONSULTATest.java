package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CONSULTATest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CONSULTA.class);
        CONSULTA cONSULTA1 = new CONSULTA();
        cONSULTA1.setId(1L);
        CONSULTA cONSULTA2 = new CONSULTA();
        cONSULTA2.setId(cONSULTA1.getId());
        assertThat(cONSULTA1).isEqualTo(cONSULTA2);
        cONSULTA2.setId(2L);
        assertThat(cONSULTA1).isNotEqualTo(cONSULTA2);
        cONSULTA1.setId(null);
        assertThat(cONSULTA1).isNotEqualTo(cONSULTA2);
    }
}
