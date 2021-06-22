import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IALUNO, ALUNO } from '../aluno.model';
import { ALUNOService } from '../service/aluno.service';

@Component({
  selector: 'jhi-aluno-update',
  templateUrl: './aluno-update.component.html',
})
export class ALUNOUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    matriculaAluno: [null, [Validators.required]],
    nome: [],
    areaDeConhecimento: [],
    curso: [],
  });

  constructor(protected aLUNOService: ALUNOService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ aLUNO }) => {
      this.updateForm(aLUNO);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const aLUNO = this.createFromForm();
    if (aLUNO.id !== undefined) {
      this.subscribeToSaveResponse(this.aLUNOService.update(aLUNO));
    } else {
      this.subscribeToSaveResponse(this.aLUNOService.create(aLUNO));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IALUNO>>): void {
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

  protected updateForm(aLUNO: IALUNO): void {
    this.editForm.patchValue({
      id: aLUNO.id,
      matriculaAluno: aLUNO.matriculaAluno,
      nome: aLUNO.nome,
      areaDeConhecimento: aLUNO.areaDeConhecimento,
      curso: aLUNO.curso,
    });
  }

  protected createFromForm(): IALUNO {
    return {
      ...new ALUNO(),
      id: this.editForm.get(['id'])!.value,
      matriculaAluno: this.editForm.get(['matriculaAluno'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      areaDeConhecimento: this.editForm.get(['areaDeConhecimento'])!.value,
      curso: this.editForm.get(['curso'])!.value,
    };
  }
}
