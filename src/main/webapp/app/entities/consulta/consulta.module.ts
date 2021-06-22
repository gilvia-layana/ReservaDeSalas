import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { CONSULTAComponent } from './list/consulta.component';
import { CONSULTADetailComponent } from './detail/consulta-detail.component';
import { CONSULTAUpdateComponent } from './update/consulta-update.component';
import { CONSULTADeleteDialogComponent } from './delete/consulta-delete-dialog.component';
import { CONSULTARoutingModule } from './route/consulta-routing.module';

@NgModule({
  imports: [SharedModule, CONSULTARoutingModule],
  declarations: [CONSULTAComponent, CONSULTADetailComponent, CONSULTAUpdateComponent, CONSULTADeleteDialogComponent],
  entryComponents: [CONSULTADeleteDialogComponent],
})
export class CONSULTAModule {}
