import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CatalogueCreate } from './catalogue-create.model';


@Injectable({
  providedIn: 'root'
})
export class CatalogueCreateService {

  constructor(private http: HttpClient) { }

  uploadImage(fileToUpload: any): Observable<HttpResponse<any>> {
    const formData: FormData = new FormData();
    formData.append('image', fileToUpload);
    return this.http.post<any>('/api/catalogueService/catalogue/image', formData,
      { observe: 'response' });
  }

  createCatalogue(catalogueCreate: CatalogueCreate): Observable<HttpResponse<CatalogueCreate>> {
    return this.http.post<CatalogueCreate>('/api/catalogueService/catalogue/catalogue', catalogueCreate,
      { observe: 'response' });
  }

}