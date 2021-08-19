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

export interface IUserRegister extends IUserLogin {
    passwordRepeat?: string;
}

export class UserRegister implements IUserRegister {
  constructor(
    public username?: string,
    public password?: string,
    public passwordRepeat?: string
  ) { }
}
