import {Injectable} from '@angular/core';
import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable, of, throwError} from 'rxjs';
import { AuthenticationService } from './authentication.service';
import { Router } from '@angular/router';
import { catchError } from 'rxjs/operators';
import { ShoppingCartService } from 'app/pages/cart/shopping-cart.service';

@Injectable()
export class AuthenticationInterceptor implements HttpInterceptor {

  constructor(
    private authenticationService: AuthenticationService,
    private router: Router,
    private cartService: ShoppingCartService
  ) { }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const idToken = this.getToken();
    const header = this.getHeader();
    if (idToken) {
      const cloned = req.clone({
        headers: req.headers.set('Authorization', header + idToken)
      });
      return next.handle(cloned).pipe(catchError(x=> this.handleError(x)));
    } else {
      return next.handle(req).pipe(catchError(x=> this.handleError(x)));
    }
  }

  private handleError(err: HttpErrorResponse): Observable<any> {
    if (err.status === 401 || err.status === 403) {
      this.authenticationService.logout();
      this.cartService.updateCartSize();
      this.router.navigateByUrl('/user/login');
      return of(err.message); 
    }
    return throwError(err);
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