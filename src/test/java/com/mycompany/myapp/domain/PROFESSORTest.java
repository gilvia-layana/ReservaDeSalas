package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PROFESSORTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PROFESSOR.class);
        PROFESSOR pROFESSOR1 = new PROFESSOR();
        pROFESSOR1.setId(1L);
        PROFESSOR pROFESSOR2 = new PROFESSOR();
        pROFESSOR2.setId(pROFESSOR1.getId());
        assertThat(pROFESSOR1).isEqualTo(pROFESSOR2);
        pROFESSOR2.setId(2L);
        assertThat(pROFESSOR1).isNotEqualTo(pROFESSOR2);
        pROFESSOR1.setId(null);
        assertThat(pROFESSOR1).isNotEqualTo(pROFESSOR2);
    }
}
