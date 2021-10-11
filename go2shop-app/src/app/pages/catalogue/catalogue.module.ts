import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CatalogueListingComponent } from './catalogue-listing/catalogue-listing.component';
import { CatalogueRoutingModule } from './catalogue-routing.module';
import { LibrariesModule } from 'app/shared/libraries/libraries.module';
import { ProductComponent } from './product/product.component';
import { ProductInformationComponent } from './product/product-information/product-information.component';
import { CatalogueComponent } from './catalogue.component';
import { ProductReviewComponent } from './product/product-review/product-review.component';
import { AddProductReviewComponent } from './product/product-review/add-product-review/add-product-review.component';



@NgModule({
  declarations: [
    CatalogueComponent,
    CatalogueListingComponent,
    ProductComponent,
    ProductInformationComponent,
    ProductReviewComponent,
    AddProductReviewComponent
  ],
  imports: [
    CommonModule,
    LibrariesModule,
    CatalogueRoutingModule
  ]
})
export class CatalogueModule { }
