import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { LoginService } from '../login.service';

@Component({
  selector: 'go2shop-login-otp',
  templateUrl: './login-otp.component.html'
})
export class LoginOTPComponent implements OnInit {

  loginForm: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private loginService: LoginService
  ) {}

  ngOnInit(): void {
    this.newForm();

  }

  private newForm(): void {
    this.loginForm = this.formBuilder.group({
      otp: new FormControl(null)
    });
  }

  login(): void {
    if(this.loginForm.controls.otp.value) {
      this.loginService.setUserLoginDetails({ ...this.loginService.getUserLoginDetails(), otp: this.loginForm.controls.otp.value });
      this.loginService.login();
    }
  }


}
