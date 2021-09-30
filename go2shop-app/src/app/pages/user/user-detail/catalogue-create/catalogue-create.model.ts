export interface ICatalogueCreate {
    name?: string;
    description?: string;
    price?: number;
    stock?: number;
    productImages?: IProductImage[];
    userId?: number;
}

export class CatalogueCreate implements ICatalogueCreate {
  constructor(
    public name?: string,
    public description?: string,
    public price?: number,
    public stock?: number,
    public productImages?: ProductImage[],
    public userId?: number
  ) { }
}

export interface IProductImage {
    id?: string;
    url?: string;
}

export class ProductImage implements IProductImage {
  constructor(
    public url?: string,
    public id?: string,
  ) { }
}
