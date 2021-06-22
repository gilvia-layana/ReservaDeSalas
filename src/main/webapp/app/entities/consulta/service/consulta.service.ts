import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICONSULTA, getCONSULTAIdentifier } from '../consulta.model';

export type EntityResponseType = HttpResponse<ICONSULTA>;
export type EntityArrayResponseType = HttpResponse<ICONSULTA[]>;

@Injectable({ providedIn: 'root' })
export class CONSULTAService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/consultas');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(cONSULTA: ICONSULTA): Observable<EntityResponseType> {
    return this.http.post<ICONSULTA>(this.resourceUrl, cONSULTA, { observe: 'response' });
  }

  update(cONSULTA: ICONSULTA): Observable<EntityResponseType> {
    return this.http.put<ICONSULTA>(`${this.resourceUrl}/${getCONSULTAIdentifier(cONSULTA) as number}`, cONSULTA, { observe: 'response' });
  }

  partialUpdate(cONSULTA: ICONSULTA): Observable<EntityResponseType> {
    return this.http.patch<ICONSULTA>(`${this.resourceUrl}/${getCONSULTAIdentifier(cONSULTA) as number}`, cONSULTA, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICONSULTA>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICONSULTA[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addCONSULTAToCollectionIfMissing(cONSULTACollection: ICONSULTA[], ...cONSULTASToCheck: (ICONSULTA | null | undefined)[]): ICONSULTA[] {
    const cONSULTAS: ICONSULTA[] = cONSULTASToCheck.filter(isPresent);
    if (cONSULTAS.length > 0) {
      const cONSULTACollectionIdentifiers = cONSULTACollection.map(cONSULTAItem => getCONSULTAIdentifier(cONSULTAItem)!);
      const cONSULTASToAdd = cONSULTAS.filter(cONSULTAItem => {
        const cONSULTAIdentifier = getCONSULTAIdentifier(cONSULTAItem);
        if (cONSULTAIdentifier == null || cONSULTACollectionIdentifiers.includes(cONSULTAIdentifier)) {
          return false;
        }
        cONSULTACollectionIdentifiers.push(cONSULTAIdentifier);
        return true;
      });
      return [...cONSULTASToAdd, ...cONSULTACollection];
    }
    return cONSULTACollection;
  }
}
