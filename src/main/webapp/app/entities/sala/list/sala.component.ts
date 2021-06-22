import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISALA } from '../sala.model';
import { SALAService } from '../service/sala.service';
import { SALADeleteDialogComponent } from '../delete/sala-delete-dialog.component';

@Component({
  selector: 'jhi-sala',
  templateUrl: './sala.component.html',
})
export class SALAComponent implements OnInit {
  sALAS?: ISALA[];
  isLoading = false;

  constructor(protected sALAService: SALAService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.sALAService.query().subscribe(
      (res: HttpResponse<ISALA[]>) => {
        this.isLoading = false;
        this.sALAS = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: ISALA): number {
    return item.id!;
  }

  delete(sALA: ISALA): void {
    const modalRef = this.modalService.open(SALADeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.sALA = sALA;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
