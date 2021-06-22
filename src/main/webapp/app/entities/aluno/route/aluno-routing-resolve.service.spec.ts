jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IALUNO, ALUNO } from '../aluno.model';
import { ALUNOService } from '../service/aluno.service';

import { ALUNORoutingResolveService } from './aluno-routing-resolve.service';

describe('Service Tests', () => {
  describe('ALUNO routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: ALUNORoutingResolveService;
    let service: ALUNOService;
    let resultALUNO: IALUNO | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(ALUNORoutingResolveService);
      service = TestBed.inject(ALUNOService);
      resultALUNO = undefined;
    });

    describe('resolve', () => {
      it('should return IALUNO returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultALUNO = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultALUNO).toEqual({ id: 123 });
      });

      it('should return new IALUNO if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultALUNO = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultALUNO).toEqual(new ALUNO());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultALUNO = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultALUNO).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
