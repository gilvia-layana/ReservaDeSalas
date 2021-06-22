import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IALUNO } from '../aluno.model';
import { ALUNOService } from '../service/aluno.service';
import { ALUNODeleteDialogComponent } from '../delete/aluno-delete-dialog.component';

@Component({
  selector: 'jhi-aluno',
  templateUrl: './aluno.component.html',
})
export class ALUNOComponent implements OnInit {
  aLUNOS?: IALUNO[];
  isLoading = false;

  constructor(protected aLUNOService: ALUNOService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.aLUNOService.query().subscribe(
      (res: HttpResponse<IALUNO[]>) => {
        this.isLoading = false;
        this.aLUNOS = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IALUNO): number {
    return item.id!;
  }

  delete(aLUNO: IALUNO): void {
    const modalRef = this.modalService.open(ALUNODeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.aLUNO = aLUNO;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
