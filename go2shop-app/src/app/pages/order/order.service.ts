import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { IOrder, IOrderSearchDTO } from './order.model';

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  constructor(private http: HttpClient) { }

  private api = 'api/orderService/order';

  placeOrder(order: IOrder): Observable<HttpResponse<IOrder[]>> {
    return this.http.post<IOrder[]>(`${this.api}/createOrder`, order, { observe: 'response' });
  }

  getOrder(orderId: number): Observable<HttpResponse<IOrder>> {
    return this.http.get<IOrder>(`${this.api}/order/${orderId}`, { observe: 'response' });
  }

  cancelPayment(orderIds: number[]): Observable<HttpResponse<void>> {
    return this.http.post<void>(`${this.api}/cancelPayment`, orderIds, { observe: 'response' });
  }

  confirmPayment(payOption: string, orderIds: number[]): Observable<HttpResponse<void>> {
    return this.http.post<void>(`${this.api}/confirmPayment/${payOption}`, orderIds, { observe: 'response' });
  }

  searchOrders(search: IOrderSearchDTO): Observable<HttpResponse<IOrder[]>> {
    return this.http.post<IOrder[]>(`${this.api}/order/search`, search, { observe: 'response' });
  }

  orderReceived(order: IOrder): Observable<HttpResponse<void>> {
    return this.http.post<void>(`${this.api}/orderReceived`, order, { observe: 'response' });
  }

  confirmDelivery(order: IOrder): Observable<HttpResponse<void>> {
    return this.http.post<void>(`${this.api}/confirmDelivery`, order, { observe: 'response' });
  }
}