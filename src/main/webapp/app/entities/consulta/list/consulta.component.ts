import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICONSULTA } from '../consulta.model';
import { CONSULTAService } from '../service/consulta.service';
import { CONSULTADeleteDialogComponent } from '../delete/consulta-delete-dialog.component';

@Component({
  selector: 'jhi-consulta',
  templateUrl: './consulta.component.html',
})
export class CONSULTAComponent implements OnInit {
  cONSULTAS?: ICONSULTA[];
  isLoading = false;

  constructor(protected cONSULTAService: CONSULTAService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.cONSULTAService.query().subscribe(
      (res: HttpResponse<ICONSULTA[]>) => {
        this.isLoading = false;
        this.cONSULTAS = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: ICONSULTA): number {
    return item.id!;
  }

  delete(cONSULTA: ICONSULTA): void {
    const modalRef = this.modalService.open(CONSULTADeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.cONSULTA = cONSULTA;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
