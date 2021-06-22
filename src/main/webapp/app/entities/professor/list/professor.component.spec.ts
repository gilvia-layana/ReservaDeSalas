import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { PROFESSORService } from '../service/professor.service';

import { PROFESSORComponent } from './professor.component';

describe('Component Tests', () => {
  describe('PROFESSOR Management Component', () => {
    let comp: PROFESSORComponent;
    let fixture: ComponentFixture<PROFESSORComponent>;
    let service: PROFESSORService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [PROFESSORComponent],
      })
        .overrideTemplate(PROFESSORComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PROFESSORComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(PROFESSORService);

      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [{ id: 123 }],
            headers,
          })
        )
      );
    });

    it('Should call load all on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.pROFESSORS?.[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
