import { HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from 'app/auth/authentication.service';
import { IOrder, IOrderSearchDTO, OrderSearchDTO } from 'app/pages/order/order.model';
import { OrderService } from 'app/pages/order/order.service';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'go2shop-sales-history',
  templateUrl: './sales-history.component.html'
})
export class SalesHistoryComponent implements OnInit {

  constructor(
    private router: Router,
    private orderService: OrderService,
    private authService: AuthenticationService,
    private messageService: MessageService
  ) {}

  dateFormat = 'dd-MM-YYYY'
  orders: IOrder[];
  active = 0;
  sellerOrderTypes = ['Pending Payment', 'Completed Payment', 'Delivery in progress', 'Completed'];

  ngOnInit(): void {
    this.retrieveOrders(0);
  }

  retrieveOrders(index: number): void {
    this.orders = [];
    if(index < this.sellerOrderTypes.length) {
      this.orderService.searchOrders(this.createOrderSearch(index)).subscribe(
        (res: HttpResponse<IOrder[]>) => {
          if(res && res.body) {
            this.orders = res.body;
          }
        }
      );
    }
  }

  private createOrderSearch(index: number): IOrderSearchDTO {
    const userId = this.authService.getCurrentUser().userId;
    return new OrderSearchDTO('SELLER', userId, this.sellerOrderTypes[index]);
  }

  confirmDelivery(order: IOrder): void {
    this.orderService.confirmDelivery(order).subscribe(
      () => this.retrieveOrders(this.active),
      () => this.messageService.add({ key: 'tc', severity: 'error', summary: 'Fail', detail: 'Failed to complete order!' })
    );
  }
}
