import {Injectable} from '@angular/core';
import { LoginUser, UserToken } from './auth.model';
import jwt_decode from 'jwt-decode';

@Injectable({providedIn: 'root'})
export class AuthenticationService {

  handleLoginSuccess(usertoken: UserToken): void {
    const user: any = jwt_decode(usertoken.token);
    const loginUSer: LoginUser = new LoginUser();
    loginUSer.username = user.user_name;
    loginUSer.authorities = user.authorities;
    loginUSer.token = usertoken.token;
    loginUSer.expiresIn = usertoken.expiresIn;
    localStorage.setItem('currentUser', JSON.stringify(loginUSer));  
  }
  
  getCurrentUser(): LoginUser {
    const userStr = localStorage.getItem('currentUser');
    return userStr ? JSON.parse(userStr) : '';
  }
  
  getToken(): string {
    const currentUser = this.getCurrentUser();
    return currentUser ? currentUser.token : '';
  }
  
  getUsername(): string {
    const currentUser = this.getCurrentUser();
    return currentUser ? currentUser.username : '';
  }
  
  logout(): void {
    localStorage.removeItem('currentUser');
  }
  
  isLoggedIn(): boolean {
    const token: string = this.getToken();
    return token && token.length > 0;
  }
  
  hasRole(role: string): boolean {
    const currentUser = this.getCurrentUser();
    if (!currentUser) {
      return false;
    }
    const authorities: string[] = currentUser.authorities;
    const result =  authorities.indexOf('ROLE_' + role) != -1;
    return result;
  }
}

