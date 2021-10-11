import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PaymentRoutingModule } from './payment-routing.module';
import { PaymentComponent } from './payment.component';
import { LibrariesModule } from 'app/shared/libraries/libraries.module';



@NgModule({
  declarations: [
    PaymentComponent
  ],
  imports: [
    CommonModule,
    LibrariesModule,
    PaymentRoutingModule
  ]
})
export class PaymentModule { }
