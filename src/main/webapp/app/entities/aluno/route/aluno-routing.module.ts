import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ALUNOComponent } from '../list/aluno.component';
import { ALUNODetailComponent } from '../detail/aluno-detail.component';
import { ALUNOUpdateComponent } from '../update/aluno-update.component';
import { ALUNORoutingResolveService } from './aluno-routing-resolve.service';

const aLUNORoute: Routes = [
  {
    path: '',
    component: ALUNOComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ALUNODetailComponent,
    resolve: {
      aLUNO: ALUNORoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ALUNOUpdateComponent,
    resolve: {
      aLUNO: ALUNORoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ALUNOUpdateComponent,
    resolve: {
      aLUNO: ALUNORoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(aLUNORoute)],
  exports: [RouterModule],
})
export class ALUNORoutingModule {}
