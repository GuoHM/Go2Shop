import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { UserLogin } from '../user.model';
import { UserService } from '../user.service';
import { Router } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { LoginService } from './login.service';

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
    private loginService: LoginService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.newForm();

  }

  private newForm(): void {
    this.loginForm = this.formBuilder.group({
      username: new FormControl(''),
      password: new FormControl(''),
      otp: new FormControl('')
    });
  }

  login(): void {
    this.loginService.setUserLoginDetails(new UserLogin(this.loginForm.get('username').value, this.loginForm.get('password').value));
    this.userService.verifyIf2faIsRequired(this.loginForm.get('username').value).subscribe(
      (res: HttpResponse<boolean>) => {
        if(res && res.body) {
          this.router.navigate(['/user', 'login', 'otp']);
        } else{
          this.loginService.login();
        }
      }
    )
  }


}
