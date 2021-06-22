import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ISALA } from '../sala.model';
import { SALAService } from '../service/sala.service';

@Component({
  templateUrl: './sala-delete-dialog.component.html',
})
export class SALADeleteDialogComponent {
  sALA?: ISALA;

  constructor(protected sALAService: SALAService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.sALAService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
