import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from 'app/auth/authentication.service';
import { MenuItem } from 'primeng/api';

@Component({
  selector: 'go2shop-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  isLogin = false;
  avatarLabel;
  userMenu: MenuItem[] = [
    {label: 'User Profile', icon: 'pi pi-fw pi-users'},
    {label: 'Logout', icon: 'pi pi-fw pi-sign-out', command: () => {
      this.authenticationService.logout();
    }}
  ];
  
  constructor( private authenticationService: AuthenticationService) { }

  ngOnInit(): void {
    this.isLogin = this.authenticationService.isLoggedIn();
    if (this.isLogin) {
      this.avatarLabel = this.authenticationService.getCurrentUser().username.toUpperCase()[0];
    }
    this.authenticationService.loginChangedObserver.subscribe(
      (isLogin) => {
        this.isLogin = isLogin;
        this.avatarLabel = this.authenticationService.getCurrentUser().username.toUpperCase()[0];
      }
    );
  }


}
