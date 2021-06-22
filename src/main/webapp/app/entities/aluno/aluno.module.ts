import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { ALUNOComponent } from './list/aluno.component';
import { ALUNODetailComponent } from './detail/aluno-detail.component';
import { ALUNOUpdateComponent } from './update/aluno-update.component';
import { ALUNODeleteDialogComponent } from './delete/aluno-delete-dialog.component';
import { ALUNORoutingModule } from './route/aluno-routing.module';

@NgModule({
  imports: [SharedModule, ALUNORoutingModule],
  declarations: [ALUNOComponent, ALUNODetailComponent, ALUNOUpdateComponent, ALUNODeleteDialogComponent],
  entryComponents: [ALUNODeleteDialogComponent],
})
export class ALUNOModule {}
