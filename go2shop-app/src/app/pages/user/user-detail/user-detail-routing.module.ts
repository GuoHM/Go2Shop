import { Injectable, NgModule } from '@angular/core';
import { Routes, RouterModule, CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { AuthenticationService } from 'app/auth/authentication.service';
import { Observable } from 'rxjs';
import { CatalogueCreateComponent } from './catalogue-create/catalogue-create.component';
import { ProfileComponent } from './profile/profile.component';
import { PurchaseHistoryComponent } from './purchase-history/purchase-history.component';
import { SalesHistoryComponent } from './sales-history/sales-history.component';
import { UserDetailComponent } from './user-detail.component';

@Injectable({
  providedIn: 'root'
})
export class SellerAuthGuard implements CanActivate {

  constructor(private router: Router, private authService: AuthenticationService) {}

  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> | boolean {
    if(!this.authService.hasRole('SELLER')) {
      this.router.navigate(['/user', 'detail']);
      return false;
    }
    return true;
  }
}

@Injectable({
  providedIn: 'root'
})
export class BuyerAuthGuard implements CanActivate {

  constructor(private router: Router, private authService: AuthenticationService) {}

  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> | boolean {
    if(!this.authService.hasRole('BUYER')) {
      this.router.navigate(['/user', 'detail']);
      return false;
    }
    return true;
  }
}

const routes: Routes = [
  { path: '', 
    component: UserDetailComponent,
    children: [
      {
        path: '',
        component: ProfileComponent
      },
      {
        path: 'profile',
        component: ProfileComponent
      },
      {
        path: 'purchase/history',
        component: PurchaseHistoryComponent,
        canActivate: [BuyerAuthGuard]
      },
      {
        path: 'seller/catalogue/create',
        component: CatalogueCreateComponent,
        canActivate: [SellerAuthGuard]
      },
      {
        path: 'sales/history',
        component: SalesHistoryComponent,
        canActivate: [SellerAuthGuard]
      }
    ] 
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UserDetailRoutingModule { }
