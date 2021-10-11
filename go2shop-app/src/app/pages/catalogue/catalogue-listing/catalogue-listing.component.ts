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

  public recommendedProductsTotalCount: number;
  public recommendedProducts: IProduct[];
  private recommendedProductsPage = 0;
  private recommendedProductsInitialSize = 6;
  private isSeeMoreClicked = false;
  public isSearchBarSearching = false;

  ngOnInit(): void {
    this.catalogueService.getSearchBarListener().subscribe(
      (searchDTO: IProductSearch) => {
        this.isSeeMoreClicked = false;
        this.page = 0;
        this.size = this.DEFAULT_SIZE;
        this.currSearchDTO = searchDTO;
        this.productsToDisplay = [];
        this.getProducts();
        if(!searchDTO) {
          this.isSearchBarSearching = false;
          this.getRecommendedProducts(this.createRecommendedProductsPage(this.recommendedProductsInitialSize));
        } else {
          this.isSearchBarSearching = true;
        }
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
    if(!this.isSeeMoreClicked) {
      this.page += 1;
      this.getProducts();
    } else {
      this.recommendedProductsPage += 1;
      this.getRecommendedProducts(this.createRecommendedProductsPage(this.DEFAULT_SIZE));
    }
  }

  seeMore(): void {
    this.isSeeMoreClicked = true;
    this.productsToDisplay = [];
    this.getRecommendedProducts(this.createRecommendedProductsPage(this.DEFAULT_SIZE));
  }

  createRecommendedProductsPage(size: number): any {
    return {
      page: this.recommendedProductsPage,
      size: size
    };
  }

  getRecommendedProducts(pageInfo: any): void {
    this.catalogueService.getRecommendedProducts(pageInfo).subscribe(
      res => {
        if(res && res.body) {
          this.recommendedProductsTotalCount = +res.headers.get('X-Total-Count');
          if(!this.isSeeMoreClicked) {
            this.recommendedProducts = res.body;
          } else {
            this.productsToDisplay = this.productsToDisplay.concat(res.body);
          }
        }
      }
    );
  }
}
