import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CONSULTAComponent } from '../list/consulta.component';
import { CONSULTADetailComponent } from '../detail/consulta-detail.component';
import { CONSULTAUpdateComponent } from '../update/consulta-update.component';
import { CONSULTARoutingResolveService } from './consulta-routing-resolve.service';

const cONSULTARoute: Routes = [
  {
    path: '',
    component: CONSULTAComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CONSULTADetailComponent,
    resolve: {
      cONSULTA: CONSULTARoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CONSULTAUpdateComponent,
    resolve: {
      cONSULTA: CONSULTARoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CONSULTAUpdateComponent,
    resolve: {
      cONSULTA: CONSULTARoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(cONSULTARoute)],
  exports: [RouterModule],
})
export class CONSULTARoutingModule {}
