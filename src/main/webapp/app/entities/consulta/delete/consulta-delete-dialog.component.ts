import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICONSULTA } from '../consulta.model';
import { CONSULTAService } from '../service/consulta.service';

@Component({
  templateUrl: './consulta-delete-dialog.component.html',
})
export class CONSULTADeleteDialogComponent {
  cONSULTA?: ICONSULTA;

  constructor(protected cONSULTAService: CONSULTAService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.cONSULTAService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
