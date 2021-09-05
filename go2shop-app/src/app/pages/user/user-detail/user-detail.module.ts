import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProfileComponent } from './profile/profile.component';
import { PurchaseHistoryComponent } from './purchase-history/purchase-history.component';
import { UserDetailRoutingModule } from './user-detail-routing.module';
import { UserDetailComponent } from './user-detail.component';
import { LibrariesModule } from 'app/shared/libraries/libraries.module';

@NgModule({
  declarations: [
    ProfileComponent,
    PurchaseHistoryComponent,
    UserDetailComponent
  ],
  imports: [
    CommonModule,
    UserDetailRoutingModule,
    LibrariesModule
  ],
  bootstrap: [UserDetailComponent]
})
export class UserDetailModule { }
