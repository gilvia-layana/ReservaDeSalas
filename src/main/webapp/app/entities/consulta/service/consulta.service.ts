import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
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
    const copy = this.convertDateFromClient(cONSULTA);
    return this.http
      .post<ICONSULTA>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(cONSULTA: ICONSULTA): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(cONSULTA);
    return this.http
      .put<ICONSULTA>(`${this.resourceUrl}/${getCONSULTAIdentifier(cONSULTA) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(cONSULTA: ICONSULTA): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(cONSULTA);
    return this.http
      .patch<ICONSULTA>(`${this.resourceUrl}/${getCONSULTAIdentifier(cONSULTA) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICONSULTA>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICONSULTA[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
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

  protected convertDateFromClient(cONSULTA: ICONSULTA): ICONSULTA {
    return Object.assign({}, cONSULTA, {
      dataDaConsulta: cONSULTA.dataDaConsulta?.isValid() ? cONSULTA.dataDaConsulta.format(DATE_FORMAT) : undefined,
      horarioDaConsulta: cONSULTA.horarioDaConsulta?.isValid() ? cONSULTA.horarioDaConsulta.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dataDaConsulta = res.body.dataDaConsulta ? dayjs(res.body.dataDaConsulta) : undefined;
      res.body.horarioDaConsulta = res.body.horarioDaConsulta ? dayjs(res.body.horarioDaConsulta) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((cONSULTA: ICONSULTA) => {
        cONSULTA.dataDaConsulta = cONSULTA.dataDaConsulta ? dayjs(cONSULTA.dataDaConsulta) : undefined;
        cONSULTA.horarioDaConsulta = cONSULTA.horarioDaConsulta ? dayjs(cONSULTA.horarioDaConsulta) : undefined;
      });
    }
    return res;
  }
}
