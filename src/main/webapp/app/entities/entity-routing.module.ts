import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'sala',
        data: { pageTitle: 'reservaApp.sALA.home.title' },
        loadChildren: () => import('./sala/sala.module').then(m => m.SALAModule),
      },
      {
        path: 'dados-pessoais',
        data: { pageTitle: 'reservaApp.dadosPessoais.home.title' },
        loadChildren: () => import('./dados-pessoais/dados-pessoais.module').then(m => m.DadosPessoaisModule),
      },
      {
        path: 'professor',
        data: { pageTitle: 'reservaApp.pROFESSOR.home.title' },
        loadChildren: () => import('./professor/professor.module').then(m => m.PROFESSORModule),
      },
      {
        path: 'aluno',
        data: { pageTitle: 'reservaApp.aLUNO.home.title' },
        loadChildren: () => import('./aluno/aluno.module').then(m => m.ALUNOModule),
      },
      {
        path: 'reserva',
        data: { pageTitle: 'reservaApp.rESERVA.home.title' },
        loadChildren: () => import('./reserva/reserva.module').then(m => m.RESERVAModule),
      },
      {
        path: 'consulta',
        data: { pageTitle: 'reservaApp.cONSULTA.home.title' },
        loadChildren: () => import('./consulta/consulta.module').then(m => m.CONSULTAModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
