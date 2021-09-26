import { HttpClient, HttpResponse } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { IShoppingCartProduct } from "./shopping-cart-product.model";
import { IShoppingCart, ShoppingCart } from "./shopping-cart.model";

@Injectable({
    providedIn: 'root'
})
export class ShoppingCartService {
    private api = 'api/shoppingCartService/shoppingCart';

    constructor(private http: HttpClient) {}
    
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

    updateQuantity(productID: number, productQuantity: number): Observable<HttpResponse<IShoppingCartProduct>> {
        return this.http.post<IShoppingCartProduct>(`${this.api}/shoppingCartProduct/update/${productID}`, productQuantity,
        { observe: 'response' });
    }
}