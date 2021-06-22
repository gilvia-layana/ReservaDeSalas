package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.Area;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ALUNO.
 */
@Entity
@Table(name = "aluno")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ALUNO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "matricula_aluno", nullable = false)
    private Integer matriculaAluno;

    @Column(name = "nome")
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(name = "area_de_conhecimento")
    private Area areaDeConhecimento;

    @Column(name = "curso")
    private String curso;

    @OneToMany(mappedBy = "aLUNO")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "aLUNO", "pROFESSOR" }, allowSetters = true)
    private Set<DadosPessoais> dadosPessoais = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ALUNO id(Long id) {
        this.id = id;
        return this;
    }

    public Integer getMatriculaAluno() {
        return this.matriculaAluno;
    }

    public ALUNO matriculaAluno(Integer matriculaAluno) {
        this.matriculaAluno = matriculaAluno;
        return this;
    }

    public void setMatriculaAluno(Integer matriculaAluno) {
        this.matriculaAluno = matriculaAluno;
    }

    public String getNome() {
        return this.nome;
    }

    public ALUNO nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Area getAreaDeConhecimento() {
        return this.areaDeConhecimento;
    }

    public ALUNO areaDeConhecimento(Area areaDeConhecimento) {
        this.areaDeConhecimento = areaDeConhecimento;
        return this;
    }

    public void setAreaDeConhecimento(Area areaDeConhecimento) {
        this.areaDeConhecimento = areaDeConhecimento;
    }

    public String getCurso() {
        return this.curso;
    }

    public ALUNO curso(String curso) {
        this.curso = curso;
        return this;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public Set<DadosPessoais> getDadosPessoais() {
        return this.dadosPessoais;
    }

    public ALUNO dadosPessoais(Set<DadosPessoais> dadosPessoais) {
        this.setDadosPessoais(dadosPessoais);
        return this;
    }

    public ALUNO addDadosPessoais(DadosPessoais dadosPessoais) {
        this.dadosPessoais.add(dadosPessoais);
        dadosPessoais.setALUNO(this);
        return this;
    }

    public ALUNO removeDadosPessoais(DadosPessoais dadosPessoais) {
        this.dadosPessoais.remove(dadosPessoais);
        dadosPessoais.setALUNO(null);
        return this;
    }

    public void setDadosPessoais(Set<DadosPessoais> dadosPessoais) {
        if (this.dadosPessoais != null) {
            this.dadosPessoais.forEach(i -> i.setALUNO(null));
        }
        if (dadosPessoais != null) {
            dadosPessoais.forEach(i -> i.setALUNO(this));
        }
        this.dadosPessoais = dadosPessoais;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ALUNO)) {
            return false;
        }
        return id != null && id.equals(((ALUNO) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ALUNO{" +
            "id=" + getId() +
            ", matriculaAluno=" + getMatriculaAluno() +
            ", nome='" + getNome() + "'" +
            ", areaDeConhecimento='" + getAreaDeConhecimento() + "'" +
            ", curso='" + getCurso() + "'" +
            "}";
    }
}
