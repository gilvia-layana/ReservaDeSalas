import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICONSULTA, CONSULTA } from '../consulta.model';
import { CONSULTAService } from '../service/consulta.service';

@Injectable({ providedIn: 'root' })
export class CONSULTARoutingResolveService implements Resolve<ICONSULTA> {
  constructor(protected service: CONSULTAService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICONSULTA> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((cONSULTA: HttpResponse<CONSULTA>) => {
          if (cONSULTA.body) {
            return of(cONSULTA.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CONSULTA());
  }
}
