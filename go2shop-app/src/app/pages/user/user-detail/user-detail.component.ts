import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from 'app/auth/authentication.service';
import { MenuItem } from 'primeng/api';

@Component({
  selector: 'go2shop-user-detail',
  templateUrl: './user-detail.component.html',
  styleUrls: ['./user-detail.component.css']
})
export class UserDetailComponent implements OnInit {

  items: MenuItem[];
  
  constructor(
    private authenticationService: AuthenticationService
  ) { 
    
  }

  ngOnInit(): void {
    this.items = [{
      label: 'Profile',
      icon: 'pi pi-users',
      routerLink: ['profile']
    }];
    if (this.authenticationService.hasRole('seller')) {
      this.items.push(
        {
          label: 'Create catalogue',
          icon: 'pi pi-images',
          routerLink: ['seller/catalogue/create']
        }
      );
    }
    if (this.authenticationService.hasRole('buyer')) {
      this.items.push(
        {
          label: 'Purchase history',
          icon: 'pi pi-wallet',
          routerLink: ['purchase/history']
        }
      );
    }
    if (this.authenticationService.hasRole('seller')) {
      this.items.push(
        {
          label: 'Sales history',
          icon: 'pi pi-money-bill',
          routerLink: ['sales/history']
        }
      );
    }
  }
}
