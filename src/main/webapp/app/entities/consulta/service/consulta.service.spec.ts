import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ICONSULTA, CONSULTA } from '../consulta.model';

import { CONSULTAService } from './consulta.service';

describe('Service Tests', () => {
  describe('CONSULTA Service', () => {
    let service: CONSULTAService;
    let httpMock: HttpTestingController;
    let elemDefault: ICONSULTA;
    let expectedResult: ICONSULTA | ICONSULTA[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(CONSULTAService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        codConsulta: 0,
        dataDaConsulta: currentDate,
        horarioDaConsulta: currentDate,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dataDaConsulta: currentDate.format(DATE_FORMAT),
            horarioDaConsulta: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a CONSULTA', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dataDaConsulta: currentDate.format(DATE_FORMAT),
            horarioDaConsulta: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataDaConsulta: currentDate,
            horarioDaConsulta: currentDate,
          },
          returnedFromService
        );

        service.create(new CONSULTA()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a CONSULTA', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            codConsulta: 1,
            dataDaConsulta: currentDate.format(DATE_FORMAT),
            horarioDaConsulta: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataDaConsulta: currentDate,
            horarioDaConsulta: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a CONSULTA', () => {
        const patchObject = Object.assign(
          {
            codConsulta: 1,
            horarioDaConsulta: currentDate.format(DATE_TIME_FORMAT),
          },
          new CONSULTA()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            dataDaConsulta: currentDate,
            horarioDaConsulta: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of CONSULTA', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            codConsulta: 1,
            dataDaConsulta: currentDate.format(DATE_FORMAT),
            horarioDaConsulta: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataDaConsulta: currentDate,
            horarioDaConsulta: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a CONSULTA', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addCONSULTAToCollectionIfMissing', () => {
        it('should add a CONSULTA to an empty array', () => {
          const cONSULTA: ICONSULTA = { id: 123 };
          expectedResult = service.addCONSULTAToCollectionIfMissing([], cONSULTA);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(cONSULTA);
        });

        it('should not add a CONSULTA to an array that contains it', () => {
          const cONSULTA: ICONSULTA = { id: 123 };
          const cONSULTACollection: ICONSULTA[] = [
            {
              ...cONSULTA,
            },
            { id: 456 },
          ];
          expectedResult = service.addCONSULTAToCollectionIfMissing(cONSULTACollection, cONSULTA);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a CONSULTA to an array that doesn't contain it", () => {
          const cONSULTA: ICONSULTA = { id: 123 };
          const cONSULTACollection: ICONSULTA[] = [{ id: 456 }];
          expectedResult = service.addCONSULTAToCollectionIfMissing(cONSULTACollection, cONSULTA);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(cONSULTA);
        });

        it('should add only unique CONSULTA to an array', () => {
          const cONSULTAArray: ICONSULTA[] = [{ id: 123 }, { id: 456 }, { id: 22091 }];
          const cONSULTACollection: ICONSULTA[] = [{ id: 123 }];
          expectedResult = service.addCONSULTAToCollectionIfMissing(cONSULTACollection, ...cONSULTAArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const cONSULTA: ICONSULTA = { id: 123 };
          const cONSULTA2: ICONSULTA = { id: 456 };
          expectedResult = service.addCONSULTAToCollectionIfMissing([], cONSULTA, cONSULTA2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(cONSULTA);
          expect(expectedResult).toContain(cONSULTA2);
        });

        it('should accept null and undefined values', () => {
          const cONSULTA: ICONSULTA = { id: 123 };
          expectedResult = service.addCONSULTAToCollectionIfMissing([], null, cONSULTA, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(cONSULTA);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
