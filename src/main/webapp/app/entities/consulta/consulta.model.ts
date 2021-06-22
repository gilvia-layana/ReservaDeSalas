import { IALUNO } from 'app/entities/aluno/aluno.model';

export interface ICONSULTA {
  id?: number;
  nomeAluno?: string | null;
  codConsulta?: number;
  aLUNO?: IALUNO | null;
}

export class CONSULTA implements ICONSULTA {
  constructor(public id?: number, public nomeAluno?: string | null, public codConsulta?: number, public aLUNO?: IALUNO | null) {}
}

export function getCONSULTAIdentifier(cONSULTA: ICONSULTA): number | undefined {
  return cONSULTA.id;
}
