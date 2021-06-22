import { StatusSala } from 'app/entities/enumerations/status-sala.model';

export interface ISALA {
  id?: number;
  codSala?: number;
  nome?: string | null;
  local?: string | null;
  status?: StatusSala | null;
}

export class SALA implements ISALA {
  constructor(
    public id?: number,
    public codSala?: number,
    public nome?: string | null,
    public local?: string | null,
    public status?: StatusSala | null
  ) {}
}

export function getSALAIdentifier(sALA: ISALA): number | undefined {
  return sALA.id;
}
