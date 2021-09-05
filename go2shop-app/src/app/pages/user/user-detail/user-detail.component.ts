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
      label: 'Options',
      items: [{
        label: 'Update',
        icon: 'pi pi-refresh',
           
      },
      {
        label: 'Delete',
        icon: 'pi pi-times',
           
      }
      ]},
    {
      label: 'Navigate',
      items: [{
        label: 'Angular',
        icon: 'pi pi-external-link',
        routerLink: ['purchase/history']
      },
      {
        label: 'Router',
        icon: 'pi pi-upload',
        routerLink: ['profile']
      }
      ]}
    ];
  }

}
