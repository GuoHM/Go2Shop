export interface IShoppingCart {
	id?: number;
    price?: number;
    discount?: number;
    userId?: number;
}

export class ShoppingCart implements IShoppingCart {
    constructor(
        public id?: number,
        public price?: number,
        public discount?: number,
        public userId?: number
    ) {}
}