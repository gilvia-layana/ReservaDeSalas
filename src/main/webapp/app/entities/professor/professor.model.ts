import { IDadosPessoais } from 'app/entities/dados-pessoais/dados-pessoais.model';
import { IRESERVA } from 'app/entities/reserva/reserva.model';

export interface IPROFESSOR {
  id?: number;
  matriculaProf?: number;
  nome?: string | null;
  disciplina?: string | null;
  faculdade?: string | null;
  dadosPessoais?: IDadosPessoais[] | null;
  pROFESSORS?: IRESERVA[] | null;
  pROFESSORS?: IRESERVA[] | null;
}

export class PROFESSOR implements IPROFESSOR {
  constructor(
    public id?: number,
    public matriculaProf?: number,
    public nome?: string | null,
    public disciplina?: string | null,
    public faculdade?: string | null,
    public dadosPessoais?: IDadosPessoais[] | null,
    public pROFESSORS?: IRESERVA[] | null,
    public pROFESSORS?: IRESERVA[] | null
  ) {}
}

export function getPROFESSORIdentifier(pROFESSOR: IPROFESSOR): number | undefined {
  return pROFESSOR.id;
}
