import { CatalogueService } from './catalogue.service';
import { Injectable } from '@angular/core';
import { IProduct, IProductRatings, IProductReview, Product, ProductRatings, ProductReview } from './product.model';
import { Resolve, ActivatedRouteSnapshot } from '@angular/router';
import { forkJoin, Observable, of } from 'rxjs';
import { map, catchError, mergeMap } from 'rxjs/operators';
import { HttpResponse } from '@angular/common/http';

@Injectable({ providedIn: 'root' })
export class ProductResolver implements Resolve<IProduct> {
  constructor(private service: CatalogueService) {}
    
  resolve(route: ActivatedRouteSnapshot ): Observable<IProduct>|Promise<IProduct>| IProduct {
    const id = +route.paramMap.get('id');
    if(id) {
      return this.service.getProduct(id)
        .pipe(
          mergeMap((p: HttpResponse<IProduct>) => {
            return forkJoin(
              [
                of(p), 
                this.service.getProductRatings(id).pipe(
                  catchError(() => of(new ProductRatings()))
                ), 
                this.service.getProductReviews(id).pipe(
                  catchError(() => of([new ProductReview()]))
                )
              ]
            );
          }),
          map((res: [HttpResponse<IProduct>, HttpResponse<IProductRatings>, HttpResponse<IProductReview[]>]) => {
            res[0].body.productRatings = res[1].body;
            res[0].body.productReviews = res[2].body;
            return res[0].body;
          }),
          catchError(() => of(new Product(1)))
        );
    }
    return new Product();
  }
}