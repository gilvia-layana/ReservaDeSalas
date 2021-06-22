import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ISALA, SALA } from '../sala.model';
import { SALAService } from '../service/sala.service';

@Component({
  selector: 'jhi-sala-update',
  templateUrl: './sala-update.component.html',
})
export class SALAUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    codSala: [null, [Validators.required]],
    nome: [],
    local: [],
    status: [],
  });

  constructor(protected sALAService: SALAService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sALA }) => {
      this.updateForm(sALA);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const sALA = this.createFromForm();
    if (sALA.id !== undefined) {
      this.subscribeToSaveResponse(this.sALAService.update(sALA));
    } else {
      this.subscribeToSaveResponse(this.sALAService.create(sALA));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISALA>>): void {
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

  protected updateForm(sALA: ISALA): void {
    this.editForm.patchValue({
      id: sALA.id,
      codSala: sALA.codSala,
      nome: sALA.nome,
      local: sALA.local,
      status: sALA.status,
    });
  }

  protected createFromForm(): ISALA {
    return {
      ...new SALA(),
      id: this.editForm.get(['id'])!.value,
      codSala: this.editForm.get(['codSala'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      local: this.editForm.get(['local'])!.value,
      status: this.editForm.get(['status'])!.value,
    };
  }
}
