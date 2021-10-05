export interface IUserToProduct {
    userId: number;
    productId: number;
    quantity: number;
}

export class UserToProduct implements IUserToProduct {
  constructor(
        public userId: number,
        public productId: number,
        public quantity: number
  ) {}
}

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