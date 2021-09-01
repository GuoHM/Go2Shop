export interface IUserLogin {
    username?: string;
    password?: string;
}

export class UserLogin implements IUserLogin {
  constructor(
    public username?: string,
    public password?: string,
  ) { }
}

export interface IUserRegister {
  username?: string;
  password?: string;
  name?: string;
  cardNumber?: string;
  address?: string;
  contactDetail?: string;
  type?: string;
}

export class UserRegister implements IUserRegister {
  constructor(
    public username?: string,
    public password?: string,
    public name?: string,
    public cardNumber?: string,
    public address?: string,
    public contactDetail?: string,
    public type?: string,
  ) { }
}

export interface IUser {
  id: number;
  name: string;
  cardNumber: string;
  address: string;
  contactDetail: string;
  type: string;
}

export class User implements IUser {
  constructor(
    public id: number,
    public name: string,
    public cardNumber: string,
    public address: string,
    public contactDetail: string,
    public type: string,
  ) { }
}
