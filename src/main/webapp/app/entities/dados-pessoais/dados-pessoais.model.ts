import { IALUNO } from 'app/entities/aluno/aluno.model';
import { IPROFESSOR } from 'app/entities/professor/professor.model';

export interface IDadosPessoais {
  id?: number;
  endereco?: string | null;
  telefone?: string | null;
  email?: string | null;
  aLUNO?: IALUNO | null;
  pROFESSOR?: IPROFESSOR | null;
}

export class DadosPessoais implements IDadosPessoais {
  constructor(
    public id?: number,
    public endereco?: string | null,
    public telefone?: string | null,
    public email?: string | null,
    public aLUNO?: IALUNO | null,
    public pROFESSOR?: IPROFESSOR | null
  ) {}
}

export function getDadosPessoaisIdentifier(dadosPessoais: IDadosPessoais): number | undefined {
  return dadosPessoais.id;
}
