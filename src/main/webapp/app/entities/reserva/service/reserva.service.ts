import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IRESERVA, getRESERVAIdentifier } from '../reserva.model';

export type EntityResponseType = HttpResponse<IRESERVA>;
export type EntityArrayResponseType = HttpResponse<IRESERVA[]>;

@Injectable({ providedIn: 'root' })
export class RESERVAService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/reservas');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(rESERVA: IRESERVA): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(rESERVA);
    return this.http
      .post<IRESERVA>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(rESERVA: IRESERVA): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(rESERVA);
    return this.http
      .put<IRESERVA>(`${this.resourceUrl}/${getRESERVAIdentifier(rESERVA) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(rESERVA: IRESERVA): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(rESERVA);
    return this.http
      .patch<IRESERVA>(`${this.resourceUrl}/${getRESERVAIdentifier(rESERVA) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IRESERVA>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IRESERVA[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addRESERVAToCollectionIfMissing(rESERVACollection: IRESERVA[], ...rESERVASToCheck: (IRESERVA | null | undefined)[]): IRESERVA[] {
    const rESERVAS: IRESERVA[] = rESERVASToCheck.filter(isPresent);
    if (rESERVAS.length > 0) {
      const rESERVACollectionIdentifiers = rESERVACollection.map(rESERVAItem => getRESERVAIdentifier(rESERVAItem)!);
      const rESERVASToAdd = rESERVAS.filter(rESERVAItem => {
        const rESERVAIdentifier = getRESERVAIdentifier(rESERVAItem);
        if (rESERVAIdentifier == null || rESERVACollectionIdentifiers.includes(rESERVAIdentifier)) {
          return false;
        }
        rESERVACollectionIdentifiers.push(rESERVAIdentifier);
        return true;
      });
      return [...rESERVASToAdd, ...rESERVACollection];
    }
    return rESERVACollection;
  }

  protected convertDateFromClient(rESERVA: IRESERVA): IRESERVA {
    return Object.assign({}, rESERVA, {
      dataReserva: rESERVA.dataReserva?.isValid() ? rESERVA.dataReserva.format(DATE_FORMAT) : undefined,
      horarioInicio: rESERVA.horarioInicio?.isValid() ? rESERVA.horarioInicio.toJSON() : undefined,
      horarioFinal: rESERVA.horarioFinal?.isValid() ? rESERVA.horarioFinal.toJSON() : undefined,
      dataSolicitacao: rESERVA.dataSolicitacao?.isValid() ? rESERVA.dataSolicitacao.toJSON() : undefined,
      horarioDaSolicitacao: rESERVA.horarioDaSolicitacao?.isValid() ? rESERVA.horarioDaSolicitacao.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dataReserva = res.body.dataReserva ? dayjs(res.body.dataReserva) : undefined;
      res.body.horarioInicio = res.body.horarioInicio ? dayjs(res.body.horarioInicio) : undefined;
      res.body.horarioFinal = res.body.horarioFinal ? dayjs(res.body.horarioFinal) : undefined;
      res.body.dataSolicitacao = res.body.dataSolicitacao ? dayjs(res.body.dataSolicitacao) : undefined;
      res.body.horarioDaSolicitacao = res.body.horarioDaSolicitacao ? dayjs(res.body.horarioDaSolicitacao) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((rESERVA: IRESERVA) => {
        rESERVA.dataReserva = rESERVA.dataReserva ? dayjs(rESERVA.dataReserva) : undefined;
        rESERVA.horarioInicio = rESERVA.horarioInicio ? dayjs(rESERVA.horarioInicio) : undefined;
        rESERVA.horarioFinal = rESERVA.horarioFinal ? dayjs(rESERVA.horarioFinal) : undefined;
        rESERVA.dataSolicitacao = rESERVA.dataSolicitacao ? dayjs(rESERVA.dataSolicitacao) : undefined;
        rESERVA.horarioDaSolicitacao = rESERVA.horarioDaSolicitacao ? dayjs(rESERVA.horarioDaSolicitacao) : undefined;
      });
    }
    return res;
  }
}
