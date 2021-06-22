import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { PROFESSORComponent } from './list/professor.component';
import { PROFESSORDetailComponent } from './detail/professor-detail.component';
import { PROFESSORUpdateComponent } from './update/professor-update.component';
import { PROFESSORDeleteDialogComponent } from './delete/professor-delete-dialog.component';
import { PROFESSORRoutingModule } from './route/professor-routing.module';

@NgModule({
  imports: [SharedModule, PROFESSORRoutingModule],
  declarations: [PROFESSORComponent, PROFESSORDetailComponent, PROFESSORUpdateComponent, PROFESSORDeleteDialogComponent],
  entryComponents: [PROFESSORDeleteDialogComponent],
})
export class PROFESSORModule {}
