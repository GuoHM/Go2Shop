import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { UserRoutingModule } from './user-routing.module';
import { LibrariesModule } from 'app/shared/libraries/libraries.module';
import { AuthenticationModule } from 'app/auth/authentication.module';



@NgModule({
  declarations: [
    LoginComponent,
    RegisterComponent
  ],
  imports: [
    CommonModule,
    LibrariesModule,
    UserRoutingModule,
    AuthenticationModule
  ]
})
export class UserModule { }
