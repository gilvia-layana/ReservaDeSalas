import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { RESERVAComponent } from '../list/reserva.component';
import { RESERVADetailComponent } from '../detail/reserva-detail.component';
import { RESERVAUpdateComponent } from '../update/reserva-update.component';
import { RESERVARoutingResolveService } from './reserva-routing-resolve.service';

const rESERVARoute: Routes = [
  {
    path: '',
    component: RESERVAComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RESERVADetailComponent,
    resolve: {
      rESERVA: RESERVARoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RESERVAUpdateComponent,
    resolve: {
      rESERVA: RESERVARoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RESERVAUpdateComponent,
    resolve: {
      rESERVA: RESERVARoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(rESERVARoute)],
  exports: [RouterModule],
})
export class RESERVARoutingModule {}
