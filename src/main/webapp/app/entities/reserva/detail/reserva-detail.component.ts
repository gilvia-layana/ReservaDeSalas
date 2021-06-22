import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRESERVA } from '../reserva.model';

@Component({
  selector: 'jhi-reserva-detail',
  templateUrl: './reserva-detail.component.html',
})
export class RESERVADetailComponent implements OnInit {
  rESERVA: IRESERVA | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ rESERVA }) => {
      this.rESERVA = rESERVA;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
