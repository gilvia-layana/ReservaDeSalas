import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { Area } from 'app/entities/enumerations/area.model';
import { IALUNO, ALUNO } from '../aluno.model';

import { ALUNOService } from './aluno.service';

describe('Service Tests', () => {
  describe('ALUNO Service', () => {
    let service: ALUNOService;
    let httpMock: HttpTestingController;
    let elemDefault: IALUNO;
    let expectedResult: IALUNO | IALUNO[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(ALUNOService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        matriculaAluno: 0,
        nome: 'AAAAAAA',
        areaDeConhecimento: Area.HUMANAS,
        curso: 'AAAAAAA',
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

      it('should create a ALUNO', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new ALUNO()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a ALUNO', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            matriculaAluno: 1,
            nome: 'BBBBBB',
            areaDeConhecimento: 'BBBBBB',
            curso: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a ALUNO', () => {
        const patchObject = Object.assign(
          {
            matriculaAluno: 1,
            nome: 'BBBBBB',
            curso: 'BBBBBB',
          },
          new ALUNO()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of ALUNO', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            matriculaAluno: 1,
            nome: 'BBBBBB',
            areaDeConhecimento: 'BBBBBB',
            curso: 'BBBBBB',
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

      it('should delete a ALUNO', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addALUNOToCollectionIfMissing', () => {
        it('should add a ALUNO to an empty array', () => {
          const aLUNO: IALUNO = { id: 123 };
          expectedResult = service.addALUNOToCollectionIfMissing([], aLUNO);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(aLUNO);
        });

        it('should not add a ALUNO to an array that contains it', () => {
          const aLUNO: IALUNO = { id: 123 };
          const aLUNOCollection: IALUNO[] = [
            {
              ...aLUNO,
            },
            { id: 456 },
          ];
          expectedResult = service.addALUNOToCollectionIfMissing(aLUNOCollection, aLUNO);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a ALUNO to an array that doesn't contain it", () => {
          const aLUNO: IALUNO = { id: 123 };
          const aLUNOCollection: IALUNO[] = [{ id: 456 }];
          expectedResult = service.addALUNOToCollectionIfMissing(aLUNOCollection, aLUNO);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(aLUNO);
        });

        it('should add only unique ALUNO to an array', () => {
          const aLUNOArray: IALUNO[] = [{ id: 123 }, { id: 456 }, { id: 78644 }];
          const aLUNOCollection: IALUNO[] = [{ id: 123 }];
          expectedResult = service.addALUNOToCollectionIfMissing(aLUNOCollection, ...aLUNOArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const aLUNO: IALUNO = { id: 123 };
          const aLUNO2: IALUNO = { id: 456 };
          expectedResult = service.addALUNOToCollectionIfMissing([], aLUNO, aLUNO2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(aLUNO);
          expect(expectedResult).toContain(aLUNO2);
        });

        it('should accept null and undefined values', () => {
          const aLUNO: IALUNO = { id: 123 };
          expectedResult = service.addALUNOToCollectionIfMissing([], null, aLUNO, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(aLUNO);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
