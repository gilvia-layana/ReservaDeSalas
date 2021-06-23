import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/config/input.constants';
import { StatusReserva } from 'app/entities/enumerations/status-reserva.model';
import { IRESERVA, RESERVA } from '../reserva.model';

import { RESERVAService } from './reserva.service';

describe('Service Tests', () => {
  describe('RESERVA Service', () => {
    let service: RESERVAService;
    let httpMock: HttpTestingController;
    let elemDefault: IRESERVA;
    let expectedResult: IRESERVA | IRESERVA[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(RESERVAService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        codReserva: 0,
        dataReserva: currentDate,
        horarioInicio: currentDate,
        horarioFinal: currentDate,
        dataSolicitacao: currentDate,
        horarioDaSolicitacao: currentDate,
        statusReservaSala: StatusReserva.Confirmada,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dataReserva: currentDate.format(DATE_FORMAT),
            horarioInicio: currentDate.format(DATE_TIME_FORMAT),
            horarioFinal: currentDate.format(DATE_TIME_FORMAT),
            dataSolicitacao: currentDate.format(DATE_FORMAT),
            horarioDaSolicitacao: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a RESERVA', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dataReserva: currentDate.format(DATE_FORMAT),
            horarioInicio: currentDate.format(DATE_TIME_FORMAT),
            horarioFinal: currentDate.format(DATE_TIME_FORMAT),
            dataSolicitacao: currentDate.format(DATE_FORMAT),
            horarioDaSolicitacao: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataReserva: currentDate,
            horarioInicio: currentDate,
            horarioFinal: currentDate,
            dataSolicitacao: currentDate,
            horarioDaSolicitacao: currentDate,
          },
          returnedFromService
        );

        service.create(new RESERVA()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a RESERVA', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            codReserva: 1,
            dataReserva: currentDate.format(DATE_FORMAT),
            horarioInicio: currentDate.format(DATE_TIME_FORMAT),
            horarioFinal: currentDate.format(DATE_TIME_FORMAT),
            dataSolicitacao: currentDate.format(DATE_FORMAT),
            horarioDaSolicitacao: currentDate.format(DATE_TIME_FORMAT),
            statusReservaSala: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataReserva: currentDate,
            horarioInicio: currentDate,
            horarioFinal: currentDate,
            dataSolicitacao: currentDate,
            horarioDaSolicitacao: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a RESERVA', () => {
        const patchObject = Object.assign(
          {
            codReserva: 1,
            dataReserva: currentDate.format(DATE_FORMAT),
            horarioInicio: currentDate.format(DATE_TIME_FORMAT),
            horarioDaSolicitacao: currentDate.format(DATE_TIME_FORMAT),
          },
          new RESERVA()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            dataReserva: currentDate,
            horarioInicio: currentDate,
            horarioFinal: currentDate,
            dataSolicitacao: currentDate,
            horarioDaSolicitacao: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of RESERVA', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            codReserva: 1,
            dataReserva: currentDate.format(DATE_FORMAT),
            horarioInicio: currentDate.format(DATE_TIME_FORMAT),
            horarioFinal: currentDate.format(DATE_TIME_FORMAT),
            dataSolicitacao: currentDate.format(DATE_FORMAT),
            horarioDaSolicitacao: currentDate.format(DATE_TIME_FORMAT),
            statusReservaSala: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataReserva: currentDate,
            horarioInicio: currentDate,
            horarioFinal: currentDate,
            dataSolicitacao: currentDate,
            horarioDaSolicitacao: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a RESERVA', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addRESERVAToCollectionIfMissing', () => {
        it('should add a RESERVA to an empty array', () => {
          const rESERVA: IRESERVA = { id: 123 };
          expectedResult = service.addRESERVAToCollectionIfMissing([], rESERVA);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(rESERVA);
        });

        it('should not add a RESERVA to an array that contains it', () => {
          const rESERVA: IRESERVA = { id: 123 };
          const rESERVACollection: IRESERVA[] = [
            {
              ...rESERVA,
            },
            { id: 456 },
          ];
          expectedResult = service.addRESERVAToCollectionIfMissing(rESERVACollection, rESERVA);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a RESERVA to an array that doesn't contain it", () => {
          const rESERVA: IRESERVA = { id: 123 };
          const rESERVACollection: IRESERVA[] = [{ id: 456 }];
          expectedResult = service.addRESERVAToCollectionIfMissing(rESERVACollection, rESERVA);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(rESERVA);
        });

        it('should add only unique RESERVA to an array', () => {
          const rESERVAArray: IRESERVA[] = [{ id: 123 }, { id: 456 }, { id: 1421 }];
          const rESERVACollection: IRESERVA[] = [{ id: 123 }];
          expectedResult = service.addRESERVAToCollectionIfMissing(rESERVACollection, ...rESERVAArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const rESERVA: IRESERVA = { id: 123 };
          const rESERVA2: IRESERVA = { id: 456 };
          expectedResult = service.addRESERVAToCollectionIfMissing([], rESERVA, rESERVA2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(rESERVA);
          expect(expectedResult).toContain(rESERVA2);
        });

        it('should accept null and undefined values', () => {
          const rESERVA: IRESERVA = { id: 123 };
          expectedResult = service.addRESERVAToCollectionIfMissing([], null, rESERVA, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(rESERVA);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
