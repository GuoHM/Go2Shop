import * as moment from 'moment';
import { IShoppingCartProduct, ShoppingCartProduct } from '../cart/shopping-cart-product.model';

export interface IOrder {
    id?: number;
    status?: string;
    orderDate?: moment.Moment;
    paymentType?: string;
    buyerId?: number;
    sellerId?: number;
    orderDetails?: IOrderDetail[];
}

export class Order implements IOrder {
  constructor(
        public id?: number,
        public status?: string,
        public orderDate?: moment.Moment,
        public paymentType?: string,
        public buyerId?: number,
        public sellerId?: number,
        public orderDetails?: IOrderDetail[]
  ) {}
}

export interface IOrderDetail {
  id?: number;
  payment?: number;
}

export class OrderDetail implements IOrderDetail{
  constructor(
    public id?: number,
    public payment?: number
  ) {}
}

export interface ICreateOrderDTO {
  paymentType: string;
  buyerId: number;
  cartProducts: IShoppingCartProduct[]
}

export class CreateOrderDTO implements ICreateOrderDTO {
  constructor(
    public paymentType: string,
    public buyerId: number,
    public cartProducts: ShoppingCartProduct[]
  ) {}
}

export interface IOrderSearchDTO {
  role?: string;
  roleId?: number;
  orderStatus?: string;
}

export class OrderSearchDTO implements IOrderSearchDTO {
  constructor(
    public role?: string,
    public roleId?: number,
    public orderStatus?: string
  ) {}
}