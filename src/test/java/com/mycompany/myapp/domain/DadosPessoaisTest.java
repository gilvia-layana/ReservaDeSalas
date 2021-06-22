package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DadosPessoaisTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DadosPessoais.class);
        DadosPessoais dadosPessoais1 = new DadosPessoais();
        dadosPessoais1.setId(1L);
        DadosPessoais dadosPessoais2 = new DadosPessoais();
        dadosPessoais2.setId(dadosPessoais1.getId());
        assertThat(dadosPessoais1).isEqualTo(dadosPessoais2);
        dadosPessoais2.setId(2L);
        assertThat(dadosPessoais1).isNotEqualTo(dadosPessoais2);
        dadosPessoais1.setId(null);
        assertThat(dadosPessoais1).isNotEqualTo(dadosPessoais2);
    }
}
