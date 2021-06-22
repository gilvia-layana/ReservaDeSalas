import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPROFESSOR } from '../professor.model';
import { PROFESSORService } from '../service/professor.service';
import { PROFESSORDeleteDialogComponent } from '../delete/professor-delete-dialog.component';

@Component({
  selector: 'jhi-professor',
  templateUrl: './professor.component.html',
})
export class PROFESSORComponent implements OnInit {
  pROFESSORS?: IPROFESSOR[];
  isLoading = false;

  constructor(protected pROFESSORService: PROFESSORService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.pROFESSORService.query().subscribe(
      (res: HttpResponse<IPROFESSOR[]>) => {
        this.isLoading = false;
        this.pROFESSORS = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IPROFESSOR): number {
    return item.id!;
  }

  delete(pROFESSOR: IPROFESSOR): void {
    const modalRef = this.modalService.open(PROFESSORDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.pROFESSOR = pROFESSOR;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
