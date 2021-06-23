import * as dayjs from 'dayjs';
import { ISALA } from 'app/entities/sala/sala.model';
import { ICONSULTA } from 'app/entities/consulta/consulta.model';
import { IPROFESSOR } from 'app/entities/professor/professor.model';
import { StatusReserva } from 'app/entities/enumerations/status-reserva.model';

export interface IRESERVA {
  id?: number;
  codReserva?: number;
  dataReserva?: dayjs.Dayjs | null;
  horarioInicio?: dayjs.Dayjs | null;
  horarioFinal?: dayjs.Dayjs | null;
  dataSolicitacao?: dayjs.Dayjs | null;
  horarioDaSolicitacao?: dayjs.Dayjs | null;
  statusReservaSala?: StatusReserva | null;
  sALA?: ISALA | null;
  cONSULTA?: ICONSULTA | null;
  pROFESSOR?: IPROFESSOR | null;
}

export class RESERVA implements IRESERVA {
  constructor(
    public id?: number,
    public codReserva?: number,
    public dataReserva?: dayjs.Dayjs | null,
    public horarioInicio?: dayjs.Dayjs | null,
    public horarioFinal?: dayjs.Dayjs | null,
    public dataSolicitacao?: dayjs.Dayjs | null,
    public horarioDaSolicitacao?: dayjs.Dayjs | null,
    public statusReservaSala?: StatusReserva | null,
    public sALA?: ISALA | null,
    public cONSULTA?: ICONSULTA | null,
    public pROFESSOR?: IPROFESSOR | null
  ) {}
}

export function getRESERVAIdentifier(rESERVA: IRESERVA): number | undefined {
  return rESERVA.id;
}
