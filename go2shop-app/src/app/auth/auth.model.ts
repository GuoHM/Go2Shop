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