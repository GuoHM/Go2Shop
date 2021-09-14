import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormControl } from '@angular/forms';

@Component({
  selector: 'go2shop-catalogue',
  templateUrl: './catalogue.component.html',
  styleUrls: ['./catalogue.component.css']
})
export class CatalogueComponent implements OnInit {

  constructor(
      private fb: FormBuilder
  ) { }

  public searchForm: FormGroup = this.fb.group({
      search: new FormControl('')
  });

  ngOnInit(): void {
  }
}