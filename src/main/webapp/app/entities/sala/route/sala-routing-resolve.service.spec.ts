jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ISALA, SALA } from '../sala.model';
import { SALAService } from '../service/sala.service';

import { SALARoutingResolveService } from './sala-routing-resolve.service';

describe('Service Tests', () => {
  describe('SALA routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: SALARoutingResolveService;
    let service: SALAService;
    let resultSALA: ISALA | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(SALARoutingResolveService);
      service = TestBed.inject(SALAService);
      resultSALA = undefined;
    });

    describe('resolve', () => {
      it('should return ISALA returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultSALA = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultSALA).toEqual({ id: 123 });
      });

      it('should return new ISALA if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultSALA = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultSALA).toEqual(new SALA());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultSALA = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultSALA).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
