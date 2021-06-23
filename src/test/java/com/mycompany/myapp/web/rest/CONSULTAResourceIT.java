package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.CONSULTA;
import com.mycompany.myapp.repository.CONSULTARepository;
import java.time.Instant;
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
 * Integration tests for the {@link CONSULTAResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CONSULTAResourceIT {

    private static final Integer DEFAULT_COD_CONSULTA = 1;
    private static final Integer UPDATED_COD_CONSULTA = 2;

    private static final ZonedDateTime DEFAULT_DATA_DA_CONSULTA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATA_DA_CONSULTA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_HORARIO_DA_CONSULTA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_HORARIO_DA_CONSULTA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/consultas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CONSULTARepository cONSULTARepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCONSULTAMockMvc;

    private CONSULTA cONSULTA;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CONSULTA createEntity(EntityManager em) {
        CONSULTA cONSULTA = new CONSULTA()
            .codConsulta(DEFAULT_COD_CONSULTA)
            .dataDaConsulta(DEFAULT_DATA_DA_CONSULTA)
            .horarioDaConsulta(DEFAULT_HORARIO_DA_CONSULTA);
        return cONSULTA;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CONSULTA createUpdatedEntity(EntityManager em) {
        CONSULTA cONSULTA = new CONSULTA()
            .codConsulta(UPDATED_COD_CONSULTA)
            .dataDaConsulta(UPDATED_DATA_DA_CONSULTA)
            .horarioDaConsulta(UPDATED_HORARIO_DA_CONSULTA);
        return cONSULTA;
    }

    @BeforeEach
    public void initTest() {
        cONSULTA = createEntity(em);
    }

    @Test
    @Transactional
    void createCONSULTA() throws Exception {
        int databaseSizeBeforeCreate = cONSULTARepository.findAll().size();
        // Create the CONSULTA
        restCONSULTAMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cONSULTA)))
            .andExpect(status().isCreated());

        // Validate the CONSULTA in the database
        List<CONSULTA> cONSULTAList = cONSULTARepository.findAll();
        assertThat(cONSULTAList).hasSize(databaseSizeBeforeCreate + 1);
        CONSULTA testCONSULTA = cONSULTAList.get(cONSULTAList.size() - 1);
        assertThat(testCONSULTA.getCodConsulta()).isEqualTo(DEFAULT_COD_CONSULTA);
        assertThat(testCONSULTA.getDataDaConsulta()).isEqualTo(DEFAULT_DATA_DA_CONSULTA);
        assertThat(testCONSULTA.getHorarioDaConsulta()).isEqualTo(DEFAULT_HORARIO_DA_CONSULTA);
    }

    @Test
    @Transactional
    void createCONSULTAWithExistingId() throws Exception {
        // Create the CONSULTA with an existing ID
        cONSULTA.setId(1L);

        int databaseSizeBeforeCreate = cONSULTARepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCONSULTAMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cONSULTA)))
            .andExpect(status().isBadRequest());

        // Validate the CONSULTA in the database
        List<CONSULTA> cONSULTAList = cONSULTARepository.findAll();
        assertThat(cONSULTAList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodConsultaIsRequired() throws Exception {
        int databaseSizeBeforeTest = cONSULTARepository.findAll().size();
        // set the field null
        cONSULTA.setCodConsulta(null);

        // Create the CONSULTA, which fails.

        restCONSULTAMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cONSULTA)))
            .andExpect(status().isBadRequest());

        List<CONSULTA> cONSULTAList = cONSULTARepository.findAll();
        assertThat(cONSULTAList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCONSULTAS() throws Exception {
        // Initialize the database
        cONSULTARepository.saveAndFlush(cONSULTA);

        // Get all the cONSULTAList
        restCONSULTAMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cONSULTA.getId().intValue())))
            .andExpect(jsonPath("$.[*].codConsulta").value(hasItem(DEFAULT_COD_CONSULTA)))
            .andExpect(jsonPath("$.[*].dataDaConsulta").value(hasItem(sameInstant(DEFAULT_DATA_DA_CONSULTA))))
            .andExpect(jsonPath("$.[*].horarioDaConsulta").value(hasItem(sameInstant(DEFAULT_HORARIO_DA_CONSULTA))));
    }

    @Test
    @Transactional
    void getCONSULTA() throws Exception {
        // Initialize the database
        cONSULTARepository.saveAndFlush(cONSULTA);

        // Get the cONSULTA
        restCONSULTAMockMvc
            .perform(get(ENTITY_API_URL_ID, cONSULTA.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cONSULTA.getId().intValue()))
            .andExpect(jsonPath("$.codConsulta").value(DEFAULT_COD_CONSULTA))
            .andExpect(jsonPath("$.dataDaConsulta").value(sameInstant(DEFAULT_DATA_DA_CONSULTA)))
            .andExpect(jsonPath("$.horarioDaConsulta").value(sameInstant(DEFAULT_HORARIO_DA_CONSULTA)));
    }

    @Test
    @Transactional
    void getNonExistingCONSULTA() throws Exception {
        // Get the cONSULTA
        restCONSULTAMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCONSULTA() throws Exception {
        // Initialize the database
        cONSULTARepository.saveAndFlush(cONSULTA);

        int databaseSizeBeforeUpdate = cONSULTARepository.findAll().size();

        // Update the cONSULTA
        CONSULTA updatedCONSULTA = cONSULTARepository.findById(cONSULTA.getId()).get();
        // Disconnect from session so that the updates on updatedCONSULTA are not directly saved in db
        em.detach(updatedCONSULTA);
        updatedCONSULTA
            .codConsulta(UPDATED_COD_CONSULTA)
            .dataDaConsulta(UPDATED_DATA_DA_CONSULTA)
            .horarioDaConsulta(UPDATED_HORARIO_DA_CONSULTA);

        restCONSULTAMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCONSULTA.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCONSULTA))
            )
            .andExpect(status().isOk());

        // Validate the CONSULTA in the database
        List<CONSULTA> cONSULTAList = cONSULTARepository.findAll();
        assertThat(cONSULTAList).hasSize(databaseSizeBeforeUpdate);
        CONSULTA testCONSULTA = cONSULTAList.get(cONSULTAList.size() - 1);
        assertThat(testCONSULTA.getCodConsulta()).isEqualTo(UPDATED_COD_CONSULTA);
        assertThat(testCONSULTA.getDataDaConsulta()).isEqualTo(UPDATED_DATA_DA_CONSULTA);
        assertThat(testCONSULTA.getHorarioDaConsulta()).isEqualTo(UPDATED_HORARIO_DA_CONSULTA);
    }

    @Test
    @Transactional
    void putNonExistingCONSULTA() throws Exception {
        int databaseSizeBeforeUpdate = cONSULTARepository.findAll().size();
        cONSULTA.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCONSULTAMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cONSULTA.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cONSULTA))
            )
            .andExpect(status().isBadRequest());

        // Validate the CONSULTA in the database
        List<CONSULTA> cONSULTAList = cONSULTARepository.findAll();
        assertThat(cONSULTAList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCONSULTA() throws Exception {
        int databaseSizeBeforeUpdate = cONSULTARepository.findAll().size();
        cONSULTA.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCONSULTAMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cONSULTA))
            )
            .andExpect(status().isBadRequest());

        // Validate the CONSULTA in the database
        List<CONSULTA> cONSULTAList = cONSULTARepository.findAll();
        assertThat(cONSULTAList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCONSULTA() throws Exception {
        int databaseSizeBeforeUpdate = cONSULTARepository.findAll().size();
        cONSULTA.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCONSULTAMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cONSULTA)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CONSULTA in the database
        List<CONSULTA> cONSULTAList = cONSULTARepository.findAll();
        assertThat(cONSULTAList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCONSULTAWithPatch() throws Exception {
        // Initialize the database
        cONSULTARepository.saveAndFlush(cONSULTA);

        int databaseSizeBeforeUpdate = cONSULTARepository.findAll().size();

        // Update the cONSULTA using partial update
        CONSULTA partialUpdatedCONSULTA = new CONSULTA();
        partialUpdatedCONSULTA.setId(cONSULTA.getId());

        partialUpdatedCONSULTA.codConsulta(UPDATED_COD_CONSULTA).horarioDaConsulta(UPDATED_HORARIO_DA_CONSULTA);

        restCONSULTAMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCONSULTA.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCONSULTA))
            )
            .andExpect(status().isOk());

        // Validate the CONSULTA in the database
        List<CONSULTA> cONSULTAList = cONSULTARepository.findAll();
        assertThat(cONSULTAList).hasSize(databaseSizeBeforeUpdate);
        CONSULTA testCONSULTA = cONSULTAList.get(cONSULTAList.size() - 1);
        assertThat(testCONSULTA.getCodConsulta()).isEqualTo(UPDATED_COD_CONSULTA);
        assertThat(testCONSULTA.getDataDaConsulta()).isEqualTo(DEFAULT_DATA_DA_CONSULTA);
        assertThat(testCONSULTA.getHorarioDaConsulta()).isEqualTo(UPDATED_HORARIO_DA_CONSULTA);
    }

    @Test
    @Transactional
    void fullUpdateCONSULTAWithPatch() throws Exception {
        // Initialize the database
        cONSULTARepository.saveAndFlush(cONSULTA);

        int databaseSizeBeforeUpdate = cONSULTARepository.findAll().size();

        // Update the cONSULTA using partial update
        CONSULTA partialUpdatedCONSULTA = new CONSULTA();
        partialUpdatedCONSULTA.setId(cONSULTA.getId());

        partialUpdatedCONSULTA
            .codConsulta(UPDATED_COD_CONSULTA)
            .dataDaConsulta(UPDATED_DATA_DA_CONSULTA)
            .horarioDaConsulta(UPDATED_HORARIO_DA_CONSULTA);

        restCONSULTAMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCONSULTA.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCONSULTA))
            )
            .andExpect(status().isOk());

        // Validate the CONSULTA in the database
        List<CONSULTA> cONSULTAList = cONSULTARepository.findAll();
        assertThat(cONSULTAList).hasSize(databaseSizeBeforeUpdate);
        CONSULTA testCONSULTA = cONSULTAList.get(cONSULTAList.size() - 1);
        assertThat(testCONSULTA.getCodConsulta()).isEqualTo(UPDATED_COD_CONSULTA);
        assertThat(testCONSULTA.getDataDaConsulta()).isEqualTo(UPDATED_DATA_DA_CONSULTA);
        assertThat(testCONSULTA.getHorarioDaConsulta()).isEqualTo(UPDATED_HORARIO_DA_CONSULTA);
    }

    @Test
    @Transactional
    void patchNonExistingCONSULTA() throws Exception {
        int databaseSizeBeforeUpdate = cONSULTARepository.findAll().size();
        cONSULTA.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCONSULTAMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cONSULTA.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cONSULTA))
            )
            .andExpect(status().isBadRequest());

        // Validate the CONSULTA in the database
        List<CONSULTA> cONSULTAList = cONSULTARepository.findAll();
        assertThat(cONSULTAList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCONSULTA() throws Exception {
        int databaseSizeBeforeUpdate = cONSULTARepository.findAll().size();
        cONSULTA.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCONSULTAMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cONSULTA))
            )
            .andExpect(status().isBadRequest());

        // Validate the CONSULTA in the database
        List<CONSULTA> cONSULTAList = cONSULTARepository.findAll();
        assertThat(cONSULTAList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCONSULTA() throws Exception {
        int databaseSizeBeforeUpdate = cONSULTARepository.findAll().size();
        cONSULTA.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCONSULTAMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(cONSULTA)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CONSULTA in the database
        List<CONSULTA> cONSULTAList = cONSULTARepository.findAll();
        assertThat(cONSULTAList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCONSULTA() throws Exception {
        // Initialize the database
        cONSULTARepository.saveAndFlush(cONSULTA);

        int databaseSizeBeforeDelete = cONSULTARepository.findAll().size();

        // Delete the cONSULTA
        restCONSULTAMockMvc
            .perform(delete(ENTITY_API_URL_ID, cONSULTA.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CONSULTA> cONSULTAList = cONSULTARepository.findAll();
        assertThat(cONSULTAList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
