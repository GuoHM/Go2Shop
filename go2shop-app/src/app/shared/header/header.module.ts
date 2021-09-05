import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { AuthenticationModule } from 'app/auth/authentication.module';
import { LibrariesModule } from '../libraries/libraries.module';
import { HeaderComponent } from './header/header.component';

@NgModule({
  imports: [
    LibrariesModule,
    CommonModule,
    AuthenticationModule
  ],
  exports: [HeaderComponent],
  declarations: [
    HeaderComponent
  ],
  providers: [],
})
export class HeaderModule { }
