export interface IProduct {
    id?: number;
    name?: string;
    description?: string;
    price?: number;
    stock?: number;
    userID?: number;
    productRatings?: IProductRatings;
    productImages?: IProductImage[];
    productReviews?: IProductReview[];
}

export class Product implements IProduct {
  constructor(
        public id?: number,
        public name?: string,
        public description?: string,
        public price?: number,
        public stock?: number,
        public userID?: number,
        public productRatings?: ProductRatings,
        public productImages?: ProductImage[],
        public productReviews?: ProductReview[]
  ) {}
}

export interface IProductRatings {
  totalRatings?: number;
  noOfReviews?: number;
}

export class ProductRatings implements IProductRatings {
  constructor(
    public totalRatings?: number,
    public noOfReviews?: number
  ) {}
}

export interface IProductReview {
    id?: number;
    userId?: number;
    review?: string;
    rating?: number;
    product?: IProduct;
}

export class ProductReview implements IProductReview {
  constructor(
        public id?: number,
        public userId?: number,
        public review?: string,
        public rating?: number,
        public product?: Product
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

export interface IProductSearch {
  searchTerm?: string;
}

export class ProductSearch implements IProductSearch {
  constructor(
    public searchTerm?: string
  ) {}
}