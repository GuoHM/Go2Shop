import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { IUserLogin } from './user.model';
import { HttpClient, HttpHeaders, HttpParams, HttpResponse } from '@angular/common/http';
import { IUserToken } from 'app/auth/authentication.model';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  login(userlogin: IUserLogin): Observable<HttpResponse<IUserToken>> {
    const httpParams = new HttpParams()
      .append('grant_type', 'password')
      .append('client_id', 'client-app')
      .append('client_secret', '123456')
      .append('username', userlogin.username)
      .append('password', userlogin.password);
    return this.http.post<IUserToken>('/api/authenticationService/oauth/token',  httpParams, { headers: new HttpHeaders({ 'Content-Type': 'application/x-www-form-urlencoded' }), observe: 'response' });
  }

  //   register(taskSearch: IUserRegister): Observable<HttpResponse<IUserRegister>> {
  //     return this.http.post<ITaskGroupByPatient[]>(`${this.resourceUrl}/tasksGroupByPatient`, taskSearch,
  //       { params: options, observe: 'response' });
  //   }

}