import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { isControlValid, isControlInvalid } from 'app/shared/utils/form.utils';
import { MessageService } from 'primeng/api';
import { UserRegister } from '../user.model';
import { UserService } from '../user.service';

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
    private messageService: MessageService
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
      type: new FormControl('', [Validators.required])
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
    this.userService.register(user).subscribe(
      () => {
        this.messageService.add({ key: 'tc', severity: 'success', summary: 'Success', detail: 'You have successful login!' });
      },
      (err) => {
        console.log(err);
        if (err.error.errCode === 'B104') {
          this.messageService.add({ key: 'tc', severity: 'error', summary: 'Fail', detail: err.error.errMsg });
        }
      }
    );
  }

}
