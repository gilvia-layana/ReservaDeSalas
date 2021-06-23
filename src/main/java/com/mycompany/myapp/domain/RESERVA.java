package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.StatusReserva;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A RESERVA.
 */
@Entity
@Table(name = "reserva")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RESERVA implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "cod_reserva", nullable = false)
    private Integer codReserva;

    @Column(name = "data_reserva")
    private LocalDate dataReserva;

    @Column(name = "horario_inicio")
    private ZonedDateTime horarioInicio;

    @Column(name = "horario_final")
    private ZonedDateTime horarioFinal;

    @Column(name = "data_solicitacao")
    private LocalDate dataSolicitacao;

    @Column(name = "horario_da_solicitacao")
    private ZonedDateTime horarioDaSolicitacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_reserva_sala")
    private StatusReserva statusReservaSala;

    @ManyToOne
    private SALA sALA;

    @ManyToOne
    @JsonIgnoreProperties(value = { "aLUNO" }, allowSetters = true)
    private CONSULTA cONSULTA;

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

    public RESERVA id(Long id) {
        this.id = id;
        return this;
    }

    public Integer getCodReserva() {
        return this.codReserva;
    }

    public RESERVA codReserva(Integer codReserva) {
        this.codReserva = codReserva;
        return this;
    }

    public void setCodReserva(Integer codReserva) {
        this.codReserva = codReserva;
    }

    public LocalDate getDataReserva() {
        return this.dataReserva;
    }

    public RESERVA dataReserva(LocalDate dataReserva) {
        this.dataReserva = dataReserva;
        return this;
    }

    public void setDataReserva(LocalDate dataReserva) {
        this.dataReserva = dataReserva;
    }

    public ZonedDateTime getHorarioInicio() {
        return this.horarioInicio;
    }

    public RESERVA horarioInicio(ZonedDateTime horarioInicio) {
        this.horarioInicio = horarioInicio;
        return this;
    }

    public void setHorarioInicio(ZonedDateTime horarioInicio) {
        this.horarioInicio = horarioInicio;
    }

    public ZonedDateTime getHorarioFinal() {
        return this.horarioFinal;
    }

    public RESERVA horarioFinal(ZonedDateTime horarioFinal) {
        this.horarioFinal = horarioFinal;
        return this;
    }

    public void setHorarioFinal(ZonedDateTime horarioFinal) {
        this.horarioFinal = horarioFinal;
    }

    public LocalDate getDataSolicitacao() {
        return this.dataSolicitacao;
    }

    public RESERVA dataSolicitacao(LocalDate dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
        return this;
    }

    public void setDataSolicitacao(LocalDate dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }

    public ZonedDateTime getHorarioDaSolicitacao() {
        return this.horarioDaSolicitacao;
    }

    public RESERVA horarioDaSolicitacao(ZonedDateTime horarioDaSolicitacao) {
        this.horarioDaSolicitacao = horarioDaSolicitacao;
        return this;
    }

    public void setHorarioDaSolicitacao(ZonedDateTime horarioDaSolicitacao) {
        this.horarioDaSolicitacao = horarioDaSolicitacao;
    }

    public StatusReserva getStatusReservaSala() {
        return this.statusReservaSala;
    }

    public RESERVA statusReservaSala(StatusReserva statusReservaSala) {
        this.statusReservaSala = statusReservaSala;
        return this;
    }

    public void setStatusReservaSala(StatusReserva statusReservaSala) {
        this.statusReservaSala = statusReservaSala;
    }

    public SALA getSALA() {
        return this.sALA;
    }

    public RESERVA sALA(SALA sALA) {
        this.setSALA(sALA);
        return this;
    }

    public void setSALA(SALA sALA) {
        this.sALA = sALA;
    }

    public CONSULTA getCONSULTA() {
        return this.cONSULTA;
    }

    public RESERVA cONSULTA(CONSULTA cONSULTA) {
        this.setCONSULTA(cONSULTA);
        return this;
    }

    public void setCONSULTA(CONSULTA cONSULTA) {
        this.cONSULTA = cONSULTA;
    }

    public PROFESSOR getPROFESSOR() {
        return this.pROFESSOR;
    }

    public RESERVA pROFESSOR(PROFESSOR pROFESSOR) {
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
        if (!(o instanceof RESERVA)) {
            return false;
        }
        return id != null && id.equals(((RESERVA) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RESERVA{" +
            "id=" + getId() +
            ", codReserva=" + getCodReserva() +
            ", dataReserva='" + getDataReserva() + "'" +
            ", horarioInicio='" + getHorarioInicio() + "'" +
            ", horarioFinal='" + getHorarioFinal() + "'" +
            ", dataSolicitacao='" + getDataSolicitacao() + "'" +
            ", horarioDaSolicitacao='" + getHorarioDaSolicitacao() + "'" +
            ", statusReservaSala='" + getStatusReservaSala() + "'" +
            "}";
    }
}
