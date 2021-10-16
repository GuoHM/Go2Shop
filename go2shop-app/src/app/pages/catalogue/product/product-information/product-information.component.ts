import { HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormControl } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { LoginUser } from 'app/auth/authentication.model';
import { AuthenticationService } from 'app/auth/authentication.service';
import { IShoppingCartProduct, ShoppingCartProduct } from 'app/pages/cart/shopping-cart-product.model';
import { ShoppingCartService } from 'app/pages/cart/shopping-cart.service';
import { CatalogueService } from '../../catalogue.service';
import { IProduct, IProductImage, IProductRatings, IProductReview } from '../../product.model';
import { AddProductReviewComponent } from '../product-review/add-product-review/add-product-review.component';

@Component({
  selector: 'go2shop-product-information',
  templateUrl: './product-information.component.html',
  styleUrls: ['./product-information.component.css']
})
export class ProductInformationComponent implements OnInit {

  constructor(
        private route: ActivatedRoute,
        private authService: AuthenticationService,
        private router: Router,
        private cartService: ShoppingCartService,
        private modalService: NgbModal,
        private catalogueService: CatalogueService
  ) {}

    public collectionSize: number;
    public isLoggedIn: boolean;
    public product: IProduct;
    public quantity: AbstractControl = new FormControl(null);
    public maxRatingSize = 5;
    public rating = 0;
    public page = 1;

    ngOnInit(): void {
      this.isLoggedIn = this.authService.isLoggedIn();
      this.route.data.subscribe(
        data => {
          this.product = data.product;
          if(this.product) {
            this.getReviews();
            if(this.product.productImages) {
              this.product.productImages.forEach((img: IProductImage) => img.url = 'assets/images/products/' + img.url);
            }
            this.calculateRatings();
          }
        }
      );
      this.quantity.setValue(1);
    }

    deductQuantity(): void {
      if(this.quantity.value > 1) {
        this.quantity.setValue(this.quantity.value - 1);
      }
    }

    addQuantity(): void {
      if(this.product.stock > this.quantity.value) {
        this.quantity.setValue(this.quantity.value + 1);
      }
    }

    addToCart(): void {
      if(this.verifyLogin()) {
        this.cartService.createShoppingCartProduct(this.buildShoppingCartProduct()).subscribe(
          (res: HttpResponse<IShoppingCartProduct>) => {
            if(res && res.body) {
              this.cartService.updateCartSize();
            }
          }
        );
      } 
    }

    buyNow(): void {
      if(this.verifyLogin()) {
        this.cartService.createShoppingCartProduct(this.buildShoppingCartProduct()).subscribe(
          (res: HttpResponse<ShoppingCartProduct>) => {
            if(res && res.body) {
              this.router.navigate(['/cart', 'shoppingCart']);
              res.body.product = this.product;
              this.cartService.updateShoppingCartState(true, res.body);
            }
          }
        );
      } 
    }

    private verifyLogin(): boolean {
      if(!this.authService.isLoggedIn()) {
        this.router.navigate(['/user', 'login']);
        return false;
      }
      return true;
    }

    private buildShoppingCartProduct(): IShoppingCartProduct {
      const user: LoginUser = this.authService.getCurrentUser();
      return new ShoppingCartProduct(null, user.cartId, this.quantity.value, this.product);
    }

    private calculateRatings(): void {
      if(this.product.productRatings && this.product.productRatings.noOfReviews > 0) {
        this.rating = this.product.productRatings.totalRatings / this.product.productRatings.noOfReviews;
      } 
    }

    addReview(): void {
      const modalRef = this.modalService.open(AddProductReviewComponent as Component, { size: 'md', backdrop: 'static', centered: true });
      modalRef.componentInstance.product = this.product;
      modalRef.componentInstance.cancelBtnEmitter.subscribe(
        () => modalRef.dismiss()
      );
      modalRef.componentInstance.saveBtnEmitter.subscribe(
        () => {
          this.getRatings();
          this.getReviews();
          modalRef.dismiss();
        }
      );
    }

    getRatings(): void {
      this.catalogueService.getProductRatings(this.product.id).subscribe(
        (res: HttpResponse<IProductRatings>) => {
          if(res && res.body) {
            this.product.productRatings = res.body;
            this.calculateRatings();
          }
        }
      );
    }

    getReviews(page?: number): void {
      const pageInfo = {
        page: page ? page-1 : 0,
        size: 6,
        sort: ['id' + ',' + 'desc']
      };
      this.catalogueService.getProductReviews(this.product.id, pageInfo).subscribe(
        (res: HttpResponse<IProductReview[]>) => {
          if(res && res.body) {
            this.product.productReviews = res.body;
            this.collectionSize = +res.headers.get('X-Total-Count');
          }
        }
      );
    }
}