import * as dayjs from 'dayjs';
import { IALUNO } from 'app/entities/aluno/aluno.model';

export interface ICONSULTA {
  id?: number;
  codConsulta?: number;
  dataDaConsulta?: dayjs.Dayjs | null;
  horarioDaConsulta?: dayjs.Dayjs | null;
  aLUNO?: IALUNO | null;
}

export class CONSULTA implements ICONSULTA {
  constructor(
    public id?: number,
    public codConsulta?: number,
    public dataDaConsulta?: dayjs.Dayjs | null,
    public horarioDaConsulta?: dayjs.Dayjs | null,
    public aLUNO?: IALUNO | null
  ) {}
}

export function getCONSULTAIdentifier(cONSULTA: ICONSULTA): number | undefined {
  return cONSULTA.id;
}
