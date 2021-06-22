import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RESERVADetailComponent } from './reserva-detail.component';

describe('Component Tests', () => {
  describe('RESERVA Management Detail Component', () => {
    let comp: RESERVADetailComponent;
    let fixture: ComponentFixture<RESERVADetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [RESERVADetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ rESERVA: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(RESERVADetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RESERVADetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load rESERVA on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.rESERVA).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
