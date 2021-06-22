package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.SALA;
import com.mycompany.myapp.repository.SALARepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.SALA}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SALAResource {

    private final Logger log = LoggerFactory.getLogger(SALAResource.class);

    private static final String ENTITY_NAME = "sALA";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SALARepository sALARepository;

    public SALAResource(SALARepository sALARepository) {
        this.sALARepository = sALARepository;
    }

    /**
     * {@code POST  /salas} : Create a new sALA.
     *
     * @param sALA the sALA to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sALA, or with status {@code 400 (Bad Request)} if the sALA has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/salas")
    public ResponseEntity<SALA> createSALA(@Valid @RequestBody SALA sALA) throws URISyntaxException {
        log.debug("REST request to save SALA : {}", sALA);
        if (sALA.getId() != null) {
            throw new BadRequestAlertException("A new sALA cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SALA result = sALARepository.save(sALA);
        return ResponseEntity
            .created(new URI("/api/salas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /salas/:id} : Updates an existing sALA.
     *
     * @param id the id of the sALA to save.
     * @param sALA the sALA to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sALA,
     * or with status {@code 400 (Bad Request)} if the sALA is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sALA couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/salas/{id}")
    public ResponseEntity<SALA> updateSALA(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody SALA sALA)
        throws URISyntaxException {
        log.debug("REST request to update SALA : {}, {}", id, sALA);
        if (sALA.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sALA.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sALARepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SALA result = sALARepository.save(sALA);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sALA.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /salas/:id} : Partial updates given fields of an existing sALA, field will ignore if it is null
     *
     * @param id the id of the sALA to save.
     * @param sALA the sALA to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sALA,
     * or with status {@code 400 (Bad Request)} if the sALA is not valid,
     * or with status {@code 404 (Not Found)} if the sALA is not found,
     * or with status {@code 500 (Internal Server Error)} if the sALA couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/salas/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<SALA> partialUpdateSALA(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SALA sALA
    ) throws URISyntaxException {
        log.debug("REST request to partial update SALA partially : {}, {}", id, sALA);
        if (sALA.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sALA.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sALARepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SALA> result = sALARepository
            .findById(sALA.getId())
            .map(
                existingSALA -> {
                    if (sALA.getCodSala() != null) {
                        existingSALA.setCodSala(sALA.getCodSala());
                    }
                    if (sALA.getNome() != null) {
                        existingSALA.setNome(sALA.getNome());
                    }
                    if (sALA.getLocal() != null) {
                        existingSALA.setLocal(sALA.getLocal());
                    }
                    if (sALA.getStatus() != null) {
                        existingSALA.setStatus(sALA.getStatus());
                    }

                    return existingSALA;
                }
            )
            .map(sALARepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sALA.getId().toString())
        );
    }

    /**
     * {@code GET  /salas} : get all the sALAS.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sALAS in body.
     */
    @GetMapping("/salas")
    public List<SALA> getAllSALAS() {
        log.debug("REST request to get all SALAS");
        return sALARepository.findAll();
    }

    /**
     * {@code GET  /salas/:id} : get the "id" sALA.
     *
     * @param id the id of the sALA to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sALA, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/salas/{id}")
    public ResponseEntity<SALA> getSALA(@PathVariable Long id) {
        log.debug("REST request to get SALA : {}", id);
        Optional<SALA> sALA = sALARepository.findById(id);
        return ResponseUtil.wrapOrNotFound(sALA);
    }

    /**
     * {@code DELETE  /salas/:id} : delete the "id" sALA.
     *
     * @param id the id of the sALA to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/salas/{id}")
    public ResponseEntity<Void> deleteSALA(@PathVariable Long id) {
        log.debug("REST request to delete SALA : {}", id);
        sALARepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
