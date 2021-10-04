import { HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { User } from '../../user/user.model';
import { UserService } from '../../user/user.service';
import { IShoppingCartProduct } from '../shopping-cart-product.model';
import { IShoppingCart } from '../shopping-cart.model';
import { ShoppingCartService } from '../shopping-cart.service';
import { AuthenticationService } from 'app/auth/authentication.service';
import { CatalogueService } from '../../catalogue/catalogue.service';
import { IProduct } from '../../catalogue/product.model';

@Component({
    selector: 'go2shop-shopping-cart',
    templateUrl: './shopping-cart.component.html',
    styleUrls: ['./shopping-cart.component.css']
  })
  export class ShoppingCartComponent implements OnInit {

    constructor(
      private shoppingCartService: ShoppingCartService,
      private authenticationService: AuthenticationService,
      private catalogueService: CatalogueService
    ) { }

    public shoppingCartDTO: IShoppingCart;
    public shoppingCartProductDTO: IShoppingCartProduct[] = [];
    public checkProduct: any[] = [];
    public productInformation: IProduct[] = [];

    public userID: any;
    public productCount: any;
    public masterSelected: boolean = false;
  
    ngOnInit(): void {
      this.userID = this.authenticationService.getCurrentUser().userId;

      /* Get Shopping Cart By User ID */
      this.shoppingCartService.getShoppingCart(this.userID).subscribe(
        (res: HttpResponse<IShoppingCart>) => {
            this.shoppingCartDTO = res.body;
        }
      );

      /* Get All Shopping Cart Products By Shopping Cart ID */
      // this.shoppingCartService.getAllShoppingCartProduct(this.shoppingCartDTO.id).subscribe(
      //   (res: HttpResponse<IShoppingCartProduct[]>) => {
      //     this.shoppingCartProductDTO = this.shoppingCartProductDTO.concat(res.body)
      //   }
      // );

      this.shoppingCartProductDTO  = [
        {id : 1, shoppingCartId :1, productId : 1, quantity : 101 },
        {id : 2, shoppingCartId :1, productId : 2, quantity : 102 },
        {id : 3, shoppingCartId :1, productId : 3, quantity : 103 }];
      
      /* Get All Products Information In Shopping Cart */
      // for (var i = 0; i < this.shoppingCartProductDTO.length; i++)
      // {
      //   this.catalogueService.getProduct(this.shoppingCartProductDTO[i].productId).subscribe(
      //     (res: HttpResponse<IProduct>) => {
      //       this.productInformation = this.productInformation.concat(res.body)
      //     }
      //   );
      // }

        this.catalogueService.getCatalogue().subscribe(
          (res: HttpResponse<IProduct[]>) => {
            this.productInformation = this.productInformation.concat(res.body)
          }
        );

        this.productCount = this.productInformation.length;

      for (var i = 0; i < this.shoppingCartProductDTO.length; i++)
      {
        this.checkProduct[i] = 
        {
          id: this.shoppingCartProductDTO[i].id, 
          shoppingCartId: this.shoppingCartProductDTO[i].shoppingCartId, 
          productId: this.shoppingCartProductDTO[i].productId, 
          quantity: this.shoppingCartProductDTO[i].quantity, 
          isSelected: false
        }
      }
    }

  /* The master checkbox will check/ uncheck all items */
  checkUncheckAll() {
    for (var i = 0; i < this.checkProduct.length; i++) {
      this.checkProduct[i].isSelected = this.masterSelected;
    }
  }

  /* Check All Checkbox Checked */
  isAllSelected() {
    this.masterSelected = this.checkProduct.every(function(item:any) {
        return item.isSelected == true;
      })
  }

  updateQuantity(index: number, action: string) {
    if (action == "add") {
      this.shoppingCartProductDTO[index].quantity = this.shoppingCartProductDTO[index].quantity + 1;
    } else {
      this.shoppingCartProductDTO[index].quantity = this.shoppingCartProductDTO[index].quantity - 1;
    }
    console.log("This is the quantity: " + this.shoppingCartProductDTO[index].quantity);
    this.shoppingCartService.updateQuantity(
      this.shoppingCartProductDTO[index].productId, 
      this.shoppingCartProductDTO[index].quantity,
      this.shoppingCartProductDTO[index].shoppingCartId).subscribe(
        (res: HttpResponse<IShoppingCartProduct>) => {
          this.shoppingCartProductDTO[index].quantity = res.body.quantity;
      }
    );
  }
  
  }