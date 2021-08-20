import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';

@Component({
  selector: 'go2shop-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

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
      username: new FormControl(''),
      passowrd: new FormControl(''),
      passwordRepeat: new FormControl('')
    });
  }



}
