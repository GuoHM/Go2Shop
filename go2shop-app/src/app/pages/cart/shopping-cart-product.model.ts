import { IProduct, Product } from '../catalogue/product.model';

export interface IShoppingCartProduct {
	id?: number;
    shoppingCartId?: number;
    quantity?: number;
    product?: IProduct;
}

export class ShoppingCartProduct implements IShoppingCartProduct {
    public isSelected = false;
    constructor(
        public id?: number,
        public shoppingCartId?: number,
        public quantity?: number,
        public product?: Product
    ) {}
}
