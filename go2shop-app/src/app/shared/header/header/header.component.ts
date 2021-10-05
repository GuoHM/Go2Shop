import { HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from 'app/auth/authentication.service';
import { ShoppingCartService } from 'app/pages/cart/shopping-cart.service';
import { MenuItem, MessageService } from 'primeng/api';

@Component({
  selector: 'go2shop-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  cartSize: number;
  isLogin = false;
  avatarLabel;
  userMenu: MenuItem[] = [
    { label: 'User Detail', icon: 'pi pi-fw pi-users', routerLink: ['/user/detail'] },
    {
      label: 'Logout', icon: 'pi pi-fw pi-sign-out', command: () => {
        this.authenticationService.logout();
        this.messageService.add({ key: 'tc', severity: 'success', summary: 'Success', detail: 'Logout success!' });
      }
    }
  ];

  constructor(
    private authenticationService: AuthenticationService,
    private messageService: MessageService,
    private cartService: ShoppingCartService
  ) { }

  ngOnInit(): void {
    this.initCartSizeHandler();
    this.isLogin = this.authenticationService.isLoggedIn();
    if (this.isLogin) {
      this.avatarLabel = this.authenticationService.getCurrentUser().username.toUpperCase()[0];
      this.cartService.updateCartSize();
    }
    this.authenticationService.loginChangedObserver.subscribe(
      (isLogin) => {
        this.isLogin = isLogin;
        if (isLogin) {
          this.avatarLabel = this.authenticationService.getCurrentUser().username.toUpperCase()[0];
        }
      }
    );
  }

  private initCartSizeHandler(): void {
    this.cartService.updateCartSizeObs.subscribe(
      () => {
        if(this.authenticationService.isLoggedIn()) {
          this.cartService.getCartSize(this.authenticationService.getCurrentUser().userId).subscribe(
            (res: HttpResponse<number>) => {
              if(res && res.body) {
                this.cartSize = res.body;
              }
            }
          );
        } else {
          this.cartSize = null;
        }
      }
    );
  }
}
