export interface IShoppingCartProduct {
	id?: number;
    shoppingCartId?: number;
    productId?: number;
    quantity?: number;
}

export class ShoppingCartProduct implements IShoppingCartProduct {
    constructor(
        public id?: number,
        public shoppingCartId?: number,
        public productId?: number,
        public quantity?: number
    ) {}
}