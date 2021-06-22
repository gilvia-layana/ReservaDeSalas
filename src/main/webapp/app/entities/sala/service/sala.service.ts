import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISALA, getSALAIdentifier } from '../sala.model';

export type EntityResponseType = HttpResponse<ISALA>;
export type EntityArrayResponseType = HttpResponse<ISALA[]>;

@Injectable({ providedIn: 'root' })
export class SALAService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/salas');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(sALA: ISALA): Observable<EntityResponseType> {
    return this.http.post<ISALA>(this.resourceUrl, sALA, { observe: 'response' });
  }

  update(sALA: ISALA): Observable<EntityResponseType> {
    return this.http.put<ISALA>(`${this.resourceUrl}/${getSALAIdentifier(sALA) as number}`, sALA, { observe: 'response' });
  }

  partialUpdate(sALA: ISALA): Observable<EntityResponseType> {
    return this.http.patch<ISALA>(`${this.resourceUrl}/${getSALAIdentifier(sALA) as number}`, sALA, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISALA>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISALA[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addSALAToCollectionIfMissing(sALACollection: ISALA[], ...sALASToCheck: (ISALA | null | undefined)[]): ISALA[] {
    const sALAS: ISALA[] = sALASToCheck.filter(isPresent);
    if (sALAS.length > 0) {
      const sALACollectionIdentifiers = sALACollection.map(sALAItem => getSALAIdentifier(sALAItem)!);
      const sALASToAdd = sALAS.filter(sALAItem => {
        const sALAIdentifier = getSALAIdentifier(sALAItem);
        if (sALAIdentifier == null || sALACollectionIdentifiers.includes(sALAIdentifier)) {
          return false;
        }
        sALACollectionIdentifiers.push(sALAIdentifier);
        return true;
      });
      return [...sALASToAdd, ...sALACollection];
    }
    return sALACollection;
  }
}
