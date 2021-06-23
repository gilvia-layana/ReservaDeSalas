package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.CONSULTA;
import com.mycompany.myapp.repository.CONSULTARepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.CONSULTA}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CONSULTAResource {

    private final Logger log = LoggerFactory.getLogger(CONSULTAResource.class);

    private static final String ENTITY_NAME = "cONSULTA";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CONSULTARepository cONSULTARepository;

    public CONSULTAResource(CONSULTARepository cONSULTARepository) {
        this.cONSULTARepository = cONSULTARepository;
    }

    /**
     * {@code POST  /consultas} : Create a new cONSULTA.
     *
     * @param cONSULTA the cONSULTA to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cONSULTA, or with status {@code 400 (Bad Request)} if the cONSULTA has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/consultas")
    public ResponseEntity<CONSULTA> createCONSULTA(@Valid @RequestBody CONSULTA cONSULTA) throws URISyntaxException {
        log.debug("REST request to save CONSULTA : {}", cONSULTA);
        if (cONSULTA.getId() != null) {
            throw new BadRequestAlertException("A new cONSULTA cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CONSULTA result = cONSULTARepository.save(cONSULTA);
        return ResponseEntity
            .created(new URI("/api/consultas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /consultas/:id} : Updates an existing cONSULTA.
     *
     * @param id the id of the cONSULTA to save.
     * @param cONSULTA the cONSULTA to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cONSULTA,
     * or with status {@code 400 (Bad Request)} if the cONSULTA is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cONSULTA couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/consultas/{id}")
    public ResponseEntity<CONSULTA> updateCONSULTA(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CONSULTA cONSULTA
    ) throws URISyntaxException {
        log.debug("REST request to update CONSULTA : {}, {}", id, cONSULTA);
        if (cONSULTA.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cONSULTA.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cONSULTARepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CONSULTA result = cONSULTARepository.save(cONSULTA);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cONSULTA.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /consultas/:id} : Partial updates given fields of an existing cONSULTA, field will ignore if it is null
     *
     * @param id the id of the cONSULTA to save.
     * @param cONSULTA the cONSULTA to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cONSULTA,
     * or with status {@code 400 (Bad Request)} if the cONSULTA is not valid,
     * or with status {@code 404 (Not Found)} if the cONSULTA is not found,
     * or with status {@code 500 (Internal Server Error)} if the cONSULTA couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/consultas/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CONSULTA> partialUpdateCONSULTA(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CONSULTA cONSULTA
    ) throws URISyntaxException {
        log.debug("REST request to partial update CONSULTA partially : {}, {}", id, cONSULTA);
        if (cONSULTA.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cONSULTA.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cONSULTARepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CONSULTA> result = cONSULTARepository
            .findById(cONSULTA.getId())
            .map(
                existingCONSULTA -> {
                    if (cONSULTA.getCodConsulta() != null) {
                        existingCONSULTA.setCodConsulta(cONSULTA.getCodConsulta());
                    }
                    if (cONSULTA.getDataDaConsulta() != null) {
                        existingCONSULTA.setDataDaConsulta(cONSULTA.getDataDaConsulta());
                    }
                    if (cONSULTA.getHorarioDaConsulta() != null) {
                        existingCONSULTA.setHorarioDaConsulta(cONSULTA.getHorarioDaConsulta());
                    }

                    return existingCONSULTA;
                }
            )
            .map(cONSULTARepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cONSULTA.getId().toString())
        );
    }

    /**
     * {@code GET  /consultas} : get all the cONSULTAS.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cONSULTAS in body.
     */
    @GetMapping("/consultas")
    public List<CONSULTA> getAllCONSULTAS() {
        log.debug("REST request to get all CONSULTAS");
        return cONSULTARepository.findAll();
    }

    /**
     * {@code GET  /consultas/:id} : get the "id" cONSULTA.
     *
     * @param id the id of the cONSULTA to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cONSULTA, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/consultas/{id}")
    public ResponseEntity<CONSULTA> getCONSULTA(@PathVariable Long id) {
        log.debug("REST request to get CONSULTA : {}", id);
        Optional<CONSULTA> cONSULTA = cONSULTARepository.findById(id);
        return ResponseUtil.wrapOrNotFound(cONSULTA);
    }

    /**
     * {@code DELETE  /consultas/:id} : delete the "id" cONSULTA.
     *
     * @param id the id of the cONSULTA to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/consultas/{id}")
    public ResponseEntity<Void> deleteCONSULTA(@PathVariable Long id) {
        log.debug("REST request to delete CONSULTA : {}", id);
        cONSULTARepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
