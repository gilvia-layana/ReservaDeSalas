import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IALUNO, getALUNOIdentifier } from '../aluno.model';

export type EntityResponseType = HttpResponse<IALUNO>;
export type EntityArrayResponseType = HttpResponse<IALUNO[]>;

@Injectable({ providedIn: 'root' })
export class ALUNOService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/alunos');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(aLUNO: IALUNO): Observable<EntityResponseType> {
    return this.http.post<IALUNO>(this.resourceUrl, aLUNO, { observe: 'response' });
  }

  update(aLUNO: IALUNO): Observable<EntityResponseType> {
    return this.http.put<IALUNO>(`${this.resourceUrl}/${getALUNOIdentifier(aLUNO) as number}`, aLUNO, { observe: 'response' });
  }

  partialUpdate(aLUNO: IALUNO): Observable<EntityResponseType> {
    return this.http.patch<IALUNO>(`${this.resourceUrl}/${getALUNOIdentifier(aLUNO) as number}`, aLUNO, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IALUNO>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IALUNO[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addALUNOToCollectionIfMissing(aLUNOCollection: IALUNO[], ...aLUNOSToCheck: (IALUNO | null | undefined)[]): IALUNO[] {
    const aLUNOS: IALUNO[] = aLUNOSToCheck.filter(isPresent);
    if (aLUNOS.length > 0) {
      const aLUNOCollectionIdentifiers = aLUNOCollection.map(aLUNOItem => getALUNOIdentifier(aLUNOItem)!);
      const aLUNOSToAdd = aLUNOS.filter(aLUNOItem => {
        const aLUNOIdentifier = getALUNOIdentifier(aLUNOItem);
        if (aLUNOIdentifier == null || aLUNOCollectionIdentifiers.includes(aLUNOIdentifier)) {
          return false;
        }
        aLUNOCollectionIdentifiers.push(aLUNOIdentifier);
        return true;
      });
      return [...aLUNOSToAdd, ...aLUNOCollection];
    }
    return aLUNOCollection;
  }
}
