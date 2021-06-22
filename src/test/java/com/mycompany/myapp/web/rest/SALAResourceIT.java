package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.SALA;
import com.mycompany.myapp.domain.enumeration.StatusSala;
import com.mycompany.myapp.repository.SALARepository;
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
 * Integration tests for the {@link SALAResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SALAResourceIT {

    private static final Integer DEFAULT_COD_SALA = 1;
    private static final Integer UPDATED_COD_SALA = 2;

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_LOCAL = "AAAAAAAAAA";
    private static final String UPDATED_LOCAL = "BBBBBBBBBB";

    private static final StatusSala DEFAULT_STATUS = StatusSala.Livre;
    private static final StatusSala UPDATED_STATUS = StatusSala.Ocupada;

    private static final String ENTITY_API_URL = "/api/salas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SALARepository sALARepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSALAMockMvc;

    private SALA sALA;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SALA createEntity(EntityManager em) {
        SALA sALA = new SALA().codSala(DEFAULT_COD_SALA).nome(DEFAULT_NOME).local(DEFAULT_LOCAL).status(DEFAULT_STATUS);
        return sALA;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SALA createUpdatedEntity(EntityManager em) {
        SALA sALA = new SALA().codSala(UPDATED_COD_SALA).nome(UPDATED_NOME).local(UPDATED_LOCAL).status(UPDATED_STATUS);
        return sALA;
    }

    @BeforeEach
    public void initTest() {
        sALA = createEntity(em);
    }

    @Test
    @Transactional
    void createSALA() throws Exception {
        int databaseSizeBeforeCreate = sALARepository.findAll().size();
        // Create the SALA
        restSALAMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sALA)))
            .andExpect(status().isCreated());

        // Validate the SALA in the database
        List<SALA> sALAList = sALARepository.findAll();
        assertThat(sALAList).hasSize(databaseSizeBeforeCreate + 1);
        SALA testSALA = sALAList.get(sALAList.size() - 1);
        assertThat(testSALA.getCodSala()).isEqualTo(DEFAULT_COD_SALA);
        assertThat(testSALA.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testSALA.getLocal()).isEqualTo(DEFAULT_LOCAL);
        assertThat(testSALA.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void createSALAWithExistingId() throws Exception {
        // Create the SALA with an existing ID
        sALA.setId(1L);

        int databaseSizeBeforeCreate = sALARepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSALAMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sALA)))
            .andExpect(status().isBadRequest());

        // Validate the SALA in the database
        List<SALA> sALAList = sALARepository.findAll();
        assertThat(sALAList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodSalaIsRequired() throws Exception {
        int databaseSizeBeforeTest = sALARepository.findAll().size();
        // set the field null
        sALA.setCodSala(null);

        // Create the SALA, which fails.

        restSALAMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sALA)))
            .andExpect(status().isBadRequest());

        List<SALA> sALAList = sALARepository.findAll();
        assertThat(sALAList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSALAS() throws Exception {
        // Initialize the database
        sALARepository.saveAndFlush(sALA);

        // Get all the sALAList
        restSALAMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sALA.getId().intValue())))
            .andExpect(jsonPath("$.[*].codSala").value(hasItem(DEFAULT_COD_SALA)))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].local").value(hasItem(DEFAULT_LOCAL)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    void getSALA() throws Exception {
        // Initialize the database
        sALARepository.saveAndFlush(sALA);

        // Get the sALA
        restSALAMockMvc
            .perform(get(ENTITY_API_URL_ID, sALA.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sALA.getId().intValue()))
            .andExpect(jsonPath("$.codSala").value(DEFAULT_COD_SALA))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.local").value(DEFAULT_LOCAL))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    void getNonExistingSALA() throws Exception {
        // Get the sALA
        restSALAMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSALA() throws Exception {
        // Initialize the database
        sALARepository.saveAndFlush(sALA);

        int databaseSizeBeforeUpdate = sALARepository.findAll().size();

        // Update the sALA
        SALA updatedSALA = sALARepository.findById(sALA.getId()).get();
        // Disconnect from session so that the updates on updatedSALA are not directly saved in db
        em.detach(updatedSALA);
        updatedSALA.codSala(UPDATED_COD_SALA).nome(UPDATED_NOME).local(UPDATED_LOCAL).status(UPDATED_STATUS);

        restSALAMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSALA.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSALA))
            )
            .andExpect(status().isOk());

        // Validate the SALA in the database
        List<SALA> sALAList = sALARepository.findAll();
        assertThat(sALAList).hasSize(databaseSizeBeforeUpdate);
        SALA testSALA = sALAList.get(sALAList.size() - 1);
        assertThat(testSALA.getCodSala()).isEqualTo(UPDATED_COD_SALA);
        assertThat(testSALA.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testSALA.getLocal()).isEqualTo(UPDATED_LOCAL);
        assertThat(testSALA.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingSALA() throws Exception {
        int databaseSizeBeforeUpdate = sALARepository.findAll().size();
        sALA.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSALAMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sALA.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sALA))
            )
            .andExpect(status().isBadRequest());

        // Validate the SALA in the database
        List<SALA> sALAList = sALARepository.findAll();
        assertThat(sALAList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSALA() throws Exception {
        int databaseSizeBeforeUpdate = sALARepository.findAll().size();
        sALA.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSALAMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sALA))
            )
            .andExpect(status().isBadRequest());

        // Validate the SALA in the database
        List<SALA> sALAList = sALARepository.findAll();
        assertThat(sALAList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSALA() throws Exception {
        int databaseSizeBeforeUpdate = sALARepository.findAll().size();
        sALA.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSALAMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sALA)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SALA in the database
        List<SALA> sALAList = sALARepository.findAll();
        assertThat(sALAList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSALAWithPatch() throws Exception {
        // Initialize the database
        sALARepository.saveAndFlush(sALA);

        int databaseSizeBeforeUpdate = sALARepository.findAll().size();

        // Update the sALA using partial update
        SALA partialUpdatedSALA = new SALA();
        partialUpdatedSALA.setId(sALA.getId());

        partialUpdatedSALA.codSala(UPDATED_COD_SALA).status(UPDATED_STATUS);

        restSALAMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSALA.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSALA))
            )
            .andExpect(status().isOk());

        // Validate the SALA in the database
        List<SALA> sALAList = sALARepository.findAll();
        assertThat(sALAList).hasSize(databaseSizeBeforeUpdate);
        SALA testSALA = sALAList.get(sALAList.size() - 1);
        assertThat(testSALA.getCodSala()).isEqualTo(UPDATED_COD_SALA);
        assertThat(testSALA.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testSALA.getLocal()).isEqualTo(DEFAULT_LOCAL);
        assertThat(testSALA.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateSALAWithPatch() throws Exception {
        // Initialize the database
        sALARepository.saveAndFlush(sALA);

        int databaseSizeBeforeUpdate = sALARepository.findAll().size();

        // Update the sALA using partial update
        SALA partialUpdatedSALA = new SALA();
        partialUpdatedSALA.setId(sALA.getId());

        partialUpdatedSALA.codSala(UPDATED_COD_SALA).nome(UPDATED_NOME).local(UPDATED_LOCAL).status(UPDATED_STATUS);

        restSALAMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSALA.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSALA))
            )
            .andExpect(status().isOk());

        // Validate the SALA in the database
        List<SALA> sALAList = sALARepository.findAll();
        assertThat(sALAList).hasSize(databaseSizeBeforeUpdate);
        SALA testSALA = sALAList.get(sALAList.size() - 1);
        assertThat(testSALA.getCodSala()).isEqualTo(UPDATED_COD_SALA);
        assertThat(testSALA.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testSALA.getLocal()).isEqualTo(UPDATED_LOCAL);
        assertThat(testSALA.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingSALA() throws Exception {
        int databaseSizeBeforeUpdate = sALARepository.findAll().size();
        sALA.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSALAMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, sALA.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sALA))
            )
            .andExpect(status().isBadRequest());

        // Validate the SALA in the database
        List<SALA> sALAList = sALARepository.findAll();
        assertThat(sALAList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSALA() throws Exception {
        int databaseSizeBeforeUpdate = sALARepository.findAll().size();
        sALA.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSALAMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sALA))
            )
            .andExpect(status().isBadRequest());

        // Validate the SALA in the database
        List<SALA> sALAList = sALARepository.findAll();
        assertThat(sALAList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSALA() throws Exception {
        int databaseSizeBeforeUpdate = sALARepository.findAll().size();
        sALA.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSALAMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(sALA)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SALA in the database
        List<SALA> sALAList = sALARepository.findAll();
        assertThat(sALAList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSALA() throws Exception {
        // Initialize the database
        sALARepository.saveAndFlush(sALA);

        int databaseSizeBeforeDelete = sALARepository.findAll().size();

        // Delete the sALA
        restSALAMockMvc
            .perform(delete(ENTITY_API_URL_ID, sALA.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SALA> sALAList = sALARepository.findAll();
        assertThat(sALAList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
