import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators, FormBuilder } from '@angular/forms';
import { AuthenticationService } from 'app/auth/authentication.service';
import { isControlValid, isControlInvalid } from 'app/shared/utils/form.utils';
import { MessageService } from 'primeng/api';
import { CatalogueCreate, ProductImage } from './catalogue-create.model';
import { CatalogueCreateService } from './catalogue-create.service';

@Component({
  selector: 'go2shop-catalogue-create',
  templateUrl: './catalogue-create.component.html',
  styleUrls: ['./catalogue-create.component.css']
})
export class CatalogueCreateComponent implements OnInit {

  public imagePath;
  imgURL: any;
  public message: string;
  catalogueCreateForm: FormGroup;
  public files: any[];
  private productImage :ProductImage[] = [];

  isValid = isControlValid;
  isInvalid = isControlInvalid;

  constructor(
    private messageService: MessageService,
    private catalogueCreateService: CatalogueCreateService,
    private formBuilder: FormBuilder,
    private authService: AuthenticationService
  ) { }

  ngOnInit(): void {
    this.newForm();
  }

  /**
  * Returns an accessible control of the codeset form controls
  */
  get form(): any { return this.catalogueCreateForm.controls; }

  private newForm(): void {
    this.catalogueCreateForm = this.formBuilder.group({
      name: new FormControl('', [Validators.required]),
      description: new FormControl('', [Validators.required]),
      price: new FormControl('', [Validators.required]),
      stock: new FormControl('', [Validators.required])
    });
  }

  preview(files): void {
    if (files.length === 0)
      return;

    const mimeType = files[0].type;
    if (mimeType.match(/image\/*/) == null) {
      this.message = 'Only images are supported.';
      return;
    }

    const reader = new FileReader();
    this.imagePath = files;
    reader.readAsDataURL(files[0]);
    reader.onload = () => {
      this.imgURL = reader.result;
    };
    this.files = files;
  }

  uploadImage(): void {
    this.catalogueCreateService.uploadImage(this.files[0]).subscribe(
      (res) => {
        this.productImage.push(new ProductImage(res.body));
        this.messageService.add({severity: 'info', summary: 'File Uploaded', detail: ''});
      }
    );
  }

  createCatalogue(): void {
    if (this.productImage.length) {
      this.messageService.add({severity: 'error', summary: 'Create Fail', detail: 'Please upload image'});
      return;
    }
    const catalogueCreate: CatalogueCreate = new CatalogueCreate();
    catalogueCreate.name = this.catalogueCreateForm.get('name').value;
    catalogueCreate.description = this.catalogueCreateForm.get('description').value;
    catalogueCreate.price = this.catalogueCreateForm.get('price').value;
    catalogueCreate.stock = this.catalogueCreateForm.get('stock').value;
    catalogueCreate.productImages = this.productImage;
    catalogueCreate.userId = this.authService.getCurrentUser().userId;
    this.catalogueCreateService.createCatalogue(catalogueCreate).subscribe(
      () => {
        this.messageService.add({severity: 'success', summary: 'Catalogue has been created', detail: ''});
      }
    );
  }

}
