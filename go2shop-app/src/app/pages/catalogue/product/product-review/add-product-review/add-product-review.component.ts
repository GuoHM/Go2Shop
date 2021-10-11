import { Component, EventEmitter } from '@angular/core';
import { AuthenticationService } from 'app/auth/authentication.service';
import { CatalogueService } from 'app/pages/catalogue/catalogue.service';
import { IProduct, IProductReview, ProductReview } from 'app/pages/catalogue/product.model';

@Component({
  selector: 'go2shop-add-product-review',
  templateUrl: './add-product-review.component.html',
  styleUrls: ['./add-product-review.component.css']
})
export class AddProductReviewComponent {

  constructor(
    private catalogueService: CatalogueService,
    private authService: AuthenticationService
  ) {}

  public textReview: string;
  public product: IProduct;
  public maxRatingSize = 5;
  public rating = 0;
  public cancelBtnEmitter = new EventEmitter();
  public saveBtnEmitter = new EventEmitter();

  cancel(): void {
    this.cancelBtnEmitter.emit();
  }

  save(): void {
    this.catalogueService.addProductReview(this.createProductReview()).subscribe(() => this.saveBtnEmitter.emit());
  }

  createProductReview(): IProductReview {
    const userId = this.authService.getCurrentUser().userId;
    return new ProductReview(null, userId, this.textReview, this.rating, this.product);
  }
}