jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { PROFESSORService } from '../service/professor.service';

import { PROFESSORDeleteDialogComponent } from './professor-delete-dialog.component';

describe('Component Tests', () => {
  describe('PROFESSOR Management Delete Component', () => {
    let comp: PROFESSORDeleteDialogComponent;
    let fixture: ComponentFixture<PROFESSORDeleteDialogComponent>;
    let service: PROFESSORService;
    let mockActiveModal: NgbActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [PROFESSORDeleteDialogComponent],
        providers: [NgbActiveModal],
      })
        .overrideTemplate(PROFESSORDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PROFESSORDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(PROFESSORService);
      mockActiveModal = TestBed.inject(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.close).not.toHaveBeenCalled();
        expect(mockActiveModal.dismiss).toHaveBeenCalled();
      });
    });
  });
});
