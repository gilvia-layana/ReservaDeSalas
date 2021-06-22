package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.PROFESSOR;
import com.mycompany.myapp.repository.PROFESSORRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.PROFESSOR}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PROFESSORResource {

    private final Logger log = LoggerFactory.getLogger(PROFESSORResource.class);

    private static final String ENTITY_NAME = "pROFESSOR";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PROFESSORRepository pROFESSORRepository;

    public PROFESSORResource(PROFESSORRepository pROFESSORRepository) {
        this.pROFESSORRepository = pROFESSORRepository;
    }

    /**
     * {@code POST  /professors} : Create a new pROFESSOR.
     *
     * @param pROFESSOR the pROFESSOR to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pROFESSOR, or with status {@code 400 (Bad Request)} if the pROFESSOR has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/professors")
    public ResponseEntity<PROFESSOR> createPROFESSOR(@Valid @RequestBody PROFESSOR pROFESSOR) throws URISyntaxException {
        log.debug("REST request to save PROFESSOR : {}", pROFESSOR);
        if (pROFESSOR.getId() != null) {
            throw new BadRequestAlertException("A new pROFESSOR cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PROFESSOR result = pROFESSORRepository.save(pROFESSOR);
        return ResponseEntity
            .created(new URI("/api/professors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /professors/:id} : Updates an existing pROFESSOR.
     *
     * @param id the id of the pROFESSOR to save.
     * @param pROFESSOR the pROFESSOR to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pROFESSOR,
     * or with status {@code 400 (Bad Request)} if the pROFESSOR is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pROFESSOR couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/professors/{id}")
    public ResponseEntity<PROFESSOR> updatePROFESSOR(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PROFESSOR pROFESSOR
    ) throws URISyntaxException {
        log.debug("REST request to update PROFESSOR : {}, {}", id, pROFESSOR);
        if (pROFESSOR.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pROFESSOR.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pROFESSORRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PROFESSOR result = pROFESSORRepository.save(pROFESSOR);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pROFESSOR.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /professors/:id} : Partial updates given fields of an existing pROFESSOR, field will ignore if it is null
     *
     * @param id the id of the pROFESSOR to save.
     * @param pROFESSOR the pROFESSOR to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pROFESSOR,
     * or with status {@code 400 (Bad Request)} if the pROFESSOR is not valid,
     * or with status {@code 404 (Not Found)} if the pROFESSOR is not found,
     * or with status {@code 500 (Internal Server Error)} if the pROFESSOR couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/professors/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<PROFESSOR> partialUpdatePROFESSOR(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PROFESSOR pROFESSOR
    ) throws URISyntaxException {
        log.debug("REST request to partial update PROFESSOR partially : {}, {}", id, pROFESSOR);
        if (pROFESSOR.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pROFESSOR.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pROFESSORRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PROFESSOR> result = pROFESSORRepository
            .findById(pROFESSOR.getId())
            .map(
                existingPROFESSOR -> {
                    if (pROFESSOR.getMatriculaProf() != null) {
                        existingPROFESSOR.setMatriculaProf(pROFESSOR.getMatriculaProf());
                    }
                    if (pROFESSOR.getNome() != null) {
                        existingPROFESSOR.setNome(pROFESSOR.getNome());
                    }
                    if (pROFESSOR.getDisciplina() != null) {
                        existingPROFESSOR.setDisciplina(pROFESSOR.getDisciplina());
                    }
                    if (pROFESSOR.getFaculdade() != null) {
                        existingPROFESSOR.setFaculdade(pROFESSOR.getFaculdade());
                    }

                    return existingPROFESSOR;
                }
            )
            .map(pROFESSORRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pROFESSOR.getId().toString())
        );
    }

    /**
     * {@code GET  /professors} : get all the pROFESSORS.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pROFESSORS in body.
     */
    @GetMapping("/professors")
    public List<PROFESSOR> getAllPROFESSORS() {
        log.debug("REST request to get all PROFESSORS");
        return pROFESSORRepository.findAll();
    }

    /**
     * {@code GET  /professors/:id} : get the "id" pROFESSOR.
     *
     * @param id the id of the pROFESSOR to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pROFESSOR, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/professors/{id}")
    public ResponseEntity<PROFESSOR> getPROFESSOR(@PathVariable Long id) {
        log.debug("REST request to get PROFESSOR : {}", id);
        Optional<PROFESSOR> pROFESSOR = pROFESSORRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(pROFESSOR);
    }

    /**
     * {@code DELETE  /professors/:id} : delete the "id" pROFESSOR.
     *
     * @param id the id of the pROFESSOR to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/professors/{id}")
    public ResponseEntity<Void> deletePROFESSOR(@PathVariable Long id) {
        log.debug("REST request to delete PROFESSOR : {}", id);
        pROFESSORRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
