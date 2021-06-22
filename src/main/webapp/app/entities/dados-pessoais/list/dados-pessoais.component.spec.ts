import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { DadosPessoaisService } from '../service/dados-pessoais.service';

import { DadosPessoaisComponent } from './dados-pessoais.component';

describe('Component Tests', () => {
  describe('DadosPessoais Management Component', () => {
    let comp: DadosPessoaisComponent;
    let fixture: ComponentFixture<DadosPessoaisComponent>;
    let service: DadosPessoaisService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [DadosPessoaisComponent],
      })
        .overrideTemplate(DadosPessoaisComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DadosPessoaisComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(DadosPessoaisService);

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
      expect(comp.dadosPessoais?.[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
