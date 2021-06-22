package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DadosPessoais.
 */
@Entity
@Table(name = "dados_pessoais")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DadosPessoais implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "endereco")
    private String endereco;

    @Column(name = "telefone")
    private String telefone;

    @Column(name = "email")
    private String email;

    @ManyToOne
    @JsonIgnoreProperties(value = { "dadosPessoais" }, allowSetters = true)
    private ALUNO aLUNO;

    @ManyToOne
    @JsonIgnoreProperties(value = { "dadosPessoais" }, allowSetters = true)
    private PROFESSOR pROFESSOR;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DadosPessoais id(Long id) {
        this.id = id;
        return this;
    }

    public String getEndereco() {
        return this.endereco;
    }

    public DadosPessoais endereco(String endereco) {
        this.endereco = endereco;
        return this;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return this.telefone;
    }

    public DadosPessoais telefone(String telefone) {
        this.telefone = telefone;
        return this;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return this.email;
    }

    public DadosPessoais email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ALUNO getALUNO() {
        return this.aLUNO;
    }

    public DadosPessoais aLUNO(ALUNO aLUNO) {
        this.setALUNO(aLUNO);
        return this;
    }

    public void setALUNO(ALUNO aLUNO) {
        this.aLUNO = aLUNO;
    }

    public PROFESSOR getPROFESSOR() {
        return this.pROFESSOR;
    }

    public DadosPessoais pROFESSOR(PROFESSOR pROFESSOR) {
        this.setPROFESSOR(pROFESSOR);
        return this;
    }

    public void setPROFESSOR(PROFESSOR pROFESSOR) {
        this.pROFESSOR = pROFESSOR;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DadosPessoais)) {
            return false;
        }
        return id != null && id.equals(((DadosPessoais) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DadosPessoais{" +
            "id=" + getId() +
            ", endereco='" + getEndereco() + "'" +
            ", telefone='" + getTelefone() + "'" +
            ", email='" + getEmail() + "'" +
            "}";
    }
}
