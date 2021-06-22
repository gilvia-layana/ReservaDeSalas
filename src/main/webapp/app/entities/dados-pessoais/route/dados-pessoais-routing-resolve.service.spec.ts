jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IDadosPessoais, DadosPessoais } from '../dados-pessoais.model';
import { DadosPessoaisService } from '../service/dados-pessoais.service';

import { DadosPessoaisRoutingResolveService } from './dados-pessoais-routing-resolve.service';

describe('Service Tests', () => {
  describe('DadosPessoais routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: DadosPessoaisRoutingResolveService;
    let service: DadosPessoaisService;
    let resultDadosPessoais: IDadosPessoais | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(DadosPessoaisRoutingResolveService);
      service = TestBed.inject(DadosPessoaisService);
      resultDadosPessoais = undefined;
    });

    describe('resolve', () => {
      it('should return IDadosPessoais returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultDadosPessoais = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultDadosPessoais).toEqual({ id: 123 });
      });

      it('should return new IDadosPessoais if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultDadosPessoais = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultDadosPessoais).toEqual(new DadosPessoais());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultDadosPessoais = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultDadosPessoais).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
