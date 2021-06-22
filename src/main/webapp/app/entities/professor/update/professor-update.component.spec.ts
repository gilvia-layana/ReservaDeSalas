jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { PROFESSORService } from '../service/professor.service';
import { IPROFESSOR, PROFESSOR } from '../professor.model';

import { PROFESSORUpdateComponent } from './professor-update.component';

describe('Component Tests', () => {
  describe('PROFESSOR Management Update Component', () => {
    let comp: PROFESSORUpdateComponent;
    let fixture: ComponentFixture<PROFESSORUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let pROFESSORService: PROFESSORService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [PROFESSORUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(PROFESSORUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PROFESSORUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      pROFESSORService = TestBed.inject(PROFESSORService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const pROFESSOR: IPROFESSOR = { id: 456 };

        activatedRoute.data = of({ pROFESSOR });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(pROFESSOR));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const pROFESSOR = { id: 123 };
        spyOn(pROFESSORService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ pROFESSOR });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: pROFESSOR }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(pROFESSORService.update).toHaveBeenCalledWith(pROFESSOR);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const pROFESSOR = new PROFESSOR();
        spyOn(pROFESSORService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ pROFESSOR });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: pROFESSOR }));
        saveSubject.complete();

        // THEN
        expect(pROFESSORService.create).toHaveBeenCalledWith(pROFESSOR);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const pROFESSOR = { id: 123 };
        spyOn(pROFESSORService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ pROFESSOR });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(pROFESSORService.update).toHaveBeenCalledWith(pROFESSOR);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
