import { HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ShoppingCartProduct } from '../shopping-cart-product.model';
import { IShoppingCart } from '../shopping-cart.model';
import { ShoppingCartService } from '../shopping-cart.service';
import { AuthenticationService } from 'app/auth/authentication.service';
import { IProduct } from '../../catalogue/product.model';
import { Router } from '@angular/router';

@Component({
  selector: 'go2shop-shopping-cart',
  templateUrl: './shopping-cart.component.html',
  styleUrls: ['./shopping-cart.component.css']
})
export class ShoppingCartComponent implements OnInit {

  constructor(
      private shoppingCartService: ShoppingCartService,
      private authenticationService: AuthenticationService,
      private router: Router
  ) { }

    public shoppingCartDTO: IShoppingCart;
    public shoppingCartProducts: ShoppingCartProduct[] = [];
    public checkProduct: any[] = [];
    public productInformation: IProduct[] = [];

    public userID: any;
    public productCount: any;
    public masterSelected = false;
    public totalPrice = 0;
  
    ngOnInit(): void {
      if(this.authenticationService.isLoggedIn()) {
        this.shoppingCartService.getAllShoppingCartProduct(this.authenticationService.getCurrentUser().cartId).subscribe(
          (res: HttpResponse<ShoppingCartProduct[]>) => {
            this.shoppingCartProducts = res.body;
            this.calculateTotalPrice();
          }
        );
      } else {
        this.router.navigate(['/user', 'login']);
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
}
