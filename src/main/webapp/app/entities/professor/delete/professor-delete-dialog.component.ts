import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPROFESSOR } from '../professor.model';
import { PROFESSORService } from '../service/professor.service';

@Component({
  templateUrl: './professor-delete-dialog.component.html',
})
export class PROFESSORDeleteDialogComponent {
  pROFESSOR?: IPROFESSOR;

  constructor(protected pROFESSORService: PROFESSORService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.pROFESSORService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
