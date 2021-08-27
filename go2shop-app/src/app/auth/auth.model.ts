export interface ILoginUser {
  username?: string,
  authorities?: string[],
  expiresIn?: string,
  token?: string;
}

export class LoginUser implements ILoginUser {
  constructor(
    public username?: string,
    public authorities?: string[],
    public token?: string,
    public expiresIn?: string,
  ) { }
}


export interface IUserToken {
  token?: string;
  refreshToken?: string;
  tokenHead?: string,
  expiresIn?: string
}

export class UserToken implements IUserToken {
  constructor(
    public token?: string,
    public refreshToken?: string,
    public tokenHead?: string,
    public expiresIn?: string,
  ) { }
}