import { Component, OnInit } from '@angular/core';
import { CatalogueService } from '../catalogue.service';
import { HttpResponse } from '@angular/common/http';
import { IProduct } from '../product.model';
import { FormBuilder, FormGroup, FormControl } from '@angular/forms';

@Component({
  selector: 'go2shop-catalogue-listing',
  templateUrl: './catalogue-listing.component.html',
  styleUrls: ['./catalogue-listing.component.css']
})
export class CatalogueListingComponent implements OnInit {

  constructor(
      private fb: FormBuilder,
      private catalogueService: CatalogueService
  ) { }

  public searchForm: FormGroup = this.fb.group({
      search: new FormControl('')
  });
  public productsToDisplay: IProduct[] = [];

  ngOnInit(): void {
    this.catalogueService.getCatalogue().subscribe(
        (res: HttpResponse<IProduct[]>) => {
            this.productsToDisplay = this.productsToDisplay.concat(res.body);
        }
    );
  }
}
