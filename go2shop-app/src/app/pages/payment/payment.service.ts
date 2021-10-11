import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';


@Injectable({
  providedIn: 'root'
})
export class PaymentService {
  private api = 'api/catalogueService/catalogue';
  
  constructor(private http: HttpClient) {}
}