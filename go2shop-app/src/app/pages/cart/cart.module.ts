import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ShoppingCartComponent } from './shopping-cart/shopping-cart.component';
import { LibrariesModule } from 'app/shared/libraries/libraries.module';
import { CartRoutingModule } from './cart-routing.module';
import { AuthenticationModule } from 'app/auth/authentication.module';
import { CatalogueModule } from '../catalogue/catalogue.module';



@NgModule({
  declarations: [
    ShoppingCartComponent
  ],
  imports: [
    CommonModule,
    LibrariesModule,
    CartRoutingModule,
    AuthenticationModule,
    CatalogueModule
  ]
})
export class CartModule { }
