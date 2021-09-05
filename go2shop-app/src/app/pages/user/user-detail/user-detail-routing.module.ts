import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ProfileComponent } from './profile/profile.component';
import { PurchaseHistoryComponent } from './purchase-history/purchase-history.component';
import { UserDetailComponent } from './user-detail.component';


const routes: Routes = [
  { path: '', 
    component: UserDetailComponent,
    children: [
      {
        path: 'profile',
        component: ProfileComponent
      },
      {
        path: 'purchase/history',
        component: PurchaseHistoryComponent
      },
    ] 
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UserDetailRoutingModule { }
