import { constants } from './constants';
import { environment } from '../../environments/environment';
import { Usuario } from '../model/usuario.model';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { throwError } from 'rxjs';
import { Questao } from '../model/questao.model';
import { ServiceBase } from './service-base.service';

@Injectable({
  providedIn: 'root',
})
export class QuestaoService extends ServiceBase<Questao> {
  getURL(): string {
    return constants.QUESTOES_API;
  }
}
