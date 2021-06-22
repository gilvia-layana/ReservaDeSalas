import { IDadosPessoais } from 'app/entities/dados-pessoais/dados-pessoais.model';
import { Area } from 'app/entities/enumerations/area.model';

export interface IALUNO {
  id?: number;
  matriculaAluno?: number;
  nome?: string | null;
  areaDeConhecimento?: Area | null;
  curso?: string | null;
  dadosPessoais?: IDadosPessoais[] | null;
}

export class ALUNO implements IALUNO {
  constructor(
    public id?: number,
    public matriculaAluno?: number,
    public nome?: string | null,
    public areaDeConhecimento?: Area | null,
    public curso?: string | null,
    public dadosPessoais?: IDadosPessoais[] | null
  ) {}
}

export function getALUNOIdentifier(aLUNO: IALUNO): number | undefined {
  return aLUNO.id;
}
