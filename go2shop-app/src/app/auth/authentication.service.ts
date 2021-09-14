import {Injectable} from '@angular/core';
import { LoginUser, UserToken } from './authentication.model';
import jwt_decode from 'jwt-decode';
import { Subject } from 'rxjs';

@Injectable({providedIn: 'root'})
export class AuthenticationService {

  private loginChangedSubject = new Subject<boolean>();
  loginChangedObserver = this.loginChangedSubject.asObservable();

  handleLoginSuccess(usertoken: UserToken): void {
    const user: any = jwt_decode(usertoken.token);
    const loginUser: LoginUser = new LoginUser();
    loginUser.username = user.user_name;
    loginUser.authorities = user.authorities;
    loginUser.token = usertoken.token;
    loginUser.expiresIn = usertoken.expiresIn;
    loginUser.tokenHead = usertoken.tokenHead;
    loginUser.userId = usertoken.userId;
    localStorage.setItem('currentUser', JSON.stringify(loginUser));  
    this.loginChanged(true);
  }
  
  getCurrentUser(): LoginUser {
    const userStr = localStorage.getItem('currentUser');
    return userStr ? JSON.parse(userStr) : null;
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
    this.loginChanged(false);
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
    const result =  authorities.indexOf(role) != -1;
    return result;
  }

  loginChanged(isLogin: boolean): void {
    this.loginChangedSubject.next(isLogin);
  }
}

