import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPROFESSOR, PROFESSOR } from '../professor.model';
import { PROFESSORService } from '../service/professor.service';

@Injectable({ providedIn: 'root' })
export class PROFESSORRoutingResolveService implements Resolve<IPROFESSOR> {
  constructor(protected service: PROFESSORService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPROFESSOR> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((pROFESSOR: HttpResponse<PROFESSOR>) => {
          if (pROFESSOR.body) {
            return of(pROFESSOR.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PROFESSOR());
  }
}
