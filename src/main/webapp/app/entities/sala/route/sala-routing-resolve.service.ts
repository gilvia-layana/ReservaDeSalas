import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISALA, SALA } from '../sala.model';
import { SALAService } from '../service/sala.service';

@Injectable({ providedIn: 'root' })
export class SALARoutingResolveService implements Resolve<ISALA> {
  constructor(protected service: SALAService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISALA> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((sALA: HttpResponse<SALA>) => {
          if (sALA.body) {
            return of(sALA.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SALA());
  }
}
