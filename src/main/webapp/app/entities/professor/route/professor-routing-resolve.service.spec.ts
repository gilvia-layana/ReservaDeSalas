jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IPROFESSOR, PROFESSOR } from '../professor.model';
import { PROFESSORService } from '../service/professor.service';

import { PROFESSORRoutingResolveService } from './professor-routing-resolve.service';

describe('Service Tests', () => {
  describe('PROFESSOR routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: PROFESSORRoutingResolveService;
    let service: PROFESSORService;
    let resultPROFESSOR: IPROFESSOR | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(PROFESSORRoutingResolveService);
      service = TestBed.inject(PROFESSORService);
      resultPROFESSOR = undefined;
    });

    describe('resolve', () => {
      it('should return IPROFESSOR returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultPROFESSOR = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultPROFESSOR).toEqual({ id: 123 });
      });

      it('should return new IPROFESSOR if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultPROFESSOR = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultPROFESSOR).toEqual(new PROFESSOR());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultPROFESSOR = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultPROFESSOR).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
