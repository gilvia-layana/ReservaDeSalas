jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { DadosPessoaisService } from '../service/dados-pessoais.service';
import { IDadosPessoais, DadosPessoais } from '../dados-pessoais.model';
import { IALUNO } from 'app/entities/aluno/aluno.model';
import { ALUNOService } from 'app/entities/aluno/service/aluno.service';
import { IPROFESSOR } from 'app/entities/professor/professor.model';
import { PROFESSORService } from 'app/entities/professor/service/professor.service';

import { DadosPessoaisUpdateComponent } from './dados-pessoais-update.component';

describe('Component Tests', () => {
  describe('DadosPessoais Management Update Component', () => {
    let comp: DadosPessoaisUpdateComponent;
    let fixture: ComponentFixture<DadosPessoaisUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let dadosPessoaisService: DadosPessoaisService;
    let aLUNOService: ALUNOService;
    let pROFESSORService: PROFESSORService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [DadosPessoaisUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(DadosPessoaisUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DadosPessoaisUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      dadosPessoaisService = TestBed.inject(DadosPessoaisService);
      aLUNOService = TestBed.inject(ALUNOService);
      pROFESSORService = TestBed.inject(PROFESSORService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call ALUNO query and add missing value', () => {
        const dadosPessoais: IDadosPessoais = { id: 456 };
        const aLUNO: IALUNO = { id: 1284 };
        dadosPessoais.aLUNO = aLUNO;

        const aLUNOCollection: IALUNO[] = [{ id: 35687 }];
        spyOn(aLUNOService, 'query').and.returnValue(of(new HttpResponse({ body: aLUNOCollection })));
        const additionalALUNOS = [aLUNO];
        const expectedCollection: IALUNO[] = [...additionalALUNOS, ...aLUNOCollection];
        spyOn(aLUNOService, 'addALUNOToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ dadosPessoais });
        comp.ngOnInit();

        expect(aLUNOService.query).toHaveBeenCalled();
        expect(aLUNOService.addALUNOToCollectionIfMissing).toHaveBeenCalledWith(aLUNOCollection, ...additionalALUNOS);
        expect(comp.aLUNOSSharedCollection).toEqual(expectedCollection);
      });

      it('Should call PROFESSOR query and add missing value', () => {
        const dadosPessoais: IDadosPessoais = { id: 456 };
        const pROFESSOR: IPROFESSOR = { id: 59943 };
        dadosPessoais.pROFESSOR = pROFESSOR;

        const pROFESSORCollection: IPROFESSOR[] = [{ id: 42431 }];
        spyOn(pROFESSORService, 'query').and.returnValue(of(new HttpResponse({ body: pROFESSORCollection })));
        const additionalPROFESSORS = [pROFESSOR];
        const expectedCollection: IPROFESSOR[] = [...additionalPROFESSORS, ...pROFESSORCollection];
        spyOn(pROFESSORService, 'addPROFESSORToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ dadosPessoais });
        comp.ngOnInit();

        expect(pROFESSORService.query).toHaveBeenCalled();
        expect(pROFESSORService.addPROFESSORToCollectionIfMissing).toHaveBeenCalledWith(pROFESSORCollection, ...additionalPROFESSORS);
        expect(comp.pROFESSORSSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const dadosPessoais: IDadosPessoais = { id: 456 };
        const aLUNO: IALUNO = { id: 65654 };
        dadosPessoais.aLUNO = aLUNO;
        const pROFESSOR: IPROFESSOR = { id: 57129 };
        dadosPessoais.pROFESSOR = pROFESSOR;

        activatedRoute.data = of({ dadosPessoais });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(dadosPessoais));
        expect(comp.aLUNOSSharedCollection).toContain(aLUNO);
        expect(comp.pROFESSORSSharedCollection).toContain(pROFESSOR);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const dadosPessoais = { id: 123 };
        spyOn(dadosPessoaisService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ dadosPessoais });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: dadosPessoais }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(dadosPessoaisService.update).toHaveBeenCalledWith(dadosPessoais);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const dadosPessoais = new DadosPessoais();
        spyOn(dadosPessoaisService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ dadosPessoais });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: dadosPessoais }));
        saveSubject.complete();

        // THEN
        expect(dadosPessoaisService.create).toHaveBeenCalledWith(dadosPessoais);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const dadosPessoais = { id: 123 };
        spyOn(dadosPessoaisService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ dadosPessoais });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(dadosPessoaisService.update).toHaveBeenCalledWith(dadosPessoais);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackALUNOById', () => {
        it('Should return tracked ALUNO primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackALUNOById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackPROFESSORById', () => {
        it('Should return tracked PROFESSOR primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackPROFESSORById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
