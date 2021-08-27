import { NgModule } from '@angular/core';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';

import { MenubarModule } from 'primeng/menubar';
import { InputTextModule } from 'primeng/inputtext';
import { PasswordModule } from 'primeng/password';
import { MessageService } from 'primeng/api';


@NgModule({
  imports: [
    CardModule,
    MenubarModule,
    ButtonModule,
    InputTextModule,
    PasswordModule
  ],
  exports: [
    CardModule,
    MenubarModule,
    ButtonModule,
    InputTextModule,
    PasswordModule
  ],
  declarations: [],
  providers: [MessageService],
})
export class LibrariesModule { }
