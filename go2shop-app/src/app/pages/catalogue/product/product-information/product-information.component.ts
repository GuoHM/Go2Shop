import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { IProduct } from '../../product.model';

@Component({
    selector: 'go2shop-product-information',
    templateUrl: './product-information.component.html'
})
export class ProductInformationComponent implements OnInit {

    constructor(
        private route: ActivatedRoute
    ) {}

    public product: IProduct;

    ngOnInit(): void {
        this.route.data.subscribe(
            data => this.product = data.product
        );
    }
}