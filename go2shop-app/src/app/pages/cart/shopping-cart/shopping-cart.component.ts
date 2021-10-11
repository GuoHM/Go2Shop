import { HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { IShoppingCartProduct, ShoppingCartProduct } from '../shopping-cart-product.model';
import { IShoppingCart } from '../shopping-cart.model';
import { ShoppingCartService } from '../shopping-cart.service';
import { AuthenticationService } from 'app/auth/authentication.service';
import { IProduct, IProductImage } from '../../catalogue/product.model';
import { Router } from '@angular/router';
import { IUser } from 'app/pages/user/user.model';
import { UserService } from 'app/pages/user/user.service';
import { AbstractControl, FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { OrderService } from 'app/pages/order/order.service';
import { MessageService } from 'primeng/api';
import { CreateOrderDTO, ICreateOrderDTO, IOrder } from 'app/pages/order/order.model';
import { PAYMENT_OPTIONS } from 'app/pages/payment/payment.constants';

@Component({
  selector: 'go2shop-shopping-cart',
  templateUrl: './shopping-cart.component.html',
  styleUrls: ['./shopping-cart.component.css']
})
export class ShoppingCartComponent implements OnInit {

  constructor(
      private shoppingCartService: ShoppingCartService,
      private authenticationService: AuthenticationService,
      private router: Router,
      private userService: UserService,
      private fb: FormBuilder,
      private orderService: OrderService,
      private messageService: MessageService
  ) { }

    public paymentOptions = PAYMENT_OPTIONS;
    public shoppingCartDTO: IShoppingCart;
    public shoppingCartProducts: ShoppingCartProduct[] = [];
    public displayedShoppingCartProducts: ShoppingCartProduct[] = [];
    public checkProduct: any[] = [];
    public productInformation: IProduct[] = [];
    public isBuyNow = false;
    public userID: any;
    public productCount: any;
    public masterSelected = false;
    public totalPrice = 0;
    public user: IUser;
    public isCheckedOut = false;
    public userDetailsForm: FormGroup = this.fb.group({
      address: new FormControl(''),
      payOption: new FormControl(PAYMENT_OPTIONS.CARD),
      cardNo: new FormControl('')
    });
    private checkedOutShoppingCartProducts: ShoppingCartProduct[] =[];

    get address(): AbstractControl {
      return this.userDetailsForm.get('address');
    }

    get cardNo(): AbstractControl {
      return this.userDetailsForm.get('cardNo');
    }

    get payOption(): AbstractControl {
      return this.userDetailsForm.get('payOption');
    }

    ngOnInit(): void {
      this.initShoppingCartStateHandler();
    }

    initShoppingCartStateHandler(): void {
      this.shoppingCartService.buyNowObs.subscribe(
        (isBuyNow: boolean) => {
          if(isBuyNow) {
            this.isBuyNow = isBuyNow;
            if(this.shoppingCartService.buyNowProduct) {
              this.shoppingCartService.buyNowProduct.isSelected = true;
              this.shoppingCartProducts = [this.shoppingCartService.buyNowProduct];
              this.checkOut();
              this.calculateTotalPrice();
            }
          } else if(this.isBuyNow) {
            this.cancelCheckOut();
          } else {  
            if(this.authenticationService.isLoggedIn()) {
              this.retrieveShoppingCart();
            } else {
              this.router.navigate(['/user', 'login']);
            }
          }
        }
      );
    }

    retrieveShoppingCart(): void {
      this.shoppingCartService.getAllShoppingCartProduct(this.authenticationService.getCurrentUser().cartId).subscribe(
        (res: HttpResponse<ShoppingCartProduct[]>) => {
          if(res && res.body) {
            this.processImageUrls(res.body);
            this.shoppingCartProducts = res.body;
            this.displayedShoppingCartProducts = res.body;
            this.calculateTotalPrice();
          }
        }
      );  
    }

    processImageUrls(products: ShoppingCartProduct[]): void {
      if(products) {
        products.forEach((product: IShoppingCartProduct) => {
          if(product.product && product.product.productImages) {
            product.product.productImages.forEach((image: IProductImage) => image.url = 'assets/images/products/' + image.url);
          }
        });
      }
    }

    /* The master checkbox will check/ uncheck all items */
    checkAll(): void {
      if(this.shoppingCartProducts){
        this.shoppingCartProducts.forEach((product: ShoppingCartProduct) => {
          product.isSelected = this.masterSelected;
        });
        this.calculateTotalPrice();
      }
    }

    /* Check All Checkbox Checked */
    isAllSelected(): void {
      this.masterSelected = this.shoppingCartProducts.every((item:ShoppingCartProduct) => item.isSelected);
    }

    hasSelected(): boolean {
      return this.shoppingCartProducts.some((item:ShoppingCartProduct) => item.isSelected);
    }

    deductQuantity(index: number): void {
      if(this.shoppingCartProducts && index > -1 && this.shoppingCartProducts.length > index) {
        if(this.shoppingCartProducts[index].quantity - 1 > 0) {
          this.shoppingCartProducts[index].quantity -= 1;
        }
      }
    }

    addQuantity(index: number): void {
      if(this.shoppingCartProducts && index > -1 && this.shoppingCartProducts.length > index) {
        if(this.shoppingCartProducts[index].product.stock >= this.shoppingCartProducts[index].quantity + 1) {
          this.shoppingCartProducts[index].quantity += 1;
        }
      }
    }

    calculateTotalPrice(): void {
      let sum = 0;
      this.shoppingCartProducts.forEach((product: ShoppingCartProduct) => {
        if(product.isSelected) {
          sum += product.quantity * product.product.price;
        }
      });
      this.totalPrice = sum;
    }

    checkOut(): void {
      this.userService.getUserByUserId(this.authenticationService.getCurrentUser().userId).subscribe(
        (res: HttpResponse<IUser>) => {
          if(res && res.body) {
            this.user = res.body;
            this.address.setValue(this.user.address);
            this.cardNo.setValue(this.maskCardNo(this.user.cardNumber));
            this.isCheckedOut = true;
            this.checkedOutShoppingCartProducts = this.shoppingCartProducts.filter((product: ShoppingCartProduct) => product.isSelected);
            this.displayedShoppingCartProducts = this.checkedOutShoppingCartProducts;
          }
        }
      );
    }

    cancelCheckOut(): void {
      this.userDetailsForm.reset();
      this.isCheckedOut = false;
      if(this.isBuyNow) {
        this.isBuyNow = false;
        this.retrieveShoppingCart();
      } else {
        this.displayedShoppingCartProducts = this.shoppingCartProducts;
      }
      
      this.checkedOutShoppingCartProducts = [];
    }

    placeOrder(): void {
      this.orderService.placeOrder(this.createOrder()).subscribe(
        res => {
          if(res && res.body) {
            this.shoppingCartService.updateCartSize();
            this.messageService.add({key: 'tc', severity:'success', summary:'Success', detail: 'Order created successfully'});
            setTimeout(() => {
              const ids = [];
              res.body.forEach((o: IOrder) => ids.push(o.id));
              this.router.navigate(['/payment', 'order'], { queryParams: { ids: JSON.stringify(ids), total: this.totalPrice, payOption: this.payOption.value } });
            }, 2000);
          }
        },
        (err) => this.messageService.add({key: 'tc', severity:'error', summary:'Fail', detail: err.error.errMsg})
      );
    }

    createOrder(): ICreateOrderDTO {
      const userId = this.authenticationService.getCurrentUser().userId;
      return new CreateOrderDTO(this.payOption.value, userId, this.checkedOutShoppingCartProducts);
    }

    maskCardNo(cardNo: string): string {
      if(cardNo && cardNo !== '') {
        return '*' + cardNo.substring(cardNo.length-4, cardNo.length);
      }
      return '';
    }
}
