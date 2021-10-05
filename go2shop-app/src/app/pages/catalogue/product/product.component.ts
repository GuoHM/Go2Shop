import { Component, Input, OnInit } from '@angular/core';
import { IProduct } from '../product.model';

@Component({
  selector: 'go2shop-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css']
})
export class ProductComponent implements OnInit {

    image;
    @Input() public product: IProduct;

    ngOnInit(): void {
      this.image = (this.product.productImages && this.product.productImages.length > 0) ? 'assets/images/products/' + this.product.productImages[0].url : 'assets/images/no_image_available.jpg';
    }

}