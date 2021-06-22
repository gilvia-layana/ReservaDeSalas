import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { SALAComponent } from './list/sala.component';
import { SALADetailComponent } from './detail/sala-detail.component';
import { SALAUpdateComponent } from './update/sala-update.component';
import { SALADeleteDialogComponent } from './delete/sala-delete-dialog.component';
import { SALARoutingModule } from './route/sala-routing.module';

@NgModule({
  imports: [SharedModule, SALARoutingModule],
  declarations: [SALAComponent, SALADetailComponent, SALAUpdateComponent, SALADeleteDialogComponent],
  entryComponents: [SALADeleteDialogComponent],
})
export class SALAModule {}
