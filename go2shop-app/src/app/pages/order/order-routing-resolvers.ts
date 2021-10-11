import { HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { IOrder, Order } from './order.model';
import { OrderService } from './order.service';


@Injectable({ providedIn: 'root' })
export class OrderResolver implements Resolve<IOrder> {
  constructor(private service: OrderService) {}
    
  resolve(route: ActivatedRouteSnapshot ): Observable<IOrder>|Promise<IOrder>| IOrder {
    const id = +route.paramMap.get('id');
    if(id) {
      return this.service.getOrder(id).pipe(
        map((res: HttpResponse<IOrder>) => res.body)
      );
    }
    return new Order();
  }
}