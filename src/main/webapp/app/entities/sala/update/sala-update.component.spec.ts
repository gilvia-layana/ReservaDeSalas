jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { SALAService } from '../service/sala.service';
import { ISALA, SALA } from '../sala.model';

import { SALAUpdateComponent } from './sala-update.component';

describe('Component Tests', () => {
  describe('SALA Management Update Component', () => {
    let comp: SALAUpdateComponent;
    let fixture: ComponentFixture<SALAUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let sALAService: SALAService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [SALAUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(SALAUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SALAUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      sALAService = TestBed.inject(SALAService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const sALA: ISALA = { id: 456 };

        activatedRoute.data = of({ sALA });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(sALA));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const sALA = { id: 123 };
        spyOn(sALAService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ sALA });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: sALA }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(sALAService.update).toHaveBeenCalledWith(sALA);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const sALA = new SALA();
        spyOn(sALAService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ sALA });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: sALA }));
        saveSubject.complete();

        // THEN
        expect(sALAService.create).toHaveBeenCalledWith(sALA);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const sALA = { id: 123 };
        spyOn(sALAService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ sALA });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(sALAService.update).toHaveBeenCalledWith(sALA);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
