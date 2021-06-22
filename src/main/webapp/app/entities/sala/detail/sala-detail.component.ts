import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISALA } from '../sala.model';

@Component({
  selector: 'jhi-sala-detail',
  templateUrl: './sala-detail.component.html',
})
export class SALADetailComponent implements OnInit {
  sALA: ISALA | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sALA }) => {
      this.sALA = sALA;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
