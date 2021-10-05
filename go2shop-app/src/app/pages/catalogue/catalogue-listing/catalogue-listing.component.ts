import { Component, OnInit } from '@angular/core';
import { CatalogueService } from '../catalogue.service';
import { HttpResponse } from '@angular/common/http';
import { IProduct, IProductSearch } from '../product.model';
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
  public totalCount: number;
  public productsToDisplay: IProduct[] = [];
  private page = 0;
  private DEFAULT_SIZE = 24;
  private size = this.DEFAULT_SIZE;
  private currSearchDTO: IProductSearch;

  ngOnInit(): void {
    this.catalogueService.getSearchBarListener().subscribe(
      (searchDTO: IProductSearch) => {
        this.page = 0;
        this.size = this.DEFAULT_SIZE;
        this.currSearchDTO = searchDTO;
        this.productsToDisplay = [];
        this.getProducts();
      }
    );
  }

  getProducts(): void {
    const pageInfo = {
      page: this.page,
      size: this.size
    };
    this.catalogueService.search(this.currSearchDTO, pageInfo).subscribe(
      (res: HttpResponse<IProduct[]>) => {
        this.productsToDisplay = this.productsToDisplay.concat(res.body);
        this.totalCount = +res.headers.get('X-Total-Count');
      }
    );
  }

  loadMore(): void {
    this.page += 1;
    this.getProducts();
  }
}
