import { Component, Input } from '@angular/core';
import { IProduct } from '../product.model';

@Component({
    selector: 'go2shop-product',
    templateUrl: './product.component.html',
    styleUrls: ['./product.component.css']
})
export class ProductComponent {
  
    @Input() public product: IProduct;

}