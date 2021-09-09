import { Component, OnInit } from '@angular/core';
import { MenuItem } from 'primeng/api';

@Component({
  selector: 'go2shop-user-detail',
  templateUrl: './user-detail.component.html',
  styleUrls: ['./user-detail.component.css']
})
export class UserDetailComponent implements OnInit {

  items: MenuItem[];
  constructor() { }

  ngOnInit(): void {
    this.items = [{
      label: 'Profile',
      icon: 'pi pi-users',
      routerLink: ['profile']
    }, {
      label: 'Purchase history',
      icon: 'pi pi-wallet',
      routerLink: ['purchase/history']
    }];
  }


}
