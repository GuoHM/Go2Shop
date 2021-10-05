import { Component } from '@angular/core';
import { FormBuilder, FormGroup, FormControl, AbstractControl } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { CatalogueService } from './catalogue.service';
import { IProductSearch, ProductSearch } from './product.model';

@Component({
  selector: 'go2shop-catalogue',
  templateUrl: './catalogue.component.html',
  styleUrls: ['./catalogue.component.css']
})
export class CatalogueComponent {

  constructor(
      private fb: FormBuilder,
      private catService: CatalogueService,
      private router: Router,
      private route: ActivatedRoute
  ) { }

  public searchForm: FormGroup = this.fb.group({
    search: new FormControl('')
  });

  get searchTerm(): AbstractControl {
    return this.searchForm.get('search');
  }

  public search(): void {
    this.catService.updateSearchDTO(this.createSearchDTO());
    this.router.navigate([''], { relativeTo: this.route });
  }

  private createSearchDTO(): IProductSearch {
    return new ProductSearch(this.searchTerm.value);
  }
}