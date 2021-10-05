import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { IShoppingCartProduct, IUserToProduct } from './cart.model';

@Injectable({
  providedIn: 'root'
})
export class CartService {
    private api = 'api/cartService/cart/demo';

    private updateCartSizeSubj = new Subject();
    updateCartSizeObs = this.updateCartSizeSubj.asObservable();

    constructor(private http: HttpClient) {}
    
    updateCartSize(): void {
      this.updateCartSizeSubj.next();
    }

    getCartSize(userId: number): Observable<HttpResponse<number>> {
      return this.http.get<number>(`${this.api}/cart/size/${userId}`, { observe: 'response' });
    }
    
    addProductToCart(userToProduct: IUserToProduct): Observable<HttpResponse<IShoppingCartProduct>> {
      return this.http.post<IShoppingCartProduct>(`${this.api}/addProductToCart`, userToProduct, { observe: 'response' });
    }
}