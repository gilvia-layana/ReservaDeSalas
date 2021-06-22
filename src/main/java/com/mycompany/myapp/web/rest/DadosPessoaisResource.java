package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.DadosPessoais;
import com.mycompany.myapp.repository.DadosPessoaisRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.DadosPessoais}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DadosPessoaisResource {

    private final Logger log = LoggerFactory.getLogger(DadosPessoaisResource.class);

    private static final String ENTITY_NAME = "dadosPessoais";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DadosPessoaisRepository dadosPessoaisRepository;

    public DadosPessoaisResource(DadosPessoaisRepository dadosPessoaisRepository) {
        this.dadosPessoaisRepository = dadosPessoaisRepository;
    }

    /**
     * {@code POST  /dados-pessoais} : Create a new dadosPessoais.
     *
     * @param dadosPessoais the dadosPessoais to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dadosPessoais, or with status {@code 400 (Bad Request)} if the dadosPessoais has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dados-pessoais")
    public ResponseEntity<DadosPessoais> createDadosPessoais(@RequestBody DadosPessoais dadosPessoais) throws URISyntaxException {
        log.debug("REST request to save DadosPessoais : {}", dadosPessoais);
        if (dadosPessoais.getId() != null) {
            throw new BadRequestAlertException("A new dadosPessoais cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DadosPessoais result = dadosPessoaisRepository.save(dadosPessoais);
        return ResponseEntity
            .created(new URI("/api/dados-pessoais/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dados-pessoais/:id} : Updates an existing dadosPessoais.
     *
     * @param id the id of the dadosPessoais to save.
     * @param dadosPessoais the dadosPessoais to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dadosPessoais,
     * or with status {@code 400 (Bad Request)} if the dadosPessoais is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dadosPessoais couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dados-pessoais/{id}")
    public ResponseEntity<DadosPessoais> updateDadosPessoais(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DadosPessoais dadosPessoais
    ) throws URISyntaxException {
        log.debug("REST request to update DadosPessoais : {}, {}", id, dadosPessoais);
        if (dadosPessoais.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dadosPessoais.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dadosPessoaisRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DadosPessoais result = dadosPessoaisRepository.save(dadosPessoais);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dadosPessoais.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /dados-pessoais/:id} : Partial updates given fields of an existing dadosPessoais, field will ignore if it is null
     *
     * @param id the id of the dadosPessoais to save.
     * @param dadosPessoais the dadosPessoais to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dadosPessoais,
     * or with status {@code 400 (Bad Request)} if the dadosPessoais is not valid,
     * or with status {@code 404 (Not Found)} if the dadosPessoais is not found,
     * or with status {@code 500 (Internal Server Error)} if the dadosPessoais couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/dados-pessoais/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<DadosPessoais> partialUpdateDadosPessoais(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DadosPessoais dadosPessoais
    ) throws URISyntaxException {
        log.debug("REST request to partial update DadosPessoais partially : {}, {}", id, dadosPessoais);
        if (dadosPessoais.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dadosPessoais.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dadosPessoaisRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DadosPessoais> result = dadosPessoaisRepository
            .findById(dadosPessoais.getId())
            .map(
                existingDadosPessoais -> {
                    if (dadosPessoais.getEndereco() != null) {
                        existingDadosPessoais.setEndereco(dadosPessoais.getEndereco());
                    }
                    if (dadosPessoais.getTelefone() != null) {
                        existingDadosPessoais.setTelefone(dadosPessoais.getTelefone());
                    }
                    if (dadosPessoais.getEmail() != null) {
                        existingDadosPessoais.setEmail(dadosPessoais.getEmail());
                    }

                    return existingDadosPessoais;
                }
            )
            .map(dadosPessoaisRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dadosPessoais.getId().toString())
        );
    }

    /**
     * {@code GET  /dados-pessoais} : get all the dadosPessoais.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dadosPessoais in body.
     */
    @GetMapping("/dados-pessoais")
    public List<DadosPessoais> getAllDadosPessoais() {
        log.debug("REST request to get all DadosPessoais");
        return dadosPessoaisRepository.findAll();
    }

    /**
     * {@code GET  /dados-pessoais/:id} : get the "id" dadosPessoais.
     *
     * @param id the id of the dadosPessoais to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dadosPessoais, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dados-pessoais/{id}")
    public ResponseEntity<DadosPessoais> getDadosPessoais(@PathVariable Long id) {
        log.debug("REST request to get DadosPessoais : {}", id);
        Optional<DadosPessoais> dadosPessoais = dadosPessoaisRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(dadosPessoais);
    }

    /**
     * {@code DELETE  /dados-pessoais/:id} : delete the "id" dadosPessoais.
     *
     * @param id the id of the dadosPessoais to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dados-pessoais/{id}")
    public ResponseEntity<Void> deleteDadosPessoais(@PathVariable Long id) {
        log.debug("REST request to delete DadosPessoais : {}", id);
        dadosPessoaisRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
