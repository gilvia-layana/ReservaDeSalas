import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IALUNO, ALUNO } from '../aluno.model';
import { ALUNOService } from '../service/aluno.service';

@Injectable({ providedIn: 'root' })
export class ALUNORoutingResolveService implements Resolve<IALUNO> {
  constructor(protected service: ALUNOService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IALUNO> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((aLUNO: HttpResponse<ALUNO>) => {
          if (aLUNO.body) {
            return of(aLUNO.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ALUNO());
  }
}
