import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { createRequestOption } from 'app/shared/utils/request-util';
import { BehaviorSubject, Observable } from 'rxjs';
import { IProduct, IProductRatings, IProductReview, IProductSearch } from './product.model';

@Injectable({
  providedIn: 'root'
})
export class CatalogueService {
  private api = 'api/catalogueService/catalogue';

  private searchTermSubject: BehaviorSubject<IProductSearch> = new BehaviorSubject(null);
  
  constructor(private http: HttpClient) {}

  getSearchBarListener(): Observable<IProductSearch> {
    return this.searchTermSubject.asObservable();
  }

  updateSearchDTO(newSearch: IProductSearch): void {
    this.searchTermSubject.next(newSearch);
  }

  getCatalogue(): Observable<HttpResponse<IProduct[]>> {
    return this.http.get<IProduct[]>(`${this.api}/catalogue`, { observe: 'response' });
  }

  getProduct(id: number): Observable<HttpResponse<IProduct>> {
    return this.http.get<IProduct>(`${this.api}/product/${id}`, { observe: 'response' });
  }

  search(productSearch: IProductSearch, req?: any): Observable<HttpResponse<IProduct[]>> {
    const options = createRequestOption(req);
    return this.http.post<IProduct[]>(`${this.api}/catalogue/search`, productSearch, { params: options, observe: 'response' });
  }

  getProductRatings(id: number): Observable<HttpResponse<IProductRatings>> {
    return this.http.get<IProductRatings>(`${this.api}/product/ratings/${id}`, { observe: 'response' });
  }

  getProductReviews(id: number, req?: any): Observable<HttpResponse<IProductReview[]>> {
    const options = createRequestOption(req);
    return this.http.get<IProductReview[]>(`${this.api}/product/reviews/${id}`, { params: options, observe: 'response' });
  }

  addProductReview(review: IProductReview): Observable<HttpResponse<IProductReview>> {
    return this.http.post<IProductReview>(`${this.api}/addProductReview`, review, { observe: 'response' });
  }

  getRecommendedProducts(req?: any): Observable<HttpResponse<any[]>> {
    const options = createRequestOption(req);
    return this.http.get<any[]>(`${this.api}/product/getRecommendedProducts`, { params: options, observe: 'response' });
  }
}