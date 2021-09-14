import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ShoppingCartComponent } from './shopping-cart/shopping-cart.component';
import { LibrariesModule } from 'app/shared/libraries/libraries.module';
import { CartRoutingModule } from './cart-routing.module';



@NgModule({
  declarations: [
    ShoppingCartComponent
  ],
  imports: [
    CommonModule,
    LibrariesModule,
    CartRoutingModule
  ]
})
export class CartModule { }
