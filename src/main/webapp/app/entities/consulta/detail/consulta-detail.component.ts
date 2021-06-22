import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICONSULTA } from '../consulta.model';

@Component({
  selector: 'jhi-consulta-detail',
  templateUrl: './consulta-detail.component.html',
})
export class CONSULTADetailComponent implements OnInit {
  cONSULTA: ICONSULTA | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cONSULTA }) => {
      this.cONSULTA = cONSULTA;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
