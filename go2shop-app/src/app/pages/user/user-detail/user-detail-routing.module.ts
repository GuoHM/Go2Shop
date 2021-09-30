import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CatalogueCreateComponent } from './catalogue-create/catalogue-create.component';
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
      {
        path: 'seller/catalogue/create',
        component: CatalogueCreateComponent
      },
    ] 
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UserDetailRoutingModule { }
