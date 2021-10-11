import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { AuthenticationService } from 'app/auth/authentication.service';
import { ShoppingCartService } from 'app/pages/cart/shopping-cart.service';
import { MessageService } from 'primeng/api';
import { UserLogin } from '../user.model';
import { UserService } from '../user.service';

@Component({
  selector: 'go2shop-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private userService: UserService,
    private authenticationService: AuthenticationService,
    private messageService: MessageService,
    private cartService: ShoppingCartService,
    private location: Location
  ) { }

  ngOnInit(): void {
    this.newForm();

  }

  private newForm(): void {
    this.loginForm = this.formBuilder.group({
      username: new FormControl(''),
      password: new FormControl('')
    });
  }

  login(): void {
    const userlogin: UserLogin = new UserLogin(this.loginForm.get('username').value, this.loginForm.get('password').value);
    this.userService.login(userlogin).subscribe(
      (res) => {
        this.authenticationService.handleLoginSuccess(res.body);
        this.messageService.add({key: 'tc', severity:'success', summary:'Success', detail: 'You have successful login!'});
        this.cartService.updateCartSize();
        setTimeout(() => {
          this.location.back();
        }, 2000);

      },
      (err) => {
        if (err.error.errCode === 'B102') {
          this.messageService.add({ key: 'tc', severity: 'error', summary: 'Fail', detail: err.error.errMsg });
        }
      }
    );
  }


}
