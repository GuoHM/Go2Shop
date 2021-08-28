import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { isControlValid, isControlInvalid } from 'app/shared/utils/form.utils';

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
    private formBuilder: FormBuilder
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
      contactNumber: new FormControl('', [Validators.required]),
      cardNumber: new FormControl('', [Validators.required]),
      type: new FormControl('', [Validators.required])
    });
  }

  /**
  * Returns an accessible control of the codeset form controls
  */
  get form(): any { return this.registerForm.controls; }

  register(): void {
    this.registerForm.markAllAsTouched();
  }

}
