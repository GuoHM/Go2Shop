import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { IUser, IUserLogin, IUserRegister } from './user.model';
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
    return this.http.post<IUserToken>('/api/authenticationService/oauth/token', httpParams, { headers: new HttpHeaders({ 'Content-Type': 'application/x-www-form-urlencoded' }), observe: 'response' });
  }

  logout(): Observable<HttpResponse<void>> {
    return this.http.post<void>('/api/userService/user/logout', null, { observe: 'response' });
  }

  register(register: IUserRegister): Observable<HttpResponse<IUser>> {
    return this.http.post<IUser>('/api/authenticationService/securityUser/register', register,
      { observe: 'response' });
  }

  getUserByUserId(userId: number): Observable<HttpResponse<IUser>> {
    return this.http.get<IUser>('/api/userService/user/detail/' + userId, { observe: 'response' });
  }
}
