package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.PROFESSOR;
import com.mycompany.myapp.repository.PROFESSORRepository;
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
 * Integration tests for the {@link PROFESSORResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PROFESSORResourceIT {

    private static final Integer DEFAULT_MATRICULA_PROF = 1;
    private static final Integer UPDATED_MATRICULA_PROF = 2;

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DISCIPLINA = "AAAAAAAAAA";
    private static final String UPDATED_DISCIPLINA = "BBBBBBBBBB";

    private static final String DEFAULT_FACULDADE = "AAAAAAAAAA";
    private static final String UPDATED_FACULDADE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/professors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PROFESSORRepository pROFESSORRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPROFESSORMockMvc;

    private PROFESSOR pROFESSOR;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PROFESSOR createEntity(EntityManager em) {
        PROFESSOR pROFESSOR = new PROFESSOR()
            .matriculaProf(DEFAULT_MATRICULA_PROF)
            .nome(DEFAULT_NOME)
            .disciplina(DEFAULT_DISCIPLINA)
            .faculdade(DEFAULT_FACULDADE);
        return pROFESSOR;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PROFESSOR createUpdatedEntity(EntityManager em) {
        PROFESSOR pROFESSOR = new PROFESSOR()
            .matriculaProf(UPDATED_MATRICULA_PROF)
            .nome(UPDATED_NOME)
            .disciplina(UPDATED_DISCIPLINA)
            .faculdade(UPDATED_FACULDADE);
        return pROFESSOR;
    }

    @BeforeEach
    public void initTest() {
        pROFESSOR = createEntity(em);
    }

    @Test
    @Transactional
    void createPROFESSOR() throws Exception {
        int databaseSizeBeforeCreate = pROFESSORRepository.findAll().size();
        // Create the PROFESSOR
        restPROFESSORMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pROFESSOR)))
            .andExpect(status().isCreated());

        // Validate the PROFESSOR in the database
        List<PROFESSOR> pROFESSORList = pROFESSORRepository.findAll();
        assertThat(pROFESSORList).hasSize(databaseSizeBeforeCreate + 1);
        PROFESSOR testPROFESSOR = pROFESSORList.get(pROFESSORList.size() - 1);
        assertThat(testPROFESSOR.getMatriculaProf()).isEqualTo(DEFAULT_MATRICULA_PROF);
        assertThat(testPROFESSOR.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testPROFESSOR.getDisciplina()).isEqualTo(DEFAULT_DISCIPLINA);
        assertThat(testPROFESSOR.getFaculdade()).isEqualTo(DEFAULT_FACULDADE);
    }

    @Test
    @Transactional
    void createPROFESSORWithExistingId() throws Exception {
        // Create the PROFESSOR with an existing ID
        pROFESSOR.setId(1L);

        int databaseSizeBeforeCreate = pROFESSORRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPROFESSORMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pROFESSOR)))
            .andExpect(status().isBadRequest());

        // Validate the PROFESSOR in the database
        List<PROFESSOR> pROFESSORList = pROFESSORRepository.findAll();
        assertThat(pROFESSORList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkMatriculaProfIsRequired() throws Exception {
        int databaseSizeBeforeTest = pROFESSORRepository.findAll().size();
        // set the field null
        pROFESSOR.setMatriculaProf(null);

        // Create the PROFESSOR, which fails.

        restPROFESSORMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pROFESSOR)))
            .andExpect(status().isBadRequest());

        List<PROFESSOR> pROFESSORList = pROFESSORRepository.findAll();
        assertThat(pROFESSORList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPROFESSORS() throws Exception {
        // Initialize the database
        pROFESSORRepository.saveAndFlush(pROFESSOR);

        // Get all the pROFESSORList
        restPROFESSORMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pROFESSOR.getId().intValue())))
            .andExpect(jsonPath("$.[*].matriculaProf").value(hasItem(DEFAULT_MATRICULA_PROF)))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].disciplina").value(hasItem(DEFAULT_DISCIPLINA)))
            .andExpect(jsonPath("$.[*].faculdade").value(hasItem(DEFAULT_FACULDADE)));
    }

    @Test
    @Transactional
    void getPROFESSOR() throws Exception {
        // Initialize the database
        pROFESSORRepository.saveAndFlush(pROFESSOR);

        // Get the pROFESSOR
        restPROFESSORMockMvc
            .perform(get(ENTITY_API_URL_ID, pROFESSOR.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pROFESSOR.getId().intValue()))
            .andExpect(jsonPath("$.matriculaProf").value(DEFAULT_MATRICULA_PROF))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.disciplina").value(DEFAULT_DISCIPLINA))
            .andExpect(jsonPath("$.faculdade").value(DEFAULT_FACULDADE));
    }

    @Test
    @Transactional
    void getNonExistingPROFESSOR() throws Exception {
        // Get the pROFESSOR
        restPROFESSORMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPROFESSOR() throws Exception {
        // Initialize the database
        pROFESSORRepository.saveAndFlush(pROFESSOR);

        int databaseSizeBeforeUpdate = pROFESSORRepository.findAll().size();

        // Update the pROFESSOR
        PROFESSOR updatedPROFESSOR = pROFESSORRepository.findById(pROFESSOR.getId()).get();
        // Disconnect from session so that the updates on updatedPROFESSOR are not directly saved in db
        em.detach(updatedPROFESSOR);
        updatedPROFESSOR
            .matriculaProf(UPDATED_MATRICULA_PROF)
            .nome(UPDATED_NOME)
            .disciplina(UPDATED_DISCIPLINA)
            .faculdade(UPDATED_FACULDADE);

        restPROFESSORMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPROFESSOR.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPROFESSOR))
            )
            .andExpect(status().isOk());

        // Validate the PROFESSOR in the database
        List<PROFESSOR> pROFESSORList = pROFESSORRepository.findAll();
        assertThat(pROFESSORList).hasSize(databaseSizeBeforeUpdate);
        PROFESSOR testPROFESSOR = pROFESSORList.get(pROFESSORList.size() - 1);
        assertThat(testPROFESSOR.getMatriculaProf()).isEqualTo(UPDATED_MATRICULA_PROF);
        assertThat(testPROFESSOR.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testPROFESSOR.getDisciplina()).isEqualTo(UPDATED_DISCIPLINA);
        assertThat(testPROFESSOR.getFaculdade()).isEqualTo(UPDATED_FACULDADE);
    }

    @Test
    @Transactional
    void putNonExistingPROFESSOR() throws Exception {
        int databaseSizeBeforeUpdate = pROFESSORRepository.findAll().size();
        pROFESSOR.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPROFESSORMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pROFESSOR.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pROFESSOR))
            )
            .andExpect(status().isBadRequest());

        // Validate the PROFESSOR in the database
        List<PROFESSOR> pROFESSORList = pROFESSORRepository.findAll();
        assertThat(pROFESSORList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPROFESSOR() throws Exception {
        int databaseSizeBeforeUpdate = pROFESSORRepository.findAll().size();
        pROFESSOR.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPROFESSORMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pROFESSOR))
            )
            .andExpect(status().isBadRequest());

        // Validate the PROFESSOR in the database
        List<PROFESSOR> pROFESSORList = pROFESSORRepository.findAll();
        assertThat(pROFESSORList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPROFESSOR() throws Exception {
        int databaseSizeBeforeUpdate = pROFESSORRepository.findAll().size();
        pROFESSOR.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPROFESSORMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pROFESSOR)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PROFESSOR in the database
        List<PROFESSOR> pROFESSORList = pROFESSORRepository.findAll();
        assertThat(pROFESSORList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePROFESSORWithPatch() throws Exception {
        // Initialize the database
        pROFESSORRepository.saveAndFlush(pROFESSOR);

        int databaseSizeBeforeUpdate = pROFESSORRepository.findAll().size();

        // Update the pROFESSOR using partial update
        PROFESSOR partialUpdatedPROFESSOR = new PROFESSOR();
        partialUpdatedPROFESSOR.setId(pROFESSOR.getId());

        partialUpdatedPROFESSOR.matriculaProf(UPDATED_MATRICULA_PROF).disciplina(UPDATED_DISCIPLINA);

        restPROFESSORMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPROFESSOR.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPROFESSOR))
            )
            .andExpect(status().isOk());

        // Validate the PROFESSOR in the database
        List<PROFESSOR> pROFESSORList = pROFESSORRepository.findAll();
        assertThat(pROFESSORList).hasSize(databaseSizeBeforeUpdate);
        PROFESSOR testPROFESSOR = pROFESSORList.get(pROFESSORList.size() - 1);
        assertThat(testPROFESSOR.getMatriculaProf()).isEqualTo(UPDATED_MATRICULA_PROF);
        assertThat(testPROFESSOR.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testPROFESSOR.getDisciplina()).isEqualTo(UPDATED_DISCIPLINA);
        assertThat(testPROFESSOR.getFaculdade()).isEqualTo(DEFAULT_FACULDADE);
    }

    @Test
    @Transactional
    void fullUpdatePROFESSORWithPatch() throws Exception {
        // Initialize the database
        pROFESSORRepository.saveAndFlush(pROFESSOR);

        int databaseSizeBeforeUpdate = pROFESSORRepository.findAll().size();

        // Update the pROFESSOR using partial update
        PROFESSOR partialUpdatedPROFESSOR = new PROFESSOR();
        partialUpdatedPROFESSOR.setId(pROFESSOR.getId());

        partialUpdatedPROFESSOR
            .matriculaProf(UPDATED_MATRICULA_PROF)
            .nome(UPDATED_NOME)
            .disciplina(UPDATED_DISCIPLINA)
            .faculdade(UPDATED_FACULDADE);

        restPROFESSORMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPROFESSOR.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPROFESSOR))
            )
            .andExpect(status().isOk());

        // Validate the PROFESSOR in the database
        List<PROFESSOR> pROFESSORList = pROFESSORRepository.findAll();
        assertThat(pROFESSORList).hasSize(databaseSizeBeforeUpdate);
        PROFESSOR testPROFESSOR = pROFESSORList.get(pROFESSORList.size() - 1);
        assertThat(testPROFESSOR.getMatriculaProf()).isEqualTo(UPDATED_MATRICULA_PROF);
        assertThat(testPROFESSOR.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testPROFESSOR.getDisciplina()).isEqualTo(UPDATED_DISCIPLINA);
        assertThat(testPROFESSOR.getFaculdade()).isEqualTo(UPDATED_FACULDADE);
    }

    @Test
    @Transactional
    void patchNonExistingPROFESSOR() throws Exception {
        int databaseSizeBeforeUpdate = pROFESSORRepository.findAll().size();
        pROFESSOR.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPROFESSORMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pROFESSOR.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pROFESSOR))
            )
            .andExpect(status().isBadRequest());

        // Validate the PROFESSOR in the database
        List<PROFESSOR> pROFESSORList = pROFESSORRepository.findAll();
        assertThat(pROFESSORList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPROFESSOR() throws Exception {
        int databaseSizeBeforeUpdate = pROFESSORRepository.findAll().size();
        pROFESSOR.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPROFESSORMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pROFESSOR))
            )
            .andExpect(status().isBadRequest());

        // Validate the PROFESSOR in the database
        List<PROFESSOR> pROFESSORList = pROFESSORRepository.findAll();
        assertThat(pROFESSORList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPROFESSOR() throws Exception {
        int databaseSizeBeforeUpdate = pROFESSORRepository.findAll().size();
        pROFESSOR.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPROFESSORMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(pROFESSOR))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PROFESSOR in the database
        List<PROFESSOR> pROFESSORList = pROFESSORRepository.findAll();
        assertThat(pROFESSORList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePROFESSOR() throws Exception {
        // Initialize the database
        pROFESSORRepository.saveAndFlush(pROFESSOR);

        int databaseSizeBeforeDelete = pROFESSORRepository.findAll().size();

        // Delete the pROFESSOR
        restPROFESSORMockMvc
            .perform(delete(ENTITY_API_URL_ID, pROFESSOR.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PROFESSOR> pROFESSORList = pROFESSORRepository.findAll();
        assertThat(pROFESSORList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
