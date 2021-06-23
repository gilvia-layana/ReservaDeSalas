jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { RESERVAService } from '../service/reserva.service';
import { IRESERVA, RESERVA } from '../reserva.model';
import { ISALA } from 'app/entities/sala/sala.model';
import { SALAService } from 'app/entities/sala/service/sala.service';
import { ICONSULTA } from 'app/entities/consulta/consulta.model';
import { CONSULTAService } from 'app/entities/consulta/service/consulta.service';
import { IPROFESSOR } from 'app/entities/professor/professor.model';
import { PROFESSORService } from 'app/entities/professor/service/professor.service';

import { RESERVAUpdateComponent } from './reserva-update.component';

describe('Component Tests', () => {
  describe('RESERVA Management Update Component', () => {
    let comp: RESERVAUpdateComponent;
    let fixture: ComponentFixture<RESERVAUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let rESERVAService: RESERVAService;
    let sALAService: SALAService;
    let cONSULTAService: CONSULTAService;
    let pROFESSORService: PROFESSORService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [RESERVAUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(RESERVAUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RESERVAUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      rESERVAService = TestBed.inject(RESERVAService);
      sALAService = TestBed.inject(SALAService);
      cONSULTAService = TestBed.inject(CONSULTAService);
      pROFESSORService = TestBed.inject(PROFESSORService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call SALA query and add missing value', () => {
        const rESERVA: IRESERVA = { id: 456 };
        const sALA: ISALA = { id: 9717 };
        rESERVA.sALA = sALA;

        const sALACollection: ISALA[] = [{ id: 3354 }];
        spyOn(sALAService, 'query').and.returnValue(of(new HttpResponse({ body: sALACollection })));
        const additionalSALAS = [sALA];
        const expectedCollection: ISALA[] = [...additionalSALAS, ...sALACollection];
        spyOn(sALAService, 'addSALAToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ rESERVA });
        comp.ngOnInit();

        expect(sALAService.query).toHaveBeenCalled();
        expect(sALAService.addSALAToCollectionIfMissing).toHaveBeenCalledWith(sALACollection, ...additionalSALAS);
        expect(comp.sALASSharedCollection).toEqual(expectedCollection);
      });

      it('Should call CONSULTA query and add missing value', () => {
        const rESERVA: IRESERVA = { id: 456 };
        const cONSULTA: ICONSULTA = { id: 18665 };
        rESERVA.cONSULTA = cONSULTA;

        const cONSULTACollection: ICONSULTA[] = [{ id: 96643 }];
        spyOn(cONSULTAService, 'query').and.returnValue(of(new HttpResponse({ body: cONSULTACollection })));
        const additionalCONSULTAS = [cONSULTA];
        const expectedCollection: ICONSULTA[] = [...additionalCONSULTAS, ...cONSULTACollection];
        spyOn(cONSULTAService, 'addCONSULTAToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ rESERVA });
        comp.ngOnInit();

        expect(cONSULTAService.query).toHaveBeenCalled();
        expect(cONSULTAService.addCONSULTAToCollectionIfMissing).toHaveBeenCalledWith(cONSULTACollection, ...additionalCONSULTAS);
        expect(comp.cONSULTASSharedCollection).toEqual(expectedCollection);
      });

      it('Should call PROFESSOR query and add missing value', () => {
        const rESERVA: IRESERVA = { id: 456 };
        const rESERVA: IPROFESSOR = { id: 73113 };
        rESERVA.rESERVA = rESERVA;
        const rESERVA: IPROFESSOR = { id: 64184 };
        rESERVA.rESERVA = rESERVA;

        const pROFESSORCollection: IPROFESSOR[] = [{ id: 61573 }];
        spyOn(pROFESSORService, 'query').and.returnValue(of(new HttpResponse({ body: pROFESSORCollection })));
        const additionalPROFESSORS = [rESERVA, rESERVA];
        const expectedCollection: IPROFESSOR[] = [...additionalPROFESSORS, ...pROFESSORCollection];
        spyOn(pROFESSORService, 'addPROFESSORToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ rESERVA });
        comp.ngOnInit();

        expect(pROFESSORService.query).toHaveBeenCalled();
        expect(pROFESSORService.addPROFESSORToCollectionIfMissing).toHaveBeenCalledWith(pROFESSORCollection, ...additionalPROFESSORS);
        expect(comp.pROFESSORSSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const rESERVA: IRESERVA = { id: 456 };
        const sALA: ISALA = { id: 38719 };
        rESERVA.sALA = sALA;
        const cONSULTA: ICONSULTA = { id: 55448 };
        rESERVA.cONSULTA = cONSULTA;
        const rESERVA: IPROFESSOR = { id: 94859 };
        rESERVA.rESERVA = rESERVA;
        const rESERVA: IPROFESSOR = { id: 44511 };
        rESERVA.rESERVA = rESERVA;

        activatedRoute.data = of({ rESERVA });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(rESERVA));
        expect(comp.sALASSharedCollection).toContain(sALA);
        expect(comp.cONSULTASSharedCollection).toContain(cONSULTA);
        expect(comp.pROFESSORSSharedCollection).toContain(rESERVA);
        expect(comp.pROFESSORSSharedCollection).toContain(rESERVA);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const rESERVA = { id: 123 };
        spyOn(rESERVAService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ rESERVA });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: rESERVA }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(rESERVAService.update).toHaveBeenCalledWith(rESERVA);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const rESERVA = new RESERVA();
        spyOn(rESERVAService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ rESERVA });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: rESERVA }));
        saveSubject.complete();

        // THEN
        expect(rESERVAService.create).toHaveBeenCalledWith(rESERVA);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const rESERVA = { id: 123 };
        spyOn(rESERVAService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ rESERVA });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(rESERVAService.update).toHaveBeenCalledWith(rESERVA);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackSALAById', () => {
        it('Should return tracked SALA primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackSALAById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackCONSULTAById', () => {
        it('Should return tracked CONSULTA primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackCONSULTAById(0, entity);
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
