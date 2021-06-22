jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { ALUNOService } from '../service/aluno.service';
import { IALUNO, ALUNO } from '../aluno.model';

import { ALUNOUpdateComponent } from './aluno-update.component';

describe('Component Tests', () => {
  describe('ALUNO Management Update Component', () => {
    let comp: ALUNOUpdateComponent;
    let fixture: ComponentFixture<ALUNOUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let aLUNOService: ALUNOService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ALUNOUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(ALUNOUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ALUNOUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      aLUNOService = TestBed.inject(ALUNOService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const aLUNO: IALUNO = { id: 456 };

        activatedRoute.data = of({ aLUNO });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(aLUNO));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const aLUNO = { id: 123 };
        spyOn(aLUNOService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ aLUNO });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: aLUNO }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(aLUNOService.update).toHaveBeenCalledWith(aLUNO);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const aLUNO = new ALUNO();
        spyOn(aLUNOService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ aLUNO });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: aLUNO }));
        saveSubject.complete();

        // THEN
        expect(aLUNOService.create).toHaveBeenCalledWith(aLUNO);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const aLUNO = { id: 123 };
        spyOn(aLUNOService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ aLUNO });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(aLUNOService.update).toHaveBeenCalledWith(aLUNO);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
