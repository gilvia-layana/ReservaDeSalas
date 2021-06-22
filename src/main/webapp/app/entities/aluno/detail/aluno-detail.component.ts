import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IALUNO } from '../aluno.model';

@Component({
  selector: 'jhi-aluno-detail',
  templateUrl: './aluno-detail.component.html',
})
export class ALUNODetailComponent implements OnInit {
  aLUNO: IALUNO | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ aLUNO }) => {
      this.aLUNO = aLUNO;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
