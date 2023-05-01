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

  retrieveToken(userlogin: IUserLogin): Observable<HttpResponse<IUserToken>> {
    const httpParams = new HttpParams()
      .append('grant_type', 'password')
      .append('client_id', 'client-app')
      .append('client_secret', '123456')
      .append('username', userlogin.username)
      .append('password', userlogin.password)
      .append('otp', `${userlogin.otp}`);
    return this.http.post<IUserToken>('/api/authenticationService/oauth/token', httpParams, { headers: new HttpHeaders({ 'Content-Type': 'application/x-www-form-urlencoded' }), observe: 'response' });
  }

  login(userlogin: IUserLogin): Observable<HttpResponse<boolean>> {
    return this.http.post<boolean>('/api/authenticationService/securityUser/login', userlogin,
      { observe: 'response' });
  }

  register(register: IUserRegister): Observable<HttpResponse<IUser>> {
    return this.http.post<IUser>('/api/authenticationService/securityUser/register', register,
      { observe: 'response' });
  }

  getUserByUserId(userId: number): Observable<HttpResponse<IUser>> {
    return this.http.get<IUser>('/api/userService/user/detail/' + userId, { observe: 'response' });
  }

  verifyIf2faIsRequired(username: string): Observable<HttpResponse<boolean>> {
    const httpParams = new HttpParams()
    .append('username', username);
    return this.http.get<boolean>('/api/authenticationService/securityUser/is2faRequired', { params: httpParams, observe: 'response' });
  }

  update2fa(user: IUserRegister): Observable<String> {
    return this.http.post<String>('/api/authenticationService/securityUser/update2fa', user, { responseType: 'text' as 'json' });
  }
}