package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.DadosPessoais;
import com.mycompany.myapp.repository.DadosPessoaisRepository;
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
 * Integration tests for the {@link DadosPessoaisResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DadosPessoaisResourceIT {

    private static final String DEFAULT_ENDERECO = "AAAAAAAAAA";
    private static final String UPDATED_ENDERECO = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/dados-pessoais";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DadosPessoaisRepository dadosPessoaisRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDadosPessoaisMockMvc;

    private DadosPessoais dadosPessoais;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DadosPessoais createEntity(EntityManager em) {
        DadosPessoais dadosPessoais = new DadosPessoais().endereco(DEFAULT_ENDERECO).telefone(DEFAULT_TELEFONE).email(DEFAULT_EMAIL);
        return dadosPessoais;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DadosPessoais createUpdatedEntity(EntityManager em) {
        DadosPessoais dadosPessoais = new DadosPessoais().endereco(UPDATED_ENDERECO).telefone(UPDATED_TELEFONE).email(UPDATED_EMAIL);
        return dadosPessoais;
    }

    @BeforeEach
    public void initTest() {
        dadosPessoais = createEntity(em);
    }

    @Test
    @Transactional
    void createDadosPessoais() throws Exception {
        int databaseSizeBeforeCreate = dadosPessoaisRepository.findAll().size();
        // Create the DadosPessoais
        restDadosPessoaisMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dadosPessoais)))
            .andExpect(status().isCreated());

        // Validate the DadosPessoais in the database
        List<DadosPessoais> dadosPessoaisList = dadosPessoaisRepository.findAll();
        assertThat(dadosPessoaisList).hasSize(databaseSizeBeforeCreate + 1);
        DadosPessoais testDadosPessoais = dadosPessoaisList.get(dadosPessoaisList.size() - 1);
        assertThat(testDadosPessoais.getEndereco()).isEqualTo(DEFAULT_ENDERECO);
        assertThat(testDadosPessoais.getTelefone()).isEqualTo(DEFAULT_TELEFONE);
        assertThat(testDadosPessoais.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    void createDadosPessoaisWithExistingId() throws Exception {
        // Create the DadosPessoais with an existing ID
        dadosPessoais.setId(1L);

        int databaseSizeBeforeCreate = dadosPessoaisRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDadosPessoaisMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dadosPessoais)))
            .andExpect(status().isBadRequest());

        // Validate the DadosPessoais in the database
        List<DadosPessoais> dadosPessoaisList = dadosPessoaisRepository.findAll();
        assertThat(dadosPessoaisList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDadosPessoais() throws Exception {
        // Initialize the database
        dadosPessoaisRepository.saveAndFlush(dadosPessoais);

        // Get all the dadosPessoaisList
        restDadosPessoaisMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dadosPessoais.getId().intValue())))
            .andExpect(jsonPath("$.[*].endereco").value(hasItem(DEFAULT_ENDERECO)))
            .andExpect(jsonPath("$.[*].telefone").value(hasItem(DEFAULT_TELEFONE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)));
    }

    @Test
    @Transactional
    void getDadosPessoais() throws Exception {
        // Initialize the database
        dadosPessoaisRepository.saveAndFlush(dadosPessoais);

        // Get the dadosPessoais
        restDadosPessoaisMockMvc
            .perform(get(ENTITY_API_URL_ID, dadosPessoais.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dadosPessoais.getId().intValue()))
            .andExpect(jsonPath("$.endereco").value(DEFAULT_ENDERECO))
            .andExpect(jsonPath("$.telefone").value(DEFAULT_TELEFONE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL));
    }

    @Test
    @Transactional
    void getNonExistingDadosPessoais() throws Exception {
        // Get the dadosPessoais
        restDadosPessoaisMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDadosPessoais() throws Exception {
        // Initialize the database
        dadosPessoaisRepository.saveAndFlush(dadosPessoais);

        int databaseSizeBeforeUpdate = dadosPessoaisRepository.findAll().size();

        // Update the dadosPessoais
        DadosPessoais updatedDadosPessoais = dadosPessoaisRepository.findById(dadosPessoais.getId()).get();
        // Disconnect from session so that the updates on updatedDadosPessoais are not directly saved in db
        em.detach(updatedDadosPessoais);
        updatedDadosPessoais.endereco(UPDATED_ENDERECO).telefone(UPDATED_TELEFONE).email(UPDATED_EMAIL);

        restDadosPessoaisMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDadosPessoais.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDadosPessoais))
            )
            .andExpect(status().isOk());

        // Validate the DadosPessoais in the database
        List<DadosPessoais> dadosPessoaisList = dadosPessoaisRepository.findAll();
        assertThat(dadosPessoaisList).hasSize(databaseSizeBeforeUpdate);
        DadosPessoais testDadosPessoais = dadosPessoaisList.get(dadosPessoaisList.size() - 1);
        assertThat(testDadosPessoais.getEndereco()).isEqualTo(UPDATED_ENDERECO);
        assertThat(testDadosPessoais.getTelefone()).isEqualTo(UPDATED_TELEFONE);
        assertThat(testDadosPessoais.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void putNonExistingDadosPessoais() throws Exception {
        int databaseSizeBeforeUpdate = dadosPessoaisRepository.findAll().size();
        dadosPessoais.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDadosPessoaisMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dadosPessoais.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dadosPessoais))
            )
            .andExpect(status().isBadRequest());

        // Validate the DadosPessoais in the database
        List<DadosPessoais> dadosPessoaisList = dadosPessoaisRepository.findAll();
        assertThat(dadosPessoaisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDadosPessoais() throws Exception {
        int databaseSizeBeforeUpdate = dadosPessoaisRepository.findAll().size();
        dadosPessoais.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDadosPessoaisMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dadosPessoais))
            )
            .andExpect(status().isBadRequest());

        // Validate the DadosPessoais in the database
        List<DadosPessoais> dadosPessoaisList = dadosPessoaisRepository.findAll();
        assertThat(dadosPessoaisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDadosPessoais() throws Exception {
        int databaseSizeBeforeUpdate = dadosPessoaisRepository.findAll().size();
        dadosPessoais.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDadosPessoaisMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dadosPessoais)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DadosPessoais in the database
        List<DadosPessoais> dadosPessoaisList = dadosPessoaisRepository.findAll();
        assertThat(dadosPessoaisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDadosPessoaisWithPatch() throws Exception {
        // Initialize the database
        dadosPessoaisRepository.saveAndFlush(dadosPessoais);

        int databaseSizeBeforeUpdate = dadosPessoaisRepository.findAll().size();

        // Update the dadosPessoais using partial update
        DadosPessoais partialUpdatedDadosPessoais = new DadosPessoais();
        partialUpdatedDadosPessoais.setId(dadosPessoais.getId());

        partialUpdatedDadosPessoais.telefone(UPDATED_TELEFONE).email(UPDATED_EMAIL);

        restDadosPessoaisMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDadosPessoais.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDadosPessoais))
            )
            .andExpect(status().isOk());

        // Validate the DadosPessoais in the database
        List<DadosPessoais> dadosPessoaisList = dadosPessoaisRepository.findAll();
        assertThat(dadosPessoaisList).hasSize(databaseSizeBeforeUpdate);
        DadosPessoais testDadosPessoais = dadosPessoaisList.get(dadosPessoaisList.size() - 1);
        assertThat(testDadosPessoais.getEndereco()).isEqualTo(DEFAULT_ENDERECO);
        assertThat(testDadosPessoais.getTelefone()).isEqualTo(UPDATED_TELEFONE);
        assertThat(testDadosPessoais.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void fullUpdateDadosPessoaisWithPatch() throws Exception {
        // Initialize the database
        dadosPessoaisRepository.saveAndFlush(dadosPessoais);

        int databaseSizeBeforeUpdate = dadosPessoaisRepository.findAll().size();

        // Update the dadosPessoais using partial update
        DadosPessoais partialUpdatedDadosPessoais = new DadosPessoais();
        partialUpdatedDadosPessoais.setId(dadosPessoais.getId());

        partialUpdatedDadosPessoais.endereco(UPDATED_ENDERECO).telefone(UPDATED_TELEFONE).email(UPDATED_EMAIL);

        restDadosPessoaisMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDadosPessoais.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDadosPessoais))
            )
            .andExpect(status().isOk());

        // Validate the DadosPessoais in the database
        List<DadosPessoais> dadosPessoaisList = dadosPessoaisRepository.findAll();
        assertThat(dadosPessoaisList).hasSize(databaseSizeBeforeUpdate);
        DadosPessoais testDadosPessoais = dadosPessoaisList.get(dadosPessoaisList.size() - 1);
        assertThat(testDadosPessoais.getEndereco()).isEqualTo(UPDATED_ENDERECO);
        assertThat(testDadosPessoais.getTelefone()).isEqualTo(UPDATED_TELEFONE);
        assertThat(testDadosPessoais.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void patchNonExistingDadosPessoais() throws Exception {
        int databaseSizeBeforeUpdate = dadosPessoaisRepository.findAll().size();
        dadosPessoais.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDadosPessoaisMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, dadosPessoais.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dadosPessoais))
            )
            .andExpect(status().isBadRequest());

        // Validate the DadosPessoais in the database
        List<DadosPessoais> dadosPessoaisList = dadosPessoaisRepository.findAll();
        assertThat(dadosPessoaisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDadosPessoais() throws Exception {
        int databaseSizeBeforeUpdate = dadosPessoaisRepository.findAll().size();
        dadosPessoais.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDadosPessoaisMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dadosPessoais))
            )
            .andExpect(status().isBadRequest());

        // Validate the DadosPessoais in the database
        List<DadosPessoais> dadosPessoaisList = dadosPessoaisRepository.findAll();
        assertThat(dadosPessoaisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDadosPessoais() throws Exception {
        int databaseSizeBeforeUpdate = dadosPessoaisRepository.findAll().size();
        dadosPessoais.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDadosPessoaisMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(dadosPessoais))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DadosPessoais in the database
        List<DadosPessoais> dadosPessoaisList = dadosPessoaisRepository.findAll();
        assertThat(dadosPessoaisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDadosPessoais() throws Exception {
        // Initialize the database
        dadosPessoaisRepository.saveAndFlush(dadosPessoais);

        int databaseSizeBeforeDelete = dadosPessoaisRepository.findAll().size();

        // Delete the dadosPessoais
        restDadosPessoaisMockMvc
            .perform(delete(ENTITY_API_URL_ID, dadosPessoais.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DadosPessoais> dadosPessoaisList = dadosPessoaisRepository.findAll();
        assertThat(dadosPessoaisList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
