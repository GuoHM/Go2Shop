import { CatalogueService } from './catalogue.service';
import { Injectable } from '@angular/core';
import { IProduct, Product } from './product.model';
import { Resolve, ActivatedRouteSnapshot } from '@angular/router';
import { Observable, of } from 'rxjs';
import { map, catchError } from 'rxjs/operators';

@Injectable({ providedIn: 'root' })
export class ProductResolver implements Resolve<IProduct> {
  constructor(private service: CatalogueService) {}
    
  resolve(route: ActivatedRouteSnapshot ): Observable<IProduct>|Promise<IProduct>| IProduct {
    const id = +route.paramMap.get('id');
    if(id) {
      return this.service.getProduct(id).pipe(map(p => p.body), catchError(() => of(new Product(1))));
    }
    return new Product();
  }
}