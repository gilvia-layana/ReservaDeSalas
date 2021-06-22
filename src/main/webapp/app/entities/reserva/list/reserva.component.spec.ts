import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { RESERVAService } from '../service/reserva.service';

import { RESERVAComponent } from './reserva.component';

describe('Component Tests', () => {
  describe('RESERVA Management Component', () => {
    let comp: RESERVAComponent;
    let fixture: ComponentFixture<RESERVAComponent>;
    let service: RESERVAService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [RESERVAComponent],
      })
        .overrideTemplate(RESERVAComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RESERVAComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(RESERVAService);

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
      expect(comp.rESERVAS?.[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
