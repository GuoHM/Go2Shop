export interface IProduct {
    id?: number;
    name?: string;
    description?: string;
    price?: number;
    stock?: number;
    userID?: number;
    productImages?: IProductImage[];
}

export class Product implements IProduct {
    constructor(
        public id?: number,
        public name?: string,
        public description?: string,
        public price?: number,
        public stock?: number,
        public userID?: number,
        public productImages?: ProductImage[]
    ) {}
}

export interface IProductImage {
    id?: number;
    productId?: number;
    url?: string;
}

export class ProductImage implements IProductImage {
    constructor(
        public id?: number,
        public productId?: number,
        public urls?: string
    ) {}
}