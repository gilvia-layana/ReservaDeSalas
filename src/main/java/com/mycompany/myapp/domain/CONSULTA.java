package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CONSULTA.
 */
@Entity
@Table(name = "consulta")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CONSULTA implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "cod_consulta", nullable = false)
    private Integer codConsulta;

    @Column(name = "data_da_consulta")
    private ZonedDateTime dataDaConsulta;

    @Column(name = "horario_da_consulta")
    private ZonedDateTime horarioDaConsulta;

    @ManyToOne
    @JsonIgnoreProperties(value = { "dadosPessoais" }, allowSetters = true)
    private ALUNO aLUNO;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CONSULTA id(Long id) {
        this.id = id;
        return this;
    }

    public Integer getCodConsulta() {
        return this.codConsulta;
    }

    public CONSULTA codConsulta(Integer codConsulta) {
        this.codConsulta = codConsulta;
        return this;
    }

    public void setCodConsulta(Integer codConsulta) {
        this.codConsulta = codConsulta;
    }

    public ZonedDateTime getDataDaConsulta() {
        return this.dataDaConsulta;
    }

    public CONSULTA dataDaConsulta(ZonedDateTime dataDaConsulta) {
        this.dataDaConsulta = dataDaConsulta;
        return this;
    }

    public void setDataDaConsulta(ZonedDateTime dataDaConsulta) {
        this.dataDaConsulta = dataDaConsulta;
    }

    public ZonedDateTime getHorarioDaConsulta() {
        return this.horarioDaConsulta;
    }

    public CONSULTA horarioDaConsulta(ZonedDateTime horarioDaConsulta) {
        this.horarioDaConsulta = horarioDaConsulta;
        return this;
    }

    public void setHorarioDaConsulta(ZonedDateTime horarioDaConsulta) {
        this.horarioDaConsulta = horarioDaConsulta;
    }

    public ALUNO getALUNO() {
        return this.aLUNO;
    }

    public CONSULTA aLUNO(ALUNO aLUNO) {
        this.setALUNO(aLUNO);
        return this;
    }

    public void setALUNO(ALUNO aLUNO) {
        this.aLUNO = aLUNO;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CONSULTA)) {
            return false;
        }
        return id != null && id.equals(((CONSULTA) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CONSULTA{" +
            "id=" + getId() +
            ", codConsulta=" + getCodConsulta() +
            ", dataDaConsulta='" + getDataDaConsulta() + "'" +
            ", horarioDaConsulta='" + getHorarioDaConsulta() + "'" +
            "}";
    }
}
