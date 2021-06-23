import { IDadosPessoais } from 'app/entities/dados-pessoais/dados-pessoais.model';

export interface IPROFESSOR {
  id?: number;
  matriculaProf?: number;
  nome?: string | null;
  disciplina?: string | null;
  faculdade?: string | null;
  dadosPessoais?: IDadosPessoais[] | null;
}

export class PROFESSOR implements IPROFESSOR {
  constructor(
    public id?: number,
    public matriculaProf?: number,
    public nome?: string | null,
    public disciplina?: string | null,
    public faculdade?: string | null,
    public dadosPessoais?: IDadosPessoais[] | null
  ) {}
}

export function getPROFESSORIdentifier(pROFESSOR: IPROFESSOR): number | undefined {
  return pROFESSOR.id;
}
