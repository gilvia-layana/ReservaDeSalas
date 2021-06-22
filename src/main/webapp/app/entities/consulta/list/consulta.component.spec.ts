import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { CONSULTAService } from '../service/consulta.service';

import { CONSULTAComponent } from './consulta.component';

describe('Component Tests', () => {
  describe('CONSULTA Management Component', () => {
    let comp: CONSULTAComponent;
    let fixture: ComponentFixture<CONSULTAComponent>;
    let service: CONSULTAService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [CONSULTAComponent],
      })
        .overrideTemplate(CONSULTAComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CONSULTAComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(CONSULTAService);

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
      expect(comp.cONSULTAS?.[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
