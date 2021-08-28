import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';
import { AuthenticationService } from './authentication.service';

@Injectable()
export class AuthenticationInterceptor implements HttpInterceptor {

  constructor(private authenticationService: AuthenticationService) { }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const idToken = this.getToken();
    const header = this.getHeader();
    if (idToken) {
      const cloned = req.clone({
        headers: req.headers.set('Authorization', header + idToken)
      });
      return next.handle(cloned);
    } else {
      return next.handle(req);
    }
  }

  private getToken(): string {
    const user = this.authenticationService.getCurrentUser();
    return user ? user.token : '';
  }

  private getHeader(): string {
    const user = this.authenticationService.getCurrentUser();
    return user ? user.tokenHead : '';
  }
}