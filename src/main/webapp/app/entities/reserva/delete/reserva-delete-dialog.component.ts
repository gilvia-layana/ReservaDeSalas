import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IRESERVA } from '../reserva.model';
import { RESERVAService } from '../service/reserva.service';

@Component({
  templateUrl: './reserva-delete-dialog.component.html',
})
export class RESERVADeleteDialogComponent {
  rESERVA?: IRESERVA;

  constructor(protected rESERVAService: RESERVAService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.rESERVAService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
