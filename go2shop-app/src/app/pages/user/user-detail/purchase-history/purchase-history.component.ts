import { HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from 'app/auth/authentication.service';
import { IOrder, IOrderDetail, IOrderSearchDTO, OrderSearchDTO } from 'app/pages/order/order.model';
import { OrderService } from 'app/pages/order/order.service';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'go2shop-purchase-history',
  templateUrl: './purchase-history.component.html',
  styleUrls: ['./purchase-history.component.css']
})
export class PurchaseHistoryComponent implements OnInit {

  constructor(
    private router: Router,
    private orderService: OrderService,
    private authService: AuthenticationService,
    private messageService: MessageService
  ) {}

  dateFormat = 'dd-MM-YYYY'
  orders: IOrder[];
  active = 0;
  buyerOrderTypes = ['Pending Payment', 'Completed Payment', 'Delivery in progress', 'Completed', 'Cancelled'];

  ngOnInit(): void {
    this.retrieveOrders(0);
  }

  retrieveOrders(index: number): void {
    this.orders = [];
    if(index < this.buyerOrderTypes.length) {
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
    return new OrderSearchDTO('BUYER', userId, this.buyerOrderTypes[index]);
  }

  confirmPayment(order: IOrder): void {
    let totalPrice = 0;
    if(order.orderDetails) {
      order.orderDetails.forEach((detail: IOrderDetail) => totalPrice += detail.payment);
    }
    this.router.navigate(['/payment', 'order'], { queryParams: { ids: JSON.stringify([order.id]), total: totalPrice, payOption: order.paymentType } });
  }

  orderReceived(order: IOrder): void {
    this.orderService.orderReceived(order).subscribe(
      () => this.retrieveOrders(this.active),
      () => this.messageService.add({ key: 'tc', severity: 'error', summary: 'Fail', detail: 'Failed to complete order!' })
    );
  }
}
