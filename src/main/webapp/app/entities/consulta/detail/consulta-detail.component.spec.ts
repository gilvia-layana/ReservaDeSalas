import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CONSULTADetailComponent } from './consulta-detail.component';

describe('Component Tests', () => {
  describe('CONSULTA Management Detail Component', () => {
    let comp: CONSULTADetailComponent;
    let fixture: ComponentFixture<CONSULTADetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [CONSULTADetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ cONSULTA: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(CONSULTADetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CONSULTADetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load cONSULTA on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.cONSULTA).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
