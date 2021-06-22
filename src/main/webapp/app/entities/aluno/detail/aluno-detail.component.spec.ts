import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ALUNODetailComponent } from './aluno-detail.component';

describe('Component Tests', () => {
  describe('ALUNO Management Detail Component', () => {
    let comp: ALUNODetailComponent;
    let fixture: ComponentFixture<ALUNODetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [ALUNODetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ aLUNO: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(ALUNODetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ALUNODetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load aLUNO on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.aLUNO).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
