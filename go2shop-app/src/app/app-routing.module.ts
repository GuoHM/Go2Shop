import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

const routes: Routes = [
  {
    path: '', redirectTo: '/catalogue', pathMatch: 'full'
  },
  {
    path: 'catalogue', loadChildren: () => import('app/pages/catalogue/catalogue.module').then(m => m.CatalogueModule)
  },
  {
    path: 'cart', loadChildren: () => import('app/pages/cart/cart.module').then(m => m.CartModule)
  },
  {
    path: 'payment', loadChildren: () => import('app/pages/payment/payment.module').then(m => m.PaymentModule)
  },
  {
    path: 'user', loadChildren: () => import('app/pages/user/user.module').then(m => m.UserModule)
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }