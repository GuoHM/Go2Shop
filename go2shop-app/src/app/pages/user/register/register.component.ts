import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { isControlValid, isControlInvalid } from 'app/shared/utils/form.utils';
import { MessageService } from 'primeng/api';
import { IUser, UserRegister } from '../user.model';
import { UserService } from '../user.service';
import { HttpResponse } from '@angular/common/http';
import * as crypto from 'crypto-js';
import { environment } from 'environments/environment';

@Component({
  selector: 'go2shop-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  isValid = isControlValid;
  isInvalid = isControlInvalid;

  registerForm: FormGroup;

  password: string;

  constructor(
    private formBuilder: FormBuilder,
    private userService: UserService,
    private messageService: MessageService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.newForm();
  }

  private newForm(): void {
    this.registerForm = this.formBuilder.group({
      username: new FormControl('', [Validators.required]),
      password: new FormControl('', [Validators.required]),
      passwordRepeat: new FormControl('', [Validators.required]),
      name: new FormControl('', [Validators.required]),
      address: new FormControl('', [Validators.required]),
      contactDetail: new FormControl('', [Validators.required]),
      cardNumber: new FormControl('', [Validators.required]),
      type: new FormControl('', [Validators.required]),
      authEnabled: new FormControl(true, [Validators.required])
    }, { validator: this.checkIfMatchingPasswords('password', 'passwordRepeat') });
  }

  checkIfMatchingPasswords(passwordKey: string, passwordConfirmationKey: string) {
    return (group: FormGroup) => {
      const passwordInput = group.controls[passwordKey];
      const passwordConfirmationInput = group.controls[passwordConfirmationKey];
      if (passwordInput.value !== passwordConfirmationInput.value) {
        return passwordConfirmationInput.setErrors({ notEquivalent: true });
      }
      else {
        return passwordConfirmationInput.setErrors(null);
      }
    };
  }

  /**
  * Returns an accessible control of the codeset form controls
  */
  get form(): any { return this.registerForm.controls; }

  register(): void {
    this.registerForm.markAllAsTouched();
    const user: UserRegister = new UserRegister();
    user.username = this.registerForm.get('username').value;
    user.password = this.registerForm.get('password').value;
    user.name = this.registerForm.get('name').value;
    user.address = this.registerForm.get('address').value;
    user.contactDetail = this.registerForm.get('contactDetail').value;
    user.cardNumber = this.registerForm.get('cardNumber').value;
    user.type = this.registerForm.get('type').value;
    user.authEnabled = this.registerForm.get('authEnabled').value;
    this.encryptDetails(user);
    this.userService.register(user).subscribe(
      (user: HttpResponse<IUser>) => {
        this.messageService.add({ key: 'tc', severity: 'success', summary: 'Success', detail: 'Registration Completed!' });
        if(user && user.body && user.body.qrCode) {
          this.router.navigate(['/user', 'register2fa'], { queryParams: { qrCode: user.body.qrCode, isRouteToLogin: true } }); 
        } else {
          this.router.navigate(['/user', 'login']);
        }
      },
      (err) => {
        console.log(err);
        if (err.error.errCode === 'B104') {
          this.messageService.add({ key: 'tc', severity: 'error', summary: 'Fail', detail: err.error.errMsg });
        }
      }
    );
  }

  private encryptDetails(user: UserRegister) {
    if(user) {
      const key = crypto.enc.Base64.parse(environment.secret);
      user.password = crypto.AES.encrypt(user.password, key, {
          mode: crypto.mode.ECB,
          padding: crypto.pad.Pkcs7
      }).toString();
      user.cardNumber = crypto.AES.encrypt(user.cardNumber, key, {
        mode: crypto.mode.ECB,
        padding: crypto.pad.Pkcs7
      }).toString();
      user.contactDetail = crypto.AES.encrypt(user.contactDetail, key, {
        mode: crypto.mode.ECB,
        padding: crypto.pad.Pkcs7
      }).toString();
      user.address = crypto.AES.encrypt(user.address, key, {
        mode: crypto.mode.ECB,
        padding: crypto.pad.Pkcs7
      }).toString(); 
    }
  }
}
