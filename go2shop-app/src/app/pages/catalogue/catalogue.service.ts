import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { createRequestOption } from 'app/shared/utils/request-util';
import { BehaviorSubject, Observable } from 'rxjs';
import { IProduct, IProductSearch } from './product.model';

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
}