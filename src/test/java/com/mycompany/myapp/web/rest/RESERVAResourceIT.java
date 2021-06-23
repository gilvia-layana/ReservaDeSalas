package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.RESERVA;
import com.mycompany.myapp.domain.enumeration.StatusReserva;
import com.mycompany.myapp.repository.RESERVARepository;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link RESERVAResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RESERVAResourceIT {

    private static final Integer DEFAULT_COD_RESERVA = 1;
    private static final Integer UPDATED_COD_RESERVA = 2;

    private static final LocalDate DEFAULT_DATA_RESERVA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_RESERVA = LocalDate.now(ZoneId.systemDefault());

    private static final ZonedDateTime DEFAULT_HORARIO_INICIO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_HORARIO_INICIO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_HORARIO_FINAL = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_HORARIO_FINAL = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DATA_SOLICITACAO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATA_SOLICITACAO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_HORARIO_DA_SOLICITACAO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_HORARIO_DA_SOLICITACAO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final StatusReserva DEFAULT_STATUS_RESERVA_SALA = StatusReserva.Confirmada;
    private static final StatusReserva UPDATED_STATUS_RESERVA_SALA = StatusReserva.Cancelada;

    private static final String ENTITY_API_URL = "/api/reservas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RESERVARepository rESERVARepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRESERVAMockMvc;

    private RESERVA rESERVA;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RESERVA createEntity(EntityManager em) {
        RESERVA rESERVA = new RESERVA()
            .codReserva(DEFAULT_COD_RESERVA)
            .dataReserva(DEFAULT_DATA_RESERVA)
            .horarioInicio(DEFAULT_HORARIO_INICIO)
            .horarioFinal(DEFAULT_HORARIO_FINAL)
            .dataSolicitacao(DEFAULT_DATA_SOLICITACAO)
            .horarioDaSolicitacao(DEFAULT_HORARIO_DA_SOLICITACAO)
            .statusReservaSala(DEFAULT_STATUS_RESERVA_SALA);
        return rESERVA;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RESERVA createUpdatedEntity(EntityManager em) {
        RESERVA rESERVA = new RESERVA()
            .codReserva(UPDATED_COD_RESERVA)
            .dataReserva(UPDATED_DATA_RESERVA)
            .horarioInicio(UPDATED_HORARIO_INICIO)
            .horarioFinal(UPDATED_HORARIO_FINAL)
            .dataSolicitacao(UPDATED_DATA_SOLICITACAO)
            .horarioDaSolicitacao(UPDATED_HORARIO_DA_SOLICITACAO)
            .statusReservaSala(UPDATED_STATUS_RESERVA_SALA);
        return rESERVA;
    }

    @BeforeEach
    public void initTest() {
        rESERVA = createEntity(em);
    }

    @Test
    @Transactional
    void createRESERVA() throws Exception {
        int databaseSizeBeforeCreate = rESERVARepository.findAll().size();
        // Create the RESERVA
        restRESERVAMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rESERVA)))
            .andExpect(status().isCreated());

        // Validate the RESERVA in the database
        List<RESERVA> rESERVAList = rESERVARepository.findAll();
        assertThat(rESERVAList).hasSize(databaseSizeBeforeCreate + 1);
        RESERVA testRESERVA = rESERVAList.get(rESERVAList.size() - 1);
        assertThat(testRESERVA.getCodReserva()).isEqualTo(DEFAULT_COD_RESERVA);
        assertThat(testRESERVA.getDataReserva()).isEqualTo(DEFAULT_DATA_RESERVA);
        assertThat(testRESERVA.getHorarioInicio()).isEqualTo(DEFAULT_HORARIO_INICIO);
        assertThat(testRESERVA.getHorarioFinal()).isEqualTo(DEFAULT_HORARIO_FINAL);
        assertThat(testRESERVA.getDataSolicitacao()).isEqualTo(DEFAULT_DATA_SOLICITACAO);
        assertThat(testRESERVA.getHorarioDaSolicitacao()).isEqualTo(DEFAULT_HORARIO_DA_SOLICITACAO);
        assertThat(testRESERVA.getStatusReservaSala()).isEqualTo(DEFAULT_STATUS_RESERVA_SALA);
    }

    @Test
    @Transactional
    void createRESERVAWithExistingId() throws Exception {
        // Create the RESERVA with an existing ID
        rESERVA.setId(1L);

        int databaseSizeBeforeCreate = rESERVARepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRESERVAMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rESERVA)))
            .andExpect(status().isBadRequest());

        // Validate the RESERVA in the database
        List<RESERVA> rESERVAList = rESERVARepository.findAll();
        assertThat(rESERVAList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodReservaIsRequired() throws Exception {
        int databaseSizeBeforeTest = rESERVARepository.findAll().size();
        // set the field null
        rESERVA.setCodReserva(null);

        // Create the RESERVA, which fails.

        restRESERVAMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rESERVA)))
            .andExpect(status().isBadRequest());

        List<RESERVA> rESERVAList = rESERVARepository.findAll();
        assertThat(rESERVAList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRESERVAS() throws Exception {
        // Initialize the database
        rESERVARepository.saveAndFlush(rESERVA);

        // Get all the rESERVAList
        restRESERVAMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rESERVA.getId().intValue())))
            .andExpect(jsonPath("$.[*].codReserva").value(hasItem(DEFAULT_COD_RESERVA)))
            .andExpect(jsonPath("$.[*].dataReserva").value(hasItem(DEFAULT_DATA_RESERVA.toString())))
            .andExpect(jsonPath("$.[*].horarioInicio").value(hasItem(sameInstant(DEFAULT_HORARIO_INICIO))))
            .andExpect(jsonPath("$.[*].horarioFinal").value(hasItem(sameInstant(DEFAULT_HORARIO_FINAL))))
            .andExpect(jsonPath("$.[*].dataSolicitacao").value(hasItem(sameInstant(DEFAULT_DATA_SOLICITACAO))))
            .andExpect(jsonPath("$.[*].horarioDaSolicitacao").value(hasItem(sameInstant(DEFAULT_HORARIO_DA_SOLICITACAO))))
            .andExpect(jsonPath("$.[*].statusReservaSala").value(hasItem(DEFAULT_STATUS_RESERVA_SALA.toString())));
    }

    @Test
    @Transactional
    void getRESERVA() throws Exception {
        // Initialize the database
        rESERVARepository.saveAndFlush(rESERVA);

        // Get the rESERVA
        restRESERVAMockMvc
            .perform(get(ENTITY_API_URL_ID, rESERVA.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rESERVA.getId().intValue()))
            .andExpect(jsonPath("$.codReserva").value(DEFAULT_COD_RESERVA))
            .andExpect(jsonPath("$.dataReserva").value(DEFAULT_DATA_RESERVA.toString()))
            .andExpect(jsonPath("$.horarioInicio").value(sameInstant(DEFAULT_HORARIO_INICIO)))
            .andExpect(jsonPath("$.horarioFinal").value(sameInstant(DEFAULT_HORARIO_FINAL)))
            .andExpect(jsonPath("$.dataSolicitacao").value(sameInstant(DEFAULT_DATA_SOLICITACAO)))
            .andExpect(jsonPath("$.horarioDaSolicitacao").value(sameInstant(DEFAULT_HORARIO_DA_SOLICITACAO)))
            .andExpect(jsonPath("$.statusReservaSala").value(DEFAULT_STATUS_RESERVA_SALA.toString()));
    }

    @Test
    @Transactional
    void getNonExistingRESERVA() throws Exception {
        // Get the rESERVA
        restRESERVAMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRESERVA() throws Exception {
        // Initialize the database
        rESERVARepository.saveAndFlush(rESERVA);

        int databaseSizeBeforeUpdate = rESERVARepository.findAll().size();

        // Update the rESERVA
        RESERVA updatedRESERVA = rESERVARepository.findById(rESERVA.getId()).get();
        // Disconnect from session so that the updates on updatedRESERVA are not directly saved in db
        em.detach(updatedRESERVA);
        updatedRESERVA
            .codReserva(UPDATED_COD_RESERVA)
            .dataReserva(UPDATED_DATA_RESERVA)
            .horarioInicio(UPDATED_HORARIO_INICIO)
            .horarioFinal(UPDATED_HORARIO_FINAL)
            .dataSolicitacao(UPDATED_DATA_SOLICITACAO)
            .horarioDaSolicitacao(UPDATED_HORARIO_DA_SOLICITACAO)
            .statusReservaSala(UPDATED_STATUS_RESERVA_SALA);

        restRESERVAMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRESERVA.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRESERVA))
            )
            .andExpect(status().isOk());

        // Validate the RESERVA in the database
        List<RESERVA> rESERVAList = rESERVARepository.findAll();
        assertThat(rESERVAList).hasSize(databaseSizeBeforeUpdate);
        RESERVA testRESERVA = rESERVAList.get(rESERVAList.size() - 1);
        assertThat(testRESERVA.getCodReserva()).isEqualTo(UPDATED_COD_RESERVA);
        assertThat(testRESERVA.getDataReserva()).isEqualTo(UPDATED_DATA_RESERVA);
        assertThat(testRESERVA.getHorarioInicio()).isEqualTo(UPDATED_HORARIO_INICIO);
        assertThat(testRESERVA.getHorarioFinal()).isEqualTo(UPDATED_HORARIO_FINAL);
        assertThat(testRESERVA.getDataSolicitacao()).isEqualTo(UPDATED_DATA_SOLICITACAO);
        assertThat(testRESERVA.getHorarioDaSolicitacao()).isEqualTo(UPDATED_HORARIO_DA_SOLICITACAO);
        assertThat(testRESERVA.getStatusReservaSala()).isEqualTo(UPDATED_STATUS_RESERVA_SALA);
    }

    @Test
    @Transactional
    void putNonExistingRESERVA() throws Exception {
        int databaseSizeBeforeUpdate = rESERVARepository.findAll().size();
        rESERVA.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRESERVAMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rESERVA.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rESERVA))
            )
            .andExpect(status().isBadRequest());

        // Validate the RESERVA in the database
        List<RESERVA> rESERVAList = rESERVARepository.findAll();
        assertThat(rESERVAList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRESERVA() throws Exception {
        int databaseSizeBeforeUpdate = rESERVARepository.findAll().size();
        rESERVA.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRESERVAMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rESERVA))
            )
            .andExpect(status().isBadRequest());

        // Validate the RESERVA in the database
        List<RESERVA> rESERVAList = rESERVARepository.findAll();
        assertThat(rESERVAList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRESERVA() throws Exception {
        int databaseSizeBeforeUpdate = rESERVARepository.findAll().size();
        rESERVA.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRESERVAMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rESERVA)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the RESERVA in the database
        List<RESERVA> rESERVAList = rESERVARepository.findAll();
        assertThat(rESERVAList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRESERVAWithPatch() throws Exception {
        // Initialize the database
        rESERVARepository.saveAndFlush(rESERVA);

        int databaseSizeBeforeUpdate = rESERVARepository.findAll().size();

        // Update the rESERVA using partial update
        RESERVA partialUpdatedRESERVA = new RESERVA();
        partialUpdatedRESERVA.setId(rESERVA.getId());

        partialUpdatedRESERVA
            .codReserva(UPDATED_COD_RESERVA)
            .dataReserva(UPDATED_DATA_RESERVA)
            .horarioInicio(UPDATED_HORARIO_INICIO)
            .horarioDaSolicitacao(UPDATED_HORARIO_DA_SOLICITACAO);

        restRESERVAMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRESERVA.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRESERVA))
            )
            .andExpect(status().isOk());

        // Validate the RESERVA in the database
        List<RESERVA> rESERVAList = rESERVARepository.findAll();
        assertThat(rESERVAList).hasSize(databaseSizeBeforeUpdate);
        RESERVA testRESERVA = rESERVAList.get(rESERVAList.size() - 1);
        assertThat(testRESERVA.getCodReserva()).isEqualTo(UPDATED_COD_RESERVA);
        assertThat(testRESERVA.getDataReserva()).isEqualTo(UPDATED_DATA_RESERVA);
        assertThat(testRESERVA.getHorarioInicio()).isEqualTo(UPDATED_HORARIO_INICIO);
        assertThat(testRESERVA.getHorarioFinal()).isEqualTo(DEFAULT_HORARIO_FINAL);
        assertThat(testRESERVA.getDataSolicitacao()).isEqualTo(DEFAULT_DATA_SOLICITACAO);
        assertThat(testRESERVA.getHorarioDaSolicitacao()).isEqualTo(UPDATED_HORARIO_DA_SOLICITACAO);
        assertThat(testRESERVA.getStatusReservaSala()).isEqualTo(DEFAULT_STATUS_RESERVA_SALA);
    }

    @Test
    @Transactional
    void fullUpdateRESERVAWithPatch() throws Exception {
        // Initialize the database
        rESERVARepository.saveAndFlush(rESERVA);

        int databaseSizeBeforeUpdate = rESERVARepository.findAll().size();

        // Update the rESERVA using partial update
        RESERVA partialUpdatedRESERVA = new RESERVA();
        partialUpdatedRESERVA.setId(rESERVA.getId());

        partialUpdatedRESERVA
            .codReserva(UPDATED_COD_RESERVA)
            .dataReserva(UPDATED_DATA_RESERVA)
            .horarioInicio(UPDATED_HORARIO_INICIO)
            .horarioFinal(UPDATED_HORARIO_FINAL)
            .dataSolicitacao(UPDATED_DATA_SOLICITACAO)
            .horarioDaSolicitacao(UPDATED_HORARIO_DA_SOLICITACAO)
            .statusReservaSala(UPDATED_STATUS_RESERVA_SALA);

        restRESERVAMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRESERVA.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRESERVA))
            )
            .andExpect(status().isOk());

        // Validate the RESERVA in the database
        List<RESERVA> rESERVAList = rESERVARepository.findAll();
        assertThat(rESERVAList).hasSize(databaseSizeBeforeUpdate);
        RESERVA testRESERVA = rESERVAList.get(rESERVAList.size() - 1);
        assertThat(testRESERVA.getCodReserva()).isEqualTo(UPDATED_COD_RESERVA);
        assertThat(testRESERVA.getDataReserva()).isEqualTo(UPDATED_DATA_RESERVA);
        assertThat(testRESERVA.getHorarioInicio()).isEqualTo(UPDATED_HORARIO_INICIO);
        assertThat(testRESERVA.getHorarioFinal()).isEqualTo(UPDATED_HORARIO_FINAL);
        assertThat(testRESERVA.getDataSolicitacao()).isEqualTo(UPDATED_DATA_SOLICITACAO);
        assertThat(testRESERVA.getHorarioDaSolicitacao()).isEqualTo(UPDATED_HORARIO_DA_SOLICITACAO);
        assertThat(testRESERVA.getStatusReservaSala()).isEqualTo(UPDATED_STATUS_RESERVA_SALA);
    }

    @Test
    @Transactional
    void patchNonExistingRESERVA() throws Exception {
        int databaseSizeBeforeUpdate = rESERVARepository.findAll().size();
        rESERVA.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRESERVAMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, rESERVA.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rESERVA))
            )
            .andExpect(status().isBadRequest());

        // Validate the RESERVA in the database
        List<RESERVA> rESERVAList = rESERVARepository.findAll();
        assertThat(rESERVAList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRESERVA() throws Exception {
        int databaseSizeBeforeUpdate = rESERVARepository.findAll().size();
        rESERVA.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRESERVAMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rESERVA))
            )
            .andExpect(status().isBadRequest());

        // Validate the RESERVA in the database
        List<RESERVA> rESERVAList = rESERVARepository.findAll();
        assertThat(rESERVAList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRESERVA() throws Exception {
        int databaseSizeBeforeUpdate = rESERVARepository.findAll().size();
        rESERVA.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRESERVAMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(rESERVA)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the RESERVA in the database
        List<RESERVA> rESERVAList = rESERVARepository.findAll();
        assertThat(rESERVAList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRESERVA() throws Exception {
        // Initialize the database
        rESERVARepository.saveAndFlush(rESERVA);

        int databaseSizeBeforeDelete = rESERVARepository.findAll().size();

        // Delete the rESERVA
        restRESERVAMockMvc
            .perform(delete(ENTITY_API_URL_ID, rESERVA.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RESERVA> rESERVAList = rESERVARepository.findAll();
        assertThat(rESERVAList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
