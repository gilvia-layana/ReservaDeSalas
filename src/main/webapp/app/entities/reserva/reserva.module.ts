import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { RESERVAComponent } from './list/reserva.component';
import { RESERVADetailComponent } from './detail/reserva-detail.component';
import { RESERVAUpdateComponent } from './update/reserva-update.component';
import { RESERVADeleteDialogComponent } from './delete/reserva-delete-dialog.component';
import { RESERVARoutingModule } from './route/reserva-routing.module';

@NgModule({
  imports: [SharedModule, RESERVARoutingModule],
  declarations: [RESERVAComponent, RESERVADetailComponent, RESERVAUpdateComponent, RESERVADeleteDialogComponent],
  entryComponents: [RESERVADeleteDialogComponent],
})
export class RESERVAModule {}
