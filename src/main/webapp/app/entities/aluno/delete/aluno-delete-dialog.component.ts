import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IALUNO } from '../aluno.model';
import { ALUNOService } from '../service/aluno.service';

@Component({
  templateUrl: './aluno-delete-dialog.component.html',
})
export class ALUNODeleteDialogComponent {
  aLUNO?: IALUNO;

  constructor(protected aLUNOService: ALUNOService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.aLUNOService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
