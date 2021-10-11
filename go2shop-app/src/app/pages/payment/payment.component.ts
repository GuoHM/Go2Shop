import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { ShoppingCartService } from '../cart/shopping-cart.service';
import { OrderService } from '../order/order.service';
import { PAYMENT_OPTIONS } from './payment.constants';

@Component({
  selector: 'go2shop-payment',
  templateUrl: './payment.component.html',
  styleUrls: ['./payment.component.css']
})
export class PaymentComponent implements OnInit {
  
  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private cartService: ShoppingCartService,
    private orderService: OrderService,
    private messageService: MessageService
  ) {}

  public orderIds: string[];
  public payOption: string;
  public totalAmountPayable = 0;

  ngOnInit(): void {
    this.totalAmountPayable = this.route.snapshot.queryParams['total'];
    this.payOption = this.route.snapshot.queryParams['payOption'];
    this.orderIds = JSON.parse(this.route.snapshot.queryParams['ids']);
    console.log(this.orderIds);
  }

  getOrderPaymentType(): string {
    if(this.payOption) {
      if(this.payOption === PAYMENT_OPTIONS.CARD) {
        return 'Credit Card / Debit Card';
      } else if(this.payOption === PAYMENT_OPTIONS.PAYNOW) {
        return 'PayNow';
      } else {
        return 'Cash';
      }
    }
  }

  cancelPayment(): void {
    this.orderService.cancelPayment(this.orderIds.map((id: string) => +id)).subscribe(
      () => {
        this.messageService.add({key: 'tc', severity:'success', summary:'Success', detail: 'Cancelled payment successfully'});
        this.cartService.updateShoppingCartState(false);
        setTimeout(() => {
          this.router.navigate(['/cart', 'shoppingCart']);
        }, 2000);
      },
      () => this.messageService.add({key: 'tc', severity:'error', summary:'Fail', detail: 'Failed to cancel payment!'})
    );
  }

  confirmPayment(): void {
    this.orderService.confirmPayment(this.payOption, this.orderIds.map((id: string) => +id)).subscribe(
      () => {
        this.messageService.add({key: 'tc', severity:'success', summary:'Success', detail: 'Completed payment successfully'});
        this.cartService.updateShoppingCartState(false);
        setTimeout(() => {
          this.router.navigate(['/cart', 'shoppingCart']);
        }, 2000);
      },
      () => this.messageService.add({key: 'tc', severity:'error', summary:'Fail', detail: 'Failed to make payment!'})
    );
  }
}