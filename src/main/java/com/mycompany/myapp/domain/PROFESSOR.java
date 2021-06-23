package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PROFESSOR.
 */
@Entity
@Table(name = "professor")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PROFESSOR implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "matricula_prof", nullable = false)
    private Integer matriculaProf;

    @Column(name = "nome")
    private String nome;

    @Column(name = "disciplina")
    private String disciplina;

    @Column(name = "faculdade")
    private String faculdade;

    @OneToMany(mappedBy = "pROFESSOR")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "aLUNO", "pROFESSOR" }, allowSetters = true)
    private Set<DadosPessoais> dadosPessoais = new HashSet<>();

    @OneToMany(mappedBy = "rESERVA")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "sALA", "cONSULTA", "rESERVA", "rESERVA" }, allowSetters = true)
    private Set<RESERVA> pROFESSORS = new HashSet<>();

    @OneToMany(mappedBy = "rESERVA")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "sALA", "cONSULTA", "rESERVA", "rESERVA" }, allowSetters = true)
    private Set<RESERVA> pROFESSORS = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PROFESSOR id(Long id) {
        this.id = id;
        return this;
    }

    public Integer getMatriculaProf() {
        return this.matriculaProf;
    }

    public PROFESSOR matriculaProf(Integer matriculaProf) {
        this.matriculaProf = matriculaProf;
        return this;
    }

    public void setMatriculaProf(Integer matriculaProf) {
        this.matriculaProf = matriculaProf;
    }

    public String getNome() {
        return this.nome;
    }

    public PROFESSOR nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDisciplina() {
        return this.disciplina;
    }

    public PROFESSOR disciplina(String disciplina) {
        this.disciplina = disciplina;
        return this;
    }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }

    public String getFaculdade() {
        return this.faculdade;
    }

    public PROFESSOR faculdade(String faculdade) {
        this.faculdade = faculdade;
        return this;
    }

    public void setFaculdade(String faculdade) {
        this.faculdade = faculdade;
    }

    public Set<DadosPessoais> getDadosPessoais() {
        return this.dadosPessoais;
    }

    public PROFESSOR dadosPessoais(Set<DadosPessoais> dadosPessoais) {
        this.setDadosPessoais(dadosPessoais);
        return this;
    }

    public PROFESSOR addDadosPessoais(DadosPessoais dadosPessoais) {
        this.dadosPessoais.add(dadosPessoais);
        dadosPessoais.setPROFESSOR(this);
        return this;
    }

    public PROFESSOR removeDadosPessoais(DadosPessoais dadosPessoais) {
        this.dadosPessoais.remove(dadosPessoais);
        dadosPessoais.setPROFESSOR(null);
        return this;
    }

    public void setDadosPessoais(Set<DadosPessoais> dadosPessoais) {
        if (this.dadosPessoais != null) {
            this.dadosPessoais.forEach(i -> i.setPROFESSOR(null));
        }
        if (dadosPessoais != null) {
            dadosPessoais.forEach(i -> i.setPROFESSOR(this));
        }
        this.dadosPessoais = dadosPessoais;
    }

    public Set<RESERVA> getPROFESSORS() {
        return this.pROFESSORS;
    }

    public PROFESSOR pROFESSORS(Set<RESERVA> rESERVAS) {
        this.setPROFESSORS(rESERVAS);
        return this;
    }

    public PROFESSOR addPROFESSOR(RESERVA rESERVA) {
        this.pROFESSORS.add(rESERVA);
        rESERVA.setRESERVA(this);
        return this;
    }

    public PROFESSOR removePROFESSOR(RESERVA rESERVA) {
        this.pROFESSORS.remove(rESERVA);
        rESERVA.setRESERVA(null);
        return this;
    }

    public void setPROFESSORS(Set<RESERVA> rESERVAS) {
        if (this.pROFESSORS != null) {
            this.pROFESSORS.forEach(i -> i.setRESERVA(null));
        }
        if (rESERVAS != null) {
            rESERVAS.forEach(i -> i.setRESERVA(this));
        }
        this.pROFESSORS = rESERVAS;
    }

    public Set<RESERVA> getPROFESSORS() {
        return this.pROFESSORS;
    }

    public PROFESSOR pROFESSORS(Set<RESERVA> rESERVAS) {
        this.setPROFESSORS(rESERVAS);
        return this;
    }

    public PROFESSOR addPROFESSOR(RESERVA rESERVA) {
        this.pROFESSORS.add(rESERVA);
        rESERVA.setRESERVA(this);
        return this;
    }

    public PROFESSOR removePROFESSOR(RESERVA rESERVA) {
        this.pROFESSORS.remove(rESERVA);
        rESERVA.setRESERVA(null);
        return this;
    }

    public void setPROFESSORS(Set<RESERVA> rESERVAS) {
        if (this.pROFESSORS != null) {
            this.pROFESSORS.forEach(i -> i.setRESERVA(null));
        }
        if (rESERVAS != null) {
            rESERVAS.forEach(i -> i.setRESERVA(this));
        }
        this.pROFESSORS = rESERVAS;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PROFESSOR)) {
            return false;
        }
        return id != null && id.equals(((PROFESSOR) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PROFESSOR{" +
            "id=" + getId() +
            ", matriculaProf=" + getMatriculaProf() +
            ", nome='" + getNome() + "'" +
            ", disciplina='" + getDisciplina() + "'" +
            ", faculdade='" + getFaculdade() + "'" +
            "}";
    }
}
