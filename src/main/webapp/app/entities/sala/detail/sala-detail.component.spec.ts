import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SALADetailComponent } from './sala-detail.component';

describe('Component Tests', () => {
  describe('SALA Management Detail Component', () => {
    let comp: SALADetailComponent;
    let fixture: ComponentFixture<SALADetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [SALADetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ sALA: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(SALADetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SALADetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load sALA on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.sALA).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
