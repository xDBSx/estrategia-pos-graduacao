import { ModelBase } from './model-base.model';
export class Questao implements ModelBase {
  id: number;
  texto: string;
  resposta: string;
}
