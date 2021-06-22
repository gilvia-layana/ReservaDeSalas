import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SALAComponent } from '../list/sala.component';
import { SALADetailComponent } from '../detail/sala-detail.component';
import { SALAUpdateComponent } from '../update/sala-update.component';
import { SALARoutingResolveService } from './sala-routing-resolve.service';

const sALARoute: Routes = [
  {
    path: '',
    component: SALAComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SALADetailComponent,
    resolve: {
      sALA: SALARoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SALAUpdateComponent,
    resolve: {
      sALA: SALARoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SALAUpdateComponent,
    resolve: {
      sALA: SALARoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(sALARoute)],
  exports: [RouterModule],
})
export class SALARoutingModule {}
