import { IProduct, Product } from '../catalogue/product.model';

export interface IShoppingCartProduct {
	id?: number;
    shoppingCartId?: number;
    productId?: number;
    quantity?: number;
    product?: IProduct;
}

export class ShoppingCartProduct implements IShoppingCartProduct {
    public isSelected = false;
    constructor(
        public id?: number,
        public shoppingCartId?: number,
        public productId?: number,
        public quantity?: number,
        public product?: Product
    ) {}
}
