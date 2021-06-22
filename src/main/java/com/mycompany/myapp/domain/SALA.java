package com.mycompany.myapp.domain;

import com.mycompany.myapp.domain.enumeration.StatusSala;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A SALA.
 */
@Entity
@Table(name = "sala")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SALA implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "cod_sala", nullable = false)
    private Integer codSala;

    @Column(name = "nome")
    private String nome;

    @Column(name = "local")
    private String local;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusSala status;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SALA id(Long id) {
        this.id = id;
        return this;
    }

    public Integer getCodSala() {
        return this.codSala;
    }

    public SALA codSala(Integer codSala) {
        this.codSala = codSala;
        return this;
    }

    public void setCodSala(Integer codSala) {
        this.codSala = codSala;
    }

    public String getNome() {
        return this.nome;
    }

    public SALA nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLocal() {
        return this.local;
    }

    public SALA local(String local) {
        this.local = local;
        return this;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public StatusSala getStatus() {
        return this.status;
    }

    public SALA status(StatusSala status) {
        this.status = status;
        return this;
    }

    public void setStatus(StatusSala status) {
        this.status = status;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SALA)) {
            return false;
        }
        return id != null && id.equals(((SALA) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SALA{" +
            "id=" + getId() +
            ", codSala=" + getCodSala() +
            ", nome='" + getNome() + "'" +
            ", local='" + getLocal() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
