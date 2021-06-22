import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IRESERVA } from '../reserva.model';
import { RESERVAService } from '../service/reserva.service';
import { RESERVADeleteDialogComponent } from '../delete/reserva-delete-dialog.component';

@Component({
  selector: 'jhi-reserva',
  templateUrl: './reserva.component.html',
})
export class RESERVAComponent implements OnInit {
  rESERVAS?: IRESERVA[];
  isLoading = false;

  constructor(protected rESERVAService: RESERVAService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.rESERVAService.query().subscribe(
      (res: HttpResponse<IRESERVA[]>) => {
        this.isLoading = false;
        this.rESERVAS = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IRESERVA): number {
    return item.id!;
  }

  delete(rESERVA: IRESERVA): void {
    const modalRef = this.modalService.open(RESERVADeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.rESERVA = rESERVA;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
