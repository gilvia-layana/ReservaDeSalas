import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPROFESSOR } from '../professor.model';

@Component({
  selector: 'jhi-professor-detail',
  templateUrl: './professor-detail.component.html',
})
export class PROFESSORDetailComponent implements OnInit {
  pROFESSOR: IPROFESSOR | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pROFESSOR }) => {
      this.pROFESSOR = pROFESSOR;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
