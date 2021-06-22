import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IPROFESSOR, PROFESSOR } from '../professor.model';

import { PROFESSORService } from './professor.service';

describe('Service Tests', () => {
  describe('PROFESSOR Service', () => {
    let service: PROFESSORService;
    let httpMock: HttpTestingController;
    let elemDefault: IPROFESSOR;
    let expectedResult: IPROFESSOR | IPROFESSOR[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(PROFESSORService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        matriculaProf: 0,
        nome: 'AAAAAAA',
        disciplina: 'AAAAAAA',
        faculdade: 'AAAAAAA',
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a PROFESSOR', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new PROFESSOR()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a PROFESSOR', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            matriculaProf: 1,
            nome: 'BBBBBB',
            disciplina: 'BBBBBB',
            faculdade: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a PROFESSOR', () => {
        const patchObject = Object.assign(
          {
            matriculaProf: 1,
            disciplina: 'BBBBBB',
          },
          new PROFESSOR()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of PROFESSOR', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            matriculaProf: 1,
            nome: 'BBBBBB',
            disciplina: 'BBBBBB',
            faculdade: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a PROFESSOR', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addPROFESSORToCollectionIfMissing', () => {
        it('should add a PROFESSOR to an empty array', () => {
          const pROFESSOR: IPROFESSOR = { id: 123 };
          expectedResult = service.addPROFESSORToCollectionIfMissing([], pROFESSOR);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(pROFESSOR);
        });

        it('should not add a PROFESSOR to an array that contains it', () => {
          const pROFESSOR: IPROFESSOR = { id: 123 };
          const pROFESSORCollection: IPROFESSOR[] = [
            {
              ...pROFESSOR,
            },
            { id: 456 },
          ];
          expectedResult = service.addPROFESSORToCollectionIfMissing(pROFESSORCollection, pROFESSOR);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a PROFESSOR to an array that doesn't contain it", () => {
          const pROFESSOR: IPROFESSOR = { id: 123 };
          const pROFESSORCollection: IPROFESSOR[] = [{ id: 456 }];
          expectedResult = service.addPROFESSORToCollectionIfMissing(pROFESSORCollection, pROFESSOR);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(pROFESSOR);
        });

        it('should add only unique PROFESSOR to an array', () => {
          const pROFESSORArray: IPROFESSOR[] = [{ id: 123 }, { id: 456 }, { id: 40497 }];
          const pROFESSORCollection: IPROFESSOR[] = [{ id: 123 }];
          expectedResult = service.addPROFESSORToCollectionIfMissing(pROFESSORCollection, ...pROFESSORArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const pROFESSOR: IPROFESSOR = { id: 123 };
          const pROFESSOR2: IPROFESSOR = { id: 456 };
          expectedResult = service.addPROFESSORToCollectionIfMissing([], pROFESSOR, pROFESSOR2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(pROFESSOR);
          expect(expectedResult).toContain(pROFESSOR2);
        });

        it('should accept null and undefined values', () => {
          const pROFESSOR: IPROFESSOR = { id: 123 };
          expectedResult = service.addPROFESSORToCollectionIfMissing([], null, pROFESSOR, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(pROFESSOR);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
