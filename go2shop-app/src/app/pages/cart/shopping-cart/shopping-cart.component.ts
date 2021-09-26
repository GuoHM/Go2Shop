import { HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { User } from '../../user/user.model';
import { UserService } from '../../user/user.service';
import { IShoppingCartProduct } from '../shopping-cart-product.model';
import { IShoppingCart } from '../shopping-cart.model';
import { ShoppingCartService } from '../shopping-cart.service';
import { AuthenticationService } from 'app/auth/authentication.service';

@Component({
    selector: 'go2shop-shopping-cart',
    templateUrl: './shopping-cart.component.html',
    styleUrls: ['./shopping-cart.component.css']
  })
  export class ShoppingCartComponent implements OnInit {

    user: User;

    constructor(
      private shoppingCartService: ShoppingCartService,
      private authenticationService: AuthenticationService
    ) { }

    public shoppingCartDTO: IShoppingCart;
    public shoppingCartProductDTO: IShoppingCartProduct[];
    public userID: any;
  
    ngOnInit(): void {
      this.userID = this.authenticationService.getCurrentUser().userId;
      this.shoppingCartProductDTO  = [
        {id : 1, shoppingCartId :1, productId : 123, quantity : 101 },
        {id : 2, shoppingCartId :1, productId : 321, quantity : 102 },
        {id : 3, shoppingCartId :1, productId : 213, quantity : 103 }];
      
      this.shoppingCartService.getShoppingCart(this.userID).subscribe(
        (res: HttpResponse<IShoppingCart>) => {
            this.shoppingCartDTO = res.body;
        }
      );
      this.shoppingCartService.getAllShoppingCartProduct(this.shoppingCartDTO.id).subscribe(
        (res: HttpResponse<IShoppingCartProduct[]>) => {
          this.shoppingCartProductDTO = this.shoppingCartProductDTO.concat(res.body)
        }
      );
    }
  
  }