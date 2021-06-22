import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPROFESSOR, getPROFESSORIdentifier } from '../professor.model';

export type EntityResponseType = HttpResponse<IPROFESSOR>;
export type EntityArrayResponseType = HttpResponse<IPROFESSOR[]>;

@Injectable({ providedIn: 'root' })
export class PROFESSORService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/professors');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(pROFESSOR: IPROFESSOR): Observable<EntityResponseType> {
    return this.http.post<IPROFESSOR>(this.resourceUrl, pROFESSOR, { observe: 'response' });
  }

  update(pROFESSOR: IPROFESSOR): Observable<EntityResponseType> {
    return this.http.put<IPROFESSOR>(`${this.resourceUrl}/${getPROFESSORIdentifier(pROFESSOR) as number}`, pROFESSOR, {
      observe: 'response',
    });
  }

  partialUpdate(pROFESSOR: IPROFESSOR): Observable<EntityResponseType> {
    return this.http.patch<IPROFESSOR>(`${this.resourceUrl}/${getPROFESSORIdentifier(pROFESSOR) as number}`, pROFESSOR, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPROFESSOR>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPROFESSOR[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPROFESSORToCollectionIfMissing(
    pROFESSORCollection: IPROFESSOR[],
    ...pROFESSORSToCheck: (IPROFESSOR | null | undefined)[]
  ): IPROFESSOR[] {
    const pROFESSORS: IPROFESSOR[] = pROFESSORSToCheck.filter(isPresent);
    if (pROFESSORS.length > 0) {
      const pROFESSORCollectionIdentifiers = pROFESSORCollection.map(pROFESSORItem => getPROFESSORIdentifier(pROFESSORItem)!);
      const pROFESSORSToAdd = pROFESSORS.filter(pROFESSORItem => {
        const pROFESSORIdentifier = getPROFESSORIdentifier(pROFESSORItem);
        if (pROFESSORIdentifier == null || pROFESSORCollectionIdentifiers.includes(pROFESSORIdentifier)) {
          return false;
        }
        pROFESSORCollectionIdentifiers.push(pROFESSORIdentifier);
        return true;
      });
      return [...pROFESSORSToAdd, ...pROFESSORCollection];
    }
    return pROFESSORCollection;
  }
}
