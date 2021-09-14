import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { IProduct } from './product.model';

@Injectable({
    providedIn: 'root'
})
export class CatalogueService {
    private api = 'api/catalogueService/catalogue';

    constructor(private http: HttpClient) {}

    getCatalogue(): Observable<HttpResponse<IProduct[]>> {
        return this.http.get<IProduct[]>(`${this.api}/catalogue`, { observe: 'response' });
    }

    getProduct(id: number): Observable<HttpResponse<IProduct>> {
        return this.http.get<IProduct>(`${this.api}/product/${id}`, { observe: 'response' });
    }
}