jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ICONSULTA, CONSULTA } from '../consulta.model';
import { CONSULTAService } from '../service/consulta.service';

import { CONSULTARoutingResolveService } from './consulta-routing-resolve.service';

describe('Service Tests', () => {
  describe('CONSULTA routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: CONSULTARoutingResolveService;
    let service: CONSULTAService;
    let resultCONSULTA: ICONSULTA | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(CONSULTARoutingResolveService);
      service = TestBed.inject(CONSULTAService);
      resultCONSULTA = undefined;
    });

    describe('resolve', () => {
      it('should return ICONSULTA returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCONSULTA = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultCONSULTA).toEqual({ id: 123 });
      });

      it('should return new ICONSULTA if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCONSULTA = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultCONSULTA).toEqual(new CONSULTA());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCONSULTA = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultCONSULTA).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
