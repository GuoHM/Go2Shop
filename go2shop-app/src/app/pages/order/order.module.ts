import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LibrariesModule } from 'app/shared/libraries/libraries.module';
import { AuthenticationModule } from 'app/auth/authentication.module';



@NgModule({
  declarations: [
  ],
  imports: [
    CommonModule,
    LibrariesModule,
    AuthenticationModule
  ]
})
export class OrderModule { }
