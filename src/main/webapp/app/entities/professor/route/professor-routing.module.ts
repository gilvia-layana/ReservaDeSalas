import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PROFESSORComponent } from '../list/professor.component';
import { PROFESSORDetailComponent } from '../detail/professor-detail.component';
import { PROFESSORUpdateComponent } from '../update/professor-update.component';
import { PROFESSORRoutingResolveService } from './professor-routing-resolve.service';

const pROFESSORRoute: Routes = [
  {
    path: '',
    component: PROFESSORComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PROFESSORDetailComponent,
    resolve: {
      pROFESSOR: PROFESSORRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PROFESSORUpdateComponent,
    resolve: {
      pROFESSOR: PROFESSORRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PROFESSORUpdateComponent,
    resolve: {
      pROFESSOR: PROFESSORRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(pROFESSORRoute)],
  exports: [RouterModule],
})
export class PROFESSORRoutingModule {}
