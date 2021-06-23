import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IRESERVA, RESERVA } from '../reserva.model';
import { RESERVAService } from '../service/reserva.service';
import { ISALA } from 'app/entities/sala/sala.model';
import { SALAService } from 'app/entities/sala/service/sala.service';
import { ICONSULTA } from 'app/entities/consulta/consulta.model';
import { CONSULTAService } from 'app/entities/consulta/service/consulta.service';
import { IPROFESSOR } from 'app/entities/professor/professor.model';
import { PROFESSORService } from 'app/entities/professor/service/professor.service';

@Component({
  selector: 'jhi-reserva-update',
  templateUrl: './reserva-update.component.html',
})
export class RESERVAUpdateComponent implements OnInit {
  isSaving = false;

  sALASSharedCollection: ISALA[] = [];
  cONSULTASSharedCollection: ICONSULTA[] = [];
  pROFESSORSSharedCollection: IPROFESSOR[] = [];

  editForm = this.fb.group({
    id: [],
    codReserva: [null, [Validators.required]],
    dataReserva: [],
    horarioInicio: [],
    horarioFinal: [],
    dataSolicitacao: [],
    horarioDaSolicitacao: [],
    statusReservaSala: [],
    sALA: [],
    cONSULTA: [],
    pROFESSOR: [],
  });

  constructor(
    protected rESERVAService: RESERVAService,
    protected sALAService: SALAService,
    protected cONSULTAService: CONSULTAService,
    protected pROFESSORService: PROFESSORService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ rESERVA }) => {
      if (rESERVA.id === undefined) {
        const today = dayjs().startOf('day');
        rESERVA.horarioInicio = today;
        rESERVA.horarioFinal = today;
        rESERVA.dataSolicitacao = today;
        rESERVA.horarioDaSolicitacao = today;
      }

      this.updateForm(rESERVA);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const rESERVA = this.createFromForm();
    if (rESERVA.id !== undefined) {
      this.subscribeToSaveResponse(this.rESERVAService.update(rESERVA));
    } else {
      this.subscribeToSaveResponse(this.rESERVAService.create(rESERVA));
    }
  }

  trackSALAById(index: number, item: ISALA): number {
    return item.id!;
  }

  trackCONSULTAById(index: number, item: ICONSULTA): number {
    return item.id!;
  }

  trackPROFESSORById(index: number, item: IPROFESSOR): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRESERVA>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(rESERVA: IRESERVA): void {
    this.editForm.patchValue({
      id: rESERVA.id,
      codReserva: rESERVA.codReserva,
      dataReserva: rESERVA.dataReserva,
      horarioInicio: rESERVA.horarioInicio ? rESERVA.horarioInicio.format(DATE_TIME_FORMAT) : null,
      horarioFinal: rESERVA.horarioFinal ? rESERVA.horarioFinal.format(DATE_TIME_FORMAT) : null,
      dataSolicitacao: rESERVA.dataSolicitacao ? rESERVA.dataSolicitacao.format(DATE_TIME_FORMAT) : null,
      horarioDaSolicitacao: rESERVA.horarioDaSolicitacao ? rESERVA.horarioDaSolicitacao.format(DATE_TIME_FORMAT) : null,
      statusReservaSala: rESERVA.statusReservaSala,
      sALA: rESERVA.sALA,
      cONSULTA: rESERVA.cONSULTA,
      pROFESSOR: rESERVA.pROFESSOR,
    });

    this.sALASSharedCollection = this.sALAService.addSALAToCollectionIfMissing(this.sALASSharedCollection, rESERVA.sALA);
    this.cONSULTASSharedCollection = this.cONSULTAService.addCONSULTAToCollectionIfMissing(
      this.cONSULTASSharedCollection,
      rESERVA.cONSULTA
    );
    this.pROFESSORSSharedCollection = this.pROFESSORService.addPROFESSORToCollectionIfMissing(
      this.pROFESSORSSharedCollection,
      rESERVA.pROFESSOR
    );
  }

  protected loadRelationshipsOptions(): void {
    this.sALAService
      .query()
      .pipe(map((res: HttpResponse<ISALA[]>) => res.body ?? []))
      .pipe(map((sALAS: ISALA[]) => this.sALAService.addSALAToCollectionIfMissing(sALAS, this.editForm.get('sALA')!.value)))
      .subscribe((sALAS: ISALA[]) => (this.sALASSharedCollection = sALAS));

    this.cONSULTAService
      .query()
      .pipe(map((res: HttpResponse<ICONSULTA[]>) => res.body ?? []))
      .pipe(
        map((cONSULTAS: ICONSULTA[]) =>
          this.cONSULTAService.addCONSULTAToCollectionIfMissing(cONSULTAS, this.editForm.get('cONSULTA')!.value)
        )
      )
      .subscribe((cONSULTAS: ICONSULTA[]) => (this.cONSULTASSharedCollection = cONSULTAS));

    this.pROFESSORService
      .query()
      .pipe(map((res: HttpResponse<IPROFESSOR[]>) => res.body ?? []))
      .pipe(
        map((pROFESSORS: IPROFESSOR[]) =>
          this.pROFESSORService.addPROFESSORToCollectionIfMissing(pROFESSORS, this.editForm.get('pROFESSOR')!.value)
        )
      )
      .subscribe((pROFESSORS: IPROFESSOR[]) => (this.pROFESSORSSharedCollection = pROFESSORS));
  }

  protected createFromForm(): IRESERVA {
    return {
      ...new RESERVA(),
      id: this.editForm.get(['id'])!.value,
      codReserva: this.editForm.get(['codReserva'])!.value,
      dataReserva: this.editForm.get(['dataReserva'])!.value,
      horarioInicio: this.editForm.get(['horarioInicio'])!.value
        ? dayjs(this.editForm.get(['horarioInicio'])!.value, DATE_TIME_FORMAT)
        : undefined,
      horarioFinal: this.editForm.get(['horarioFinal'])!.value
        ? dayjs(this.editForm.get(['horarioFinal'])!.value, DATE_TIME_FORMAT)
        : undefined,
      dataSolicitacao: this.editForm.get(['dataSolicitacao'])!.value
        ? dayjs(this.editForm.get(['dataSolicitacao'])!.value, DATE_TIME_FORMAT)
        : undefined,
      horarioDaSolicitacao: this.editForm.get(['horarioDaSolicitacao'])!.value
        ? dayjs(this.editForm.get(['horarioDaSolicitacao'])!.value, DATE_TIME_FORMAT)
        : undefined,
      statusReservaSala: this.editForm.get(['statusReservaSala'])!.value,
      sALA: this.editForm.get(['sALA'])!.value,
      cONSULTA: this.editForm.get(['cONSULTA'])!.value,
      pROFESSOR: this.editForm.get(['pROFESSOR'])!.value,
    };
  }
}
