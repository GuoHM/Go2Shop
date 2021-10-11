import { Component, Input } from '@angular/core';
import { IUser } from 'app/pages/user/user.model';
import { UserService } from 'app/pages/user/user.service';
import { IProductReview } from '../../product.model';

@Component({
  selector: 'go2shop-product-review',
  templateUrl: './product-review.component.html',
  styleUrls: ['./product-review.component.css']
})
export class ProductReviewComponent {

  constructor(
        private userService: UserService
  ) {}

    public user: IUser;
    public avatarLabel: string;
    public review: IProductReview;
    public maxRatingSize = 5;

    @Input('review') set productReview(review: IProductReview) {
      this.review = review;
      this.avatarLabel = review.user.name.toUpperCase()[0];
      this.user = review.user;
    } 
}