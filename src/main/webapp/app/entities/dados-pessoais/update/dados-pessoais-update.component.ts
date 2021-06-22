import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IDadosPessoais, DadosPessoais } from '../dados-pessoais.model';
import { DadosPessoaisService } from '../service/dados-pessoais.service';
import { IALUNO } from 'app/entities/aluno/aluno.model';
import { ALUNOService } from 'app/entities/aluno/service/aluno.service';
import { IPROFESSOR } from 'app/entities/professor/professor.model';
import { PROFESSORService } from 'app/entities/professor/service/professor.service';

@Component({
  selector: 'jhi-dados-pessoais-update',
  templateUrl: './dados-pessoais-update.component.html',
})
export class DadosPessoaisUpdateComponent implements OnInit {
  isSaving = false;

  aLUNOSSharedCollection: IALUNO[] = [];
  pROFESSORSSharedCollection: IPROFESSOR[] = [];

  editForm = this.fb.group({
    id: [],
    endereco: [],
    telefone: [],
    email: [],
    aLUNO: [],
    pROFESSOR: [],
  });

  constructor(
    protected dadosPessoaisService: DadosPessoaisService,
    protected aLUNOService: ALUNOService,
    protected pROFESSORService: PROFESSORService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dadosPessoais }) => {
      this.updateForm(dadosPessoais);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const dadosPessoais = this.createFromForm();
    if (dadosPessoais.id !== undefined) {
      this.subscribeToSaveResponse(this.dadosPessoaisService.update(dadosPessoais));
    } else {
      this.subscribeToSaveResponse(this.dadosPessoaisService.create(dadosPessoais));
    }
  }

  trackALUNOById(index: number, item: IALUNO): number {
    return item.id!;
  }

  trackPROFESSORById(index: number, item: IPROFESSOR): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDadosPessoais>>): void {
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

  protected updateForm(dadosPessoais: IDadosPessoais): void {
    this.editForm.patchValue({
      id: dadosPessoais.id,
      endereco: dadosPessoais.endereco,
      telefone: dadosPessoais.telefone,
      email: dadosPessoais.email,
      aLUNO: dadosPessoais.aLUNO,
      pROFESSOR: dadosPessoais.pROFESSOR,
    });

    this.aLUNOSSharedCollection = this.aLUNOService.addALUNOToCollectionIfMissing(this.aLUNOSSharedCollection, dadosPessoais.aLUNO);
    this.pROFESSORSSharedCollection = this.pROFESSORService.addPROFESSORToCollectionIfMissing(
      this.pROFESSORSSharedCollection,
      dadosPessoais.pROFESSOR
    );
  }

  protected loadRelationshipsOptions(): void {
    this.aLUNOService
      .query()
      .pipe(map((res: HttpResponse<IALUNO[]>) => res.body ?? []))
      .pipe(map((aLUNOS: IALUNO[]) => this.aLUNOService.addALUNOToCollectionIfMissing(aLUNOS, this.editForm.get('aLUNO')!.value)))
      .subscribe((aLUNOS: IALUNO[]) => (this.aLUNOSSharedCollection = aLUNOS));

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

  protected createFromForm(): IDadosPessoais {
    return {
      ...new DadosPessoais(),
      id: this.editForm.get(['id'])!.value,
      endereco: this.editForm.get(['endereco'])!.value,
      telefone: this.editForm.get(['telefone'])!.value,
      email: this.editForm.get(['email'])!.value,
      aLUNO: this.editForm.get(['aLUNO'])!.value,
      pROFESSOR: this.editForm.get(['pROFESSOR'])!.value,
    };
  }
}
