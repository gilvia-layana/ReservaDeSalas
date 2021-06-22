package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.ALUNO;
import com.mycompany.myapp.repository.ALUNORepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.ALUNO}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ALUNOResource {

    private final Logger log = LoggerFactory.getLogger(ALUNOResource.class);

    private static final String ENTITY_NAME = "aLUNO";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ALUNORepository aLUNORepository;

    public ALUNOResource(ALUNORepository aLUNORepository) {
        this.aLUNORepository = aLUNORepository;
    }

    /**
     * {@code POST  /alunos} : Create a new aLUNO.
     *
     * @param aLUNO the aLUNO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new aLUNO, or with status {@code 400 (Bad Request)} if the aLUNO has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/alunos")
    public ResponseEntity<ALUNO> createALUNO(@Valid @RequestBody ALUNO aLUNO) throws URISyntaxException {
        log.debug("REST request to save ALUNO : {}", aLUNO);
        if (aLUNO.getId() != null) {
            throw new BadRequestAlertException("A new aLUNO cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ALUNO result = aLUNORepository.save(aLUNO);
        return ResponseEntity
            .created(new URI("/api/alunos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /alunos/:id} : Updates an existing aLUNO.
     *
     * @param id the id of the aLUNO to save.
     * @param aLUNO the aLUNO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated aLUNO,
     * or with status {@code 400 (Bad Request)} if the aLUNO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the aLUNO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/alunos/{id}")
    public ResponseEntity<ALUNO> updateALUNO(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody ALUNO aLUNO)
        throws URISyntaxException {
        log.debug("REST request to update ALUNO : {}, {}", id, aLUNO);
        if (aLUNO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, aLUNO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!aLUNORepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ALUNO result = aLUNORepository.save(aLUNO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, aLUNO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /alunos/:id} : Partial updates given fields of an existing aLUNO, field will ignore if it is null
     *
     * @param id the id of the aLUNO to save.
     * @param aLUNO the aLUNO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated aLUNO,
     * or with status {@code 400 (Bad Request)} if the aLUNO is not valid,
     * or with status {@code 404 (Not Found)} if the aLUNO is not found,
     * or with status {@code 500 (Internal Server Error)} if the aLUNO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/alunos/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ALUNO> partialUpdateALUNO(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ALUNO aLUNO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ALUNO partially : {}, {}", id, aLUNO);
        if (aLUNO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, aLUNO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!aLUNORepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ALUNO> result = aLUNORepository
            .findById(aLUNO.getId())
            .map(
                existingALUNO -> {
                    if (aLUNO.getMatriculaAluno() != null) {
                        existingALUNO.setMatriculaAluno(aLUNO.getMatriculaAluno());
                    }
                    if (aLUNO.getNome() != null) {
                        existingALUNO.setNome(aLUNO.getNome());
                    }
                    if (aLUNO.getAreaDeConhecimento() != null) {
                        existingALUNO.setAreaDeConhecimento(aLUNO.getAreaDeConhecimento());
                    }
                    if (aLUNO.getCurso() != null) {
                        existingALUNO.setCurso(aLUNO.getCurso());
                    }

                    return existingALUNO;
                }
            )
            .map(aLUNORepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, aLUNO.getId().toString())
        );
    }

    /**
     * {@code GET  /alunos} : get all the aLUNOS.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of aLUNOS in body.
     */
    @GetMapping("/alunos")
    public List<ALUNO> getAllALUNOS() {
        log.debug("REST request to get all ALUNOS");
        return aLUNORepository.findAll();
    }

    /**
     * {@code GET  /alunos/:id} : get the "id" aLUNO.
     *
     * @param id the id of the aLUNO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the aLUNO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/alunos/{id}")
    public ResponseEntity<ALUNO> getALUNO(@PathVariable Long id) {
        log.debug("REST request to get ALUNO : {}", id);
        Optional<ALUNO> aLUNO = aLUNORepository.findById(id);
        return ResponseUtil.wrapOrNotFound(aLUNO);
    }

    /**
     * {@code DELETE  /alunos/:id} : delete the "id" aLUNO.
     *
     * @param id the id of the aLUNO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/alunos/{id}")
    public ResponseEntity<Void> deleteALUNO(@PathVariable Long id) {
        log.debug("REST request to delete ALUNO : {}", id);
        aLUNORepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
