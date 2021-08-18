import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'go2shop-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  password: string;

  constructor() { }

  ngOnInit(): void {
  }

}
