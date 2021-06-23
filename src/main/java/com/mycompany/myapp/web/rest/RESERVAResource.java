package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.RESERVA;
import com.mycompany.myapp.repository.RESERVARepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.RESERVA}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RESERVAResource {

    private final Logger log = LoggerFactory.getLogger(RESERVAResource.class);

    private static final String ENTITY_NAME = "rESERVA";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RESERVARepository rESERVARepository;

    public RESERVAResource(RESERVARepository rESERVARepository) {
        this.rESERVARepository = rESERVARepository;
    }

    /**
     * {@code POST  /reservas} : Create a new rESERVA.
     *
     * @param rESERVA the rESERVA to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rESERVA, or with status {@code 400 (Bad Request)} if the rESERVA has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/reservas")
    public ResponseEntity<RESERVA> createRESERVA(@Valid @RequestBody RESERVA rESERVA) throws URISyntaxException {
        log.debug("REST request to save RESERVA : {}", rESERVA);
        if (rESERVA.getId() != null) {
            throw new BadRequestAlertException("A new rESERVA cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RESERVA result = rESERVARepository.save(rESERVA);
        return ResponseEntity
            .created(new URI("/api/reservas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /reservas/:id} : Updates an existing rESERVA.
     *
     * @param id the id of the rESERVA to save.
     * @param rESERVA the rESERVA to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rESERVA,
     * or with status {@code 400 (Bad Request)} if the rESERVA is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rESERVA couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/reservas/{id}")
    public ResponseEntity<RESERVA> updateRESERVA(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RESERVA rESERVA
    ) throws URISyntaxException {
        log.debug("REST request to update RESERVA : {}, {}", id, rESERVA);
        if (rESERVA.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rESERVA.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rESERVARepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RESERVA result = rESERVARepository.save(rESERVA);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rESERVA.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /reservas/:id} : Partial updates given fields of an existing rESERVA, field will ignore if it is null
     *
     * @param id the id of the rESERVA to save.
     * @param rESERVA the rESERVA to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rESERVA,
     * or with status {@code 400 (Bad Request)} if the rESERVA is not valid,
     * or with status {@code 404 (Not Found)} if the rESERVA is not found,
     * or with status {@code 500 (Internal Server Error)} if the rESERVA couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/reservas/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<RESERVA> partialUpdateRESERVA(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RESERVA rESERVA
    ) throws URISyntaxException {
        log.debug("REST request to partial update RESERVA partially : {}, {}", id, rESERVA);
        if (rESERVA.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rESERVA.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rESERVARepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RESERVA> result = rESERVARepository
            .findById(rESERVA.getId())
            .map(
                existingRESERVA -> {
                    if (rESERVA.getCodReserva() != null) {
                        existingRESERVA.setCodReserva(rESERVA.getCodReserva());
                    }
                    if (rESERVA.getDataReserva() != null) {
                        existingRESERVA.setDataReserva(rESERVA.getDataReserva());
                    }
                    if (rESERVA.getHorarioInicio() != null) {
                        existingRESERVA.setHorarioInicio(rESERVA.getHorarioInicio());
                    }
                    if (rESERVA.getHorarioFinal() != null) {
                        existingRESERVA.setHorarioFinal(rESERVA.getHorarioFinal());
                    }
                    if (rESERVA.getDataSolicitacao() != null) {
                        existingRESERVA.setDataSolicitacao(rESERVA.getDataSolicitacao());
                    }
                    if (rESERVA.getHorarioDaSolicitacao() != null) {
                        existingRESERVA.setHorarioDaSolicitacao(rESERVA.getHorarioDaSolicitacao());
                    }
                    if (rESERVA.getStatusReservaSala() != null) {
                        existingRESERVA.setStatusReservaSala(rESERVA.getStatusReservaSala());
                    }
                    if (rESERVA.getCodUsuario() != null) {
                        existingRESERVA.setCodUsuario(rESERVA.getCodUsuario());
                    }
                    if (rESERVA.getDisciplinaMinistrada() != null) {
                        existingRESERVA.setDisciplinaMinistrada(rESERVA.getDisciplinaMinistrada());
                    }

                    return existingRESERVA;
                }
            )
            .map(rESERVARepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rESERVA.getId().toString())
        );
    }

    /**
     * {@code GET  /reservas} : get all the rESERVAS.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rESERVAS in body.
     */
    @GetMapping("/reservas")
    public List<RESERVA> getAllRESERVAS() {
        log.debug("REST request to get all RESERVAS");
        return rESERVARepository.findAll();
    }

    /**
     * {@code GET  /reservas/:id} : get the "id" rESERVA.
     *
     * @param id the id of the rESERVA to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rESERVA, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/reservas/{id}")
    public ResponseEntity<RESERVA> getRESERVA(@PathVariable Long id) {
        log.debug("REST request to get RESERVA : {}", id);
        Optional<RESERVA> rESERVA = rESERVARepository.findById(id);
        return ResponseUtil.wrapOrNotFound(rESERVA);
    }

    /**
     * {@code DELETE  /reservas/:id} : delete the "id" rESERVA.
     *
     * @param id the id of the rESERVA to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/reservas/{id}")
    public ResponseEntity<Void> deleteRESERVA(@PathVariable Long id) {
        log.debug("REST request to delete RESERVA : {}", id);
        rESERVARepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
