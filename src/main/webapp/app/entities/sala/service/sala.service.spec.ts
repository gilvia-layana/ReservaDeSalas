import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { StatusSala } from 'app/entities/enumerations/status-sala.model';
import { ISALA, SALA } from '../sala.model';

import { SALAService } from './sala.service';

describe('Service Tests', () => {
  describe('SALA Service', () => {
    let service: SALAService;
    let httpMock: HttpTestingController;
    let elemDefault: ISALA;
    let expectedResult: ISALA | ISALA[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(SALAService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        codSala: 0,
        nome: 'AAAAAAA',
        local: 'AAAAAAA',
        status: StatusSala.Livre,
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

      it('should create a SALA', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new SALA()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a SALA', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            codSala: 1,
            nome: 'BBBBBB',
            local: 'BBBBBB',
            status: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a SALA', () => {
        const patchObject = Object.assign(
          {
            codSala: 1,
            status: 'BBBBBB',
          },
          new SALA()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of SALA', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            codSala: 1,
            nome: 'BBBBBB',
            local: 'BBBBBB',
            status: 'BBBBBB',
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

      it('should delete a SALA', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addSALAToCollectionIfMissing', () => {
        it('should add a SALA to an empty array', () => {
          const sALA: ISALA = { id: 123 };
          expectedResult = service.addSALAToCollectionIfMissing([], sALA);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(sALA);
        });

        it('should not add a SALA to an array that contains it', () => {
          const sALA: ISALA = { id: 123 };
          const sALACollection: ISALA[] = [
            {
              ...sALA,
            },
            { id: 456 },
          ];
          expectedResult = service.addSALAToCollectionIfMissing(sALACollection, sALA);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a SALA to an array that doesn't contain it", () => {
          const sALA: ISALA = { id: 123 };
          const sALACollection: ISALA[] = [{ id: 456 }];
          expectedResult = service.addSALAToCollectionIfMissing(sALACollection, sALA);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(sALA);
        });

        it('should add only unique SALA to an array', () => {
          const sALAArray: ISALA[] = [{ id: 123 }, { id: 456 }, { id: 88860 }];
          const sALACollection: ISALA[] = [{ id: 123 }];
          expectedResult = service.addSALAToCollectionIfMissing(sALACollection, ...sALAArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const sALA: ISALA = { id: 123 };
          const sALA2: ISALA = { id: 456 };
          expectedResult = service.addSALAToCollectionIfMissing([], sALA, sALA2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(sALA);
          expect(expectedResult).toContain(sALA2);
        });

        it('should accept null and undefined values', () => {
          const sALA: ISALA = { id: 123 };
          expectedResult = service.addSALAToCollectionIfMissing([], null, sALA, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(sALA);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
