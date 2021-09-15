import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CatalogueListingComponent } from './catalogue-listing/catalogue-listing.component';
import { CatalogueRoutingModule } from './catalogue-routing.module';
import { LibrariesModule } from 'app/shared/libraries/libraries.module';
import { ProductComponent } from './product/product.component';
import { ProductInformationComponent } from './product/product-information/product-information.component';
import { CatalogueComponent } from './catalogue.component';



@NgModule({
  declarations: [
    CatalogueComponent,
    CatalogueListingComponent,
    ProductComponent,
    ProductInformationComponent
  ],
  imports: [
    CommonModule,
    LibrariesModule,
    CatalogueRoutingModule
  ]
})
export class CatalogueModule { }
