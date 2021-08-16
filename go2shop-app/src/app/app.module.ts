import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CartComponent } from './common/cart/cart.component';
import { OrderComponent } from './common/order/order.component';
import { CatalogueComponent } from './common/catalogue/catalogue.component';
import { PaymentComponent } from './common/payment/payment.component';
import { UserComponent } from './common/user/user.component';
import { CardModule } from 'primeng/card';
import { ButtonModule } from 'primeng/button';

@NgModule({
	declarations: [
		AppComponent,
		CartComponent,
		OrderComponent,
		CatalogueComponent,
		PaymentComponent,
		UserComponent
	],
	imports: [
		BrowserModule,
		AppRoutingModule,
		CardModule,
		ButtonModule
	],
	providers: [],
	bootstrap: [AppComponent]
})
export class AppModule { }
