import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PROFESSORDetailComponent } from './professor-detail.component';

describe('Component Tests', () => {
  describe('PROFESSOR Management Detail Component', () => {
    let comp: PROFESSORDetailComponent;
    let fixture: ComponentFixture<PROFESSORDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [PROFESSORDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ pROFESSOR: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(PROFESSORDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PROFESSORDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load pROFESSOR on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.pROFESSOR).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
