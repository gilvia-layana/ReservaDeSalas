import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IPROFESSOR, PROFESSOR } from '../professor.model';
import { PROFESSORService } from '../service/professor.service';

@Component({
  selector: 'jhi-professor-update',
  templateUrl: './professor-update.component.html',
})
export class PROFESSORUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    matriculaProf: [null, [Validators.required]],
    nome: [],
    disciplina: [],
    faculdade: [],
  });

  constructor(protected pROFESSORService: PROFESSORService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pROFESSOR }) => {
      this.updateForm(pROFESSOR);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pROFESSOR = this.createFromForm();
    if (pROFESSOR.id !== undefined) {
      this.subscribeToSaveResponse(this.pROFESSORService.update(pROFESSOR));
    } else {
      this.subscribeToSaveResponse(this.pROFESSORService.create(pROFESSOR));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPROFESSOR>>): void {
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

  protected updateForm(pROFESSOR: IPROFESSOR): void {
    this.editForm.patchValue({
      id: pROFESSOR.id,
      matriculaProf: pROFESSOR.matriculaProf,
      nome: pROFESSOR.nome,
      disciplina: pROFESSOR.disciplina,
      faculdade: pROFESSOR.faculdade,
    });
  }

  protected createFromForm(): IPROFESSOR {
    return {
      ...new PROFESSOR(),
      id: this.editForm.get(['id'])!.value,
      matriculaProf: this.editForm.get(['matriculaProf'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      disciplina: this.editForm.get(['disciplina'])!.value,
      faculdade: this.editForm.get(['faculdade'])!.value,
    };
  }
}
