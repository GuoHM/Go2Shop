import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CatalogueListingComponent } from './catalogue-listing/catalogue-listing.component';
import { ProductInformationComponent } from './product/product-information/product-information.component';
import { CatalogueComponent } from './catalogue.component';
import { ProductResolver } from './catalogue-routing-resolvers';


const routes: Routes = [
  {
    path: '',
    component: CatalogueComponent,
    children: [
      {
        path: '',
        component: CatalogueListingComponent
      },
      {
        path: 'product/:id',
        component: ProductInformationComponent,
        resolve: {
          product: ProductResolver
        }
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CatalogueRoutingModule { }
