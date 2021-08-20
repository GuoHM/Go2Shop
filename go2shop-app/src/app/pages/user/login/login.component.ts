import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { IUserLogin, UserLogin } from '../user.model';
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
    private userService: UserService
  ) { }

  ngOnInit(): void {
    this.newForm();

  }

  private newForm(): void {
    this.loginForm = this.formBuilder.group({
      username: new FormControl(''),
      passowrd: new FormControl('')
    });
  }

  login(): void {
    const userlogin: UserLogin = new UserLogin(this.loginForm.get('username').value, this.loginForm.get('password').value);
    this.userService.login(userlogin).subscribe({
      complete: () => { console.log('success'); }, 
      error: () => { console.log('error'); }, 
      next: () => { console.log('error'); }, 
    });
  }
  

}
