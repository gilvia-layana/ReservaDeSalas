package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SALATest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SALA.class);
        SALA sALA1 = new SALA();
        sALA1.setId(1L);
        SALA sALA2 = new SALA();
        sALA2.setId(sALA1.getId());
        assertThat(sALA1).isEqualTo(sALA2);
        sALA2.setId(2L);
        assertThat(sALA1).isNotEqualTo(sALA2);
        sALA1.setId(null);
        assertThat(sALA1).isNotEqualTo(sALA2);
    }
}
