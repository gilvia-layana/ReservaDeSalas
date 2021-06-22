import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { ALUNOService } from '../service/aluno.service';

import { ALUNOComponent } from './aluno.component';

describe('Component Tests', () => {
  describe('ALUNO Management Component', () => {
    let comp: ALUNOComponent;
    let fixture: ComponentFixture<ALUNOComponent>;
    let service: ALUNOService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ALUNOComponent],
      })
        .overrideTemplate(ALUNOComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ALUNOComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(ALUNOService);

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
      expect(comp.aLUNOS?.[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
