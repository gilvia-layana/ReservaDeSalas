jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { CONSULTAService } from '../service/consulta.service';
import { ICONSULTA, CONSULTA } from '../consulta.model';
import { IALUNO } from 'app/entities/aluno/aluno.model';
import { ALUNOService } from 'app/entities/aluno/service/aluno.service';

import { CONSULTAUpdateComponent } from './consulta-update.component';

describe('Component Tests', () => {
  describe('CONSULTA Management Update Component', () => {
    let comp: CONSULTAUpdateComponent;
    let fixture: ComponentFixture<CONSULTAUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let cONSULTAService: CONSULTAService;
    let aLUNOService: ALUNOService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [CONSULTAUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(CONSULTAUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CONSULTAUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      cONSULTAService = TestBed.inject(CONSULTAService);
      aLUNOService = TestBed.inject(ALUNOService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call ALUNO query and add missing value', () => {
        const cONSULTA: ICONSULTA = { id: 456 };
        const aLUNO: IALUNO = { id: 77019 };
        cONSULTA.aLUNO = aLUNO;

        const aLUNOCollection: IALUNO[] = [{ id: 90147 }];
        spyOn(aLUNOService, 'query').and.returnValue(of(new HttpResponse({ body: aLUNOCollection })));
        const additionalALUNOS = [aLUNO];
        const expectedCollection: IALUNO[] = [...additionalALUNOS, ...aLUNOCollection];
        spyOn(aLUNOService, 'addALUNOToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ cONSULTA });
        comp.ngOnInit();

        expect(aLUNOService.query).toHaveBeenCalled();
        expect(aLUNOService.addALUNOToCollectionIfMissing).toHaveBeenCalledWith(aLUNOCollection, ...additionalALUNOS);
        expect(comp.aLUNOSSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const cONSULTA: ICONSULTA = { id: 456 };
        const aLUNO: IALUNO = { id: 22836 };
        cONSULTA.aLUNO = aLUNO;

        activatedRoute.data = of({ cONSULTA });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(cONSULTA));
        expect(comp.aLUNOSSharedCollection).toContain(aLUNO);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const cONSULTA = { id: 123 };
        spyOn(cONSULTAService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ cONSULTA });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: cONSULTA }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(cONSULTAService.update).toHaveBeenCalledWith(cONSULTA);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const cONSULTA = new CONSULTA();
        spyOn(cONSULTAService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ cONSULTA });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: cONSULTA }));
        saveSubject.complete();

        // THEN
        expect(cONSULTAService.create).toHaveBeenCalledWith(cONSULTA);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const cONSULTA = { id: 123 };
        spyOn(cONSULTAService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ cONSULTA });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(cONSULTAService.update).toHaveBeenCalledWith(cONSULTA);
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
    });
  });
});
