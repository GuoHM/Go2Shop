import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, Subject } from 'rxjs';
import { IShoppingCartProduct, ShoppingCartProduct } from './shopping-cart-product.model';
import { IShoppingCart } from './shopping-cart.model';

@Injectable({
  providedIn: 'root'
})
export class ShoppingCartService {
    private updateCartSizeSubj = new Subject();
    updateCartSizeObs = this.updateCartSizeSubj.asObservable();

    private buyNowSubj = new BehaviorSubject<boolean>(false);
    buyNowObs = this.buyNowSubj.asObservable();
    buyNowProduct: ShoppingCartProduct;
    
    private api = 'api/cartService/shoppingCart';

    constructor(private http: HttpClient) {}

    updateShoppingCartState(isBuyNow: boolean, product?: ShoppingCartProduct): void {
      this.buyNowSubj.next(isBuyNow);
      this.buyNowProduct = product;
    }

    updateCartSize(): void {
      this.updateCartSizeSubj.next();
    }

    getCartSize(userId: number): Observable<HttpResponse<number>> {
      return this.http.get<number>(`${this.api}/cart/size/${userId}`, { observe: 'response' });
    }

    createShoppingCart(shoppingCartDTO: IShoppingCart): Observable<HttpResponse<IShoppingCart>> {
      return this.http.post<IShoppingCart>(`${this.api}/shoppingCart/create`, shoppingCartDTO,
        { observe: 'response' });
    }

    createShoppingCartProduct(shoppingCartProductDTO: IShoppingCartProduct): Observable<HttpResponse<IShoppingCartProduct>> {
      return this.http.post<IShoppingCartProduct>(`${this.api}/shoppingCartProduct/create`, shoppingCartProductDTO, 
        { observe: 'response' });
    }

    getShoppingCart(userID: number): Observable<HttpResponse<IShoppingCart>> {
      return this.http.get<IShoppingCart>(`${this.api}/shoppingCart/${userID}`, { observe: 'response' });
    }

    getShoppingCartProduct(shoppingCartProductID: number): Observable<HttpResponse<IShoppingCartProduct>> {
      return this.http.get<IShoppingCartProduct>(`${this.api}/shoppingCartProduct/product/${shoppingCartProductID}`, 
        { observe: 'response' });
    }

    getAllShoppingCartProduct(shoppingCartID: number): Observable<HttpResponse<IShoppingCartProduct[]>> {
      return this.http.get<IShoppingCartProduct[]>(`${this.api}/shoppingCartProduct/shoppingCart/${shoppingCartID}`, 
        { observe: 'response' });
    }

    deleteAllProduct(shoppingCartID: number): Observable<any> {
      return this.http.post<void>(`${this.api}/shoppingCartProduct/delete`, shoppingCartID,
        { observe: 'response' });
    }

    deleteShoppingCartProduct(shoppingCartProductID: number): Observable<any> {
      return this.http.post<void>(`${this.api}/shoppingCartProduct/delete/${shoppingCartProductID}`,
        { observe: 'response' });
    }

    updateQuantity(productID: number, productQuantity: number, shoppingCartID: number): Observable<HttpResponse<IShoppingCartProduct>> {
      return this.http.put<IShoppingCartProduct>
      (`${this.api}/shoppingCartProduct/update/${productID}/${shoppingCartID}/${productQuantity}`, 
        {productID, productQuantity, shoppingCartID}, 
        {observe: 'response'});
    }
}