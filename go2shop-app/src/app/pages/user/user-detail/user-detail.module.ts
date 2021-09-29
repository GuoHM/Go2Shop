import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProfileComponent } from './profile/profile.component';
import { PurchaseHistoryComponent } from './purchase-history/purchase-history.component';
import { UserDetailRoutingModule } from './user-detail-routing.module';
import { UserDetailComponent } from './user-detail.component';
import { LibrariesModule } from 'app/shared/libraries/libraries.module';
import { CatalogueCreateComponent } from './catalogue-create/catalogue-create.component';

@NgModule({
  declarations: [
    ProfileComponent,
    PurchaseHistoryComponent,
    UserDetailComponent,
    CatalogueCreateComponent
  ],
  imports: [
    CommonModule,
    UserDetailRoutingModule,
    LibrariesModule
  ],
  bootstrap: [UserDetailComponent]
})
export class UserDetailModule { }
