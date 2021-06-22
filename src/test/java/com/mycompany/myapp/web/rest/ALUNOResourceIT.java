package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.ALUNO;
import com.mycompany.myapp.domain.enumeration.Area;
import com.mycompany.myapp.repository.ALUNORepository;
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
 * Integration tests for the {@link ALUNOResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ALUNOResourceIT {

    private static final Integer DEFAULT_MATRICULA_ALUNO = 1;
    private static final Integer UPDATED_MATRICULA_ALUNO = 2;

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final Area DEFAULT_AREA_DE_CONHECIMENTO = Area.HUMANAS;
    private static final Area UPDATED_AREA_DE_CONHECIMENTO = Area.EXATAS;

    private static final String DEFAULT_CURSO = "AAAAAAAAAA";
    private static final String UPDATED_CURSO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/alunos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ALUNORepository aLUNORepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restALUNOMockMvc;

    private ALUNO aLUNO;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ALUNO createEntity(EntityManager em) {
        ALUNO aLUNO = new ALUNO()
            .matriculaAluno(DEFAULT_MATRICULA_ALUNO)
            .nome(DEFAULT_NOME)
            .areaDeConhecimento(DEFAULT_AREA_DE_CONHECIMENTO)
            .curso(DEFAULT_CURSO);
        return aLUNO;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ALUNO createUpdatedEntity(EntityManager em) {
        ALUNO aLUNO = new ALUNO()
            .matriculaAluno(UPDATED_MATRICULA_ALUNO)
            .nome(UPDATED_NOME)
            .areaDeConhecimento(UPDATED_AREA_DE_CONHECIMENTO)
            .curso(UPDATED_CURSO);
        return aLUNO;
    }

    @BeforeEach
    public void initTest() {
        aLUNO = createEntity(em);
    }

    @Test
    @Transactional
    void createALUNO() throws Exception {
        int databaseSizeBeforeCreate = aLUNORepository.findAll().size();
        // Create the ALUNO
        restALUNOMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(aLUNO)))
            .andExpect(status().isCreated());

        // Validate the ALUNO in the database
        List<ALUNO> aLUNOList = aLUNORepository.findAll();
        assertThat(aLUNOList).hasSize(databaseSizeBeforeCreate + 1);
        ALUNO testALUNO = aLUNOList.get(aLUNOList.size() - 1);
        assertThat(testALUNO.getMatriculaAluno()).isEqualTo(DEFAULT_MATRICULA_ALUNO);
        assertThat(testALUNO.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testALUNO.getAreaDeConhecimento()).isEqualTo(DEFAULT_AREA_DE_CONHECIMENTO);
        assertThat(testALUNO.getCurso()).isEqualTo(DEFAULT_CURSO);
    }

    @Test
    @Transactional
    void createALUNOWithExistingId() throws Exception {
        // Create the ALUNO with an existing ID
        aLUNO.setId(1L);

        int databaseSizeBeforeCreate = aLUNORepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restALUNOMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(aLUNO)))
            .andExpect(status().isBadRequest());

        // Validate the ALUNO in the database
        List<ALUNO> aLUNOList = aLUNORepository.findAll();
        assertThat(aLUNOList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkMatriculaAlunoIsRequired() throws Exception {
        int databaseSizeBeforeTest = aLUNORepository.findAll().size();
        // set the field null
        aLUNO.setMatriculaAluno(null);

        // Create the ALUNO, which fails.

        restALUNOMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(aLUNO)))
            .andExpect(status().isBadRequest());

        List<ALUNO> aLUNOList = aLUNORepository.findAll();
        assertThat(aLUNOList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllALUNOS() throws Exception {
        // Initialize the database
        aLUNORepository.saveAndFlush(aLUNO);

        // Get all the aLUNOList
        restALUNOMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aLUNO.getId().intValue())))
            .andExpect(jsonPath("$.[*].matriculaAluno").value(hasItem(DEFAULT_MATRICULA_ALUNO)))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].areaDeConhecimento").value(hasItem(DEFAULT_AREA_DE_CONHECIMENTO.toString())))
            .andExpect(jsonPath("$.[*].curso").value(hasItem(DEFAULT_CURSO)));
    }

    @Test
    @Transactional
    void getALUNO() throws Exception {
        // Initialize the database
        aLUNORepository.saveAndFlush(aLUNO);

        // Get the aLUNO
        restALUNOMockMvc
            .perform(get(ENTITY_API_URL_ID, aLUNO.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(aLUNO.getId().intValue()))
            .andExpect(jsonPath("$.matriculaAluno").value(DEFAULT_MATRICULA_ALUNO))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.areaDeConhecimento").value(DEFAULT_AREA_DE_CONHECIMENTO.toString()))
            .andExpect(jsonPath("$.curso").value(DEFAULT_CURSO));
    }

    @Test
    @Transactional
    void getNonExistingALUNO() throws Exception {
        // Get the aLUNO
        restALUNOMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewALUNO() throws Exception {
        // Initialize the database
        aLUNORepository.saveAndFlush(aLUNO);

        int databaseSizeBeforeUpdate = aLUNORepository.findAll().size();

        // Update the aLUNO
        ALUNO updatedALUNO = aLUNORepository.findById(aLUNO.getId()).get();
        // Disconnect from session so that the updates on updatedALUNO are not directly saved in db
        em.detach(updatedALUNO);
        updatedALUNO
            .matriculaAluno(UPDATED_MATRICULA_ALUNO)
            .nome(UPDATED_NOME)
            .areaDeConhecimento(UPDATED_AREA_DE_CONHECIMENTO)
            .curso(UPDATED_CURSO);

        restALUNOMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedALUNO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedALUNO))
            )
            .andExpect(status().isOk());

        // Validate the ALUNO in the database
        List<ALUNO> aLUNOList = aLUNORepository.findAll();
        assertThat(aLUNOList).hasSize(databaseSizeBeforeUpdate);
        ALUNO testALUNO = aLUNOList.get(aLUNOList.size() - 1);
        assertThat(testALUNO.getMatriculaAluno()).isEqualTo(UPDATED_MATRICULA_ALUNO);
        assertThat(testALUNO.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testALUNO.getAreaDeConhecimento()).isEqualTo(UPDATED_AREA_DE_CONHECIMENTO);
        assertThat(testALUNO.getCurso()).isEqualTo(UPDATED_CURSO);
    }

    @Test
    @Transactional
    void putNonExistingALUNO() throws Exception {
        int databaseSizeBeforeUpdate = aLUNORepository.findAll().size();
        aLUNO.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restALUNOMockMvc
            .perform(
                put(ENTITY_API_URL_ID, aLUNO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(aLUNO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ALUNO in the database
        List<ALUNO> aLUNOList = aLUNORepository.findAll();
        assertThat(aLUNOList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchALUNO() throws Exception {
        int databaseSizeBeforeUpdate = aLUNORepository.findAll().size();
        aLUNO.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restALUNOMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(aLUNO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ALUNO in the database
        List<ALUNO> aLUNOList = aLUNORepository.findAll();
        assertThat(aLUNOList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamALUNO() throws Exception {
        int databaseSizeBeforeUpdate = aLUNORepository.findAll().size();
        aLUNO.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restALUNOMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(aLUNO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ALUNO in the database
        List<ALUNO> aLUNOList = aLUNORepository.findAll();
        assertThat(aLUNOList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateALUNOWithPatch() throws Exception {
        // Initialize the database
        aLUNORepository.saveAndFlush(aLUNO);

        int databaseSizeBeforeUpdate = aLUNORepository.findAll().size();

        // Update the aLUNO using partial update
        ALUNO partialUpdatedALUNO = new ALUNO();
        partialUpdatedALUNO.setId(aLUNO.getId());

        partialUpdatedALUNO.matriculaAluno(UPDATED_MATRICULA_ALUNO).nome(UPDATED_NOME).curso(UPDATED_CURSO);

        restALUNOMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedALUNO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedALUNO))
            )
            .andExpect(status().isOk());

        // Validate the ALUNO in the database
        List<ALUNO> aLUNOList = aLUNORepository.findAll();
        assertThat(aLUNOList).hasSize(databaseSizeBeforeUpdate);
        ALUNO testALUNO = aLUNOList.get(aLUNOList.size() - 1);
        assertThat(testALUNO.getMatriculaAluno()).isEqualTo(UPDATED_MATRICULA_ALUNO);
        assertThat(testALUNO.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testALUNO.getAreaDeConhecimento()).isEqualTo(DEFAULT_AREA_DE_CONHECIMENTO);
        assertThat(testALUNO.getCurso()).isEqualTo(UPDATED_CURSO);
    }

    @Test
    @Transactional
    void fullUpdateALUNOWithPatch() throws Exception {
        // Initialize the database
        aLUNORepository.saveAndFlush(aLUNO);

        int databaseSizeBeforeUpdate = aLUNORepository.findAll().size();

        // Update the aLUNO using partial update
        ALUNO partialUpdatedALUNO = new ALUNO();
        partialUpdatedALUNO.setId(aLUNO.getId());

        partialUpdatedALUNO
            .matriculaAluno(UPDATED_MATRICULA_ALUNO)
            .nome(UPDATED_NOME)
            .areaDeConhecimento(UPDATED_AREA_DE_CONHECIMENTO)
            .curso(UPDATED_CURSO);

        restALUNOMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedALUNO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedALUNO))
            )
            .andExpect(status().isOk());

        // Validate the ALUNO in the database
        List<ALUNO> aLUNOList = aLUNORepository.findAll();
        assertThat(aLUNOList).hasSize(databaseSizeBeforeUpdate);
        ALUNO testALUNO = aLUNOList.get(aLUNOList.size() - 1);
        assertThat(testALUNO.getMatriculaAluno()).isEqualTo(UPDATED_MATRICULA_ALUNO);
        assertThat(testALUNO.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testALUNO.getAreaDeConhecimento()).isEqualTo(UPDATED_AREA_DE_CONHECIMENTO);
        assertThat(testALUNO.getCurso()).isEqualTo(UPDATED_CURSO);
    }

    @Test
    @Transactional
    void patchNonExistingALUNO() throws Exception {
        int databaseSizeBeforeUpdate = aLUNORepository.findAll().size();
        aLUNO.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restALUNOMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, aLUNO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(aLUNO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ALUNO in the database
        List<ALUNO> aLUNOList = aLUNORepository.findAll();
        assertThat(aLUNOList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchALUNO() throws Exception {
        int databaseSizeBeforeUpdate = aLUNORepository.findAll().size();
        aLUNO.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restALUNOMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(aLUNO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ALUNO in the database
        List<ALUNO> aLUNOList = aLUNORepository.findAll();
        assertThat(aLUNOList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamALUNO() throws Exception {
        int databaseSizeBeforeUpdate = aLUNORepository.findAll().size();
        aLUNO.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restALUNOMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(aLUNO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ALUNO in the database
        List<ALUNO> aLUNOList = aLUNORepository.findAll();
        assertThat(aLUNOList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteALUNO() throws Exception {
        // Initialize the database
        aLUNORepository.saveAndFlush(aLUNO);

        int databaseSizeBeforeDelete = aLUNORepository.findAll().size();

        // Delete the aLUNO
        restALUNOMockMvc
            .perform(delete(ENTITY_API_URL_ID, aLUNO.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ALUNO> aLUNOList = aLUNORepository.findAll();
        assertThat(aLUNOList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
