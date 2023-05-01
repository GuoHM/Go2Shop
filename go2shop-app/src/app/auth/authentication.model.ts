export interface ILoginUser {
  username?: string;
  authorities?: string[];
  expiresIn?: string;
  tokenHead?: string;
  token?: string;
  userId?: number;
  cartId?: number;
  authEnabled?: boolean;
}

export class LoginUser implements ILoginUser {
  constructor(
    public username?: string,
    public authorities?: string[],
    public token?: string,
    public tokenHead?: string,
    public expiresIn?: string,
    public userId?: number,
    public cartId?: number,
    public authEnabled?: boolean
  ) { }
}


export interface IUserToken {
  token?: string;
  refreshToken?: string;
  tokenHead?: string;
  expiresIn?: string;
  userId?: number;
  cartId?: number;
  authEnabled?: boolean;
}

export class UserToken implements IUserToken {
  constructor(
    public token?: string,
    public refreshToken?: string,
    public tokenHead?: string,
    public expiresIn?: string,
    public userId?: number,
    public cartId?: number,
    public authEnabled?: boolean
  ) { }
}