import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDadosPessoais } from '../dados-pessoais.model';
import { DadosPessoaisService } from '../service/dados-pessoais.service';
import { DadosPessoaisDeleteDialogComponent } from '../delete/dados-pessoais-delete-dialog.component';

@Component({
  selector: 'jhi-dados-pessoais',
  templateUrl: './dados-pessoais.component.html',
})
export class DadosPessoaisComponent implements OnInit {
  dadosPessoais?: IDadosPessoais[];
  isLoading = false;

  constructor(protected dadosPessoaisService: DadosPessoaisService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.dadosPessoaisService.query().subscribe(
      (res: HttpResponse<IDadosPessoais[]>) => {
        this.isLoading = false;
        this.dadosPessoais = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IDadosPessoais): number {
    return item.id!;
  }

  delete(dadosPessoais: IDadosPessoais): void {
    const modalRef = this.modalService.open(DadosPessoaisDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.dadosPessoais = dadosPessoais;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
