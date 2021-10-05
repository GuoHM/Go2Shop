import { NgModule } from '@angular/core';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { CarouselModule } from 'primeng/carousel';
import { MenubarModule } from 'primeng/menubar';
import { InputTextModule } from 'primeng/inputtext';
import { PasswordModule } from 'primeng/password';
import { MessageService } from 'primeng/api';
import { ToastModule } from 'primeng/toast';
import { RadioButtonModule } from 'primeng/radiobutton';
import { AvatarModule } from 'primeng/avatar';
import { MenuModule } from 'primeng/menu';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FileUploadModule } from 'primeng/fileupload';

@NgModule({
  imports: [
    CardModule,
    MenubarModule,
    ButtonModule,
    InputTextModule,
    PasswordModule,
    RadioButtonModule,
    AvatarModule,
    MenuModule,
    FormsModule,
    ReactiveFormsModule,
    CarouselModule,
    NgbModule,
    FileUploadModule
  ],
  exports: [
    CardModule,
    MenubarModule,
    ButtonModule,
    InputTextModule,
    PasswordModule,
    ToastModule,
    RadioButtonModule,
    AvatarModule,
    MenuModule,
    FormsModule,
    ReactiveFormsModule,
    CarouselModule,
    NgbModule,
    FileUploadModule
  ],
  declarations: [],
  providers: [MessageService],
})
export class LibrariesModule { }
