import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ICONSULTA, CONSULTA } from '../consulta.model';
import { CONSULTAService } from '../service/consulta.service';
import { IALUNO } from 'app/entities/aluno/aluno.model';
import { ALUNOService } from 'app/entities/aluno/service/aluno.service';

@Component({
  selector: 'jhi-consulta-update',
  templateUrl: './consulta-update.component.html',
})
export class CONSULTAUpdateComponent implements OnInit {
  isSaving = false;

  aLUNOSSharedCollection: IALUNO[] = [];

  editForm = this.fb.group({
    id: [],
    nomeAluno: [],
    codConsulta: [null, [Validators.required]],
    aLUNO: [],
  });

  constructor(
    protected cONSULTAService: CONSULTAService,
    protected aLUNOService: ALUNOService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cONSULTA }) => {
      this.updateForm(cONSULTA);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const cONSULTA = this.createFromForm();
    if (cONSULTA.id !== undefined) {
      this.subscribeToSaveResponse(this.cONSULTAService.update(cONSULTA));
    } else {
      this.subscribeToSaveResponse(this.cONSULTAService.create(cONSULTA));
    }
  }

  trackALUNOById(index: number, item: IALUNO): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICONSULTA>>): void {
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

  protected updateForm(cONSULTA: ICONSULTA): void {
    this.editForm.patchValue({
      id: cONSULTA.id,
      nomeAluno: cONSULTA.nomeAluno,
      codConsulta: cONSULTA.codConsulta,
      aLUNO: cONSULTA.aLUNO,
    });

    this.aLUNOSSharedCollection = this.aLUNOService.addALUNOToCollectionIfMissing(this.aLUNOSSharedCollection, cONSULTA.aLUNO);
  }

  protected loadRelationshipsOptions(): void {
    this.aLUNOService
      .query()
      .pipe(map((res: HttpResponse<IALUNO[]>) => res.body ?? []))
      .pipe(map((aLUNOS: IALUNO[]) => this.aLUNOService.addALUNOToCollectionIfMissing(aLUNOS, this.editForm.get('aLUNO')!.value)))
      .subscribe((aLUNOS: IALUNO[]) => (this.aLUNOSSharedCollection = aLUNOS));
  }

  protected createFromForm(): ICONSULTA {
    return {
      ...new CONSULTA(),
      id: this.editForm.get(['id'])!.value,
      nomeAluno: this.editForm.get(['nomeAluno'])!.value,
      codConsulta: this.editForm.get(['codConsulta'])!.value,
      aLUNO: this.editForm.get(['aLUNO'])!.value,
    };
  }
}