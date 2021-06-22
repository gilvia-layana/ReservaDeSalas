import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IRESERVA, RESERVA } from '../reserva.model';
import { RESERVAService } from '../service/reserva.service';

@Injectable({ providedIn: 'root' })
export class RESERVARoutingResolveService implements Resolve<IRESERVA> {
  constructor(protected service: RESERVAService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRESERVA> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((rESERVA: HttpResponse<RESERVA>) => {
          if (rESERVA.body) {
            return of(rESERVA.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new RESERVA());
  }
}
