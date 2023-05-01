import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { UserRoutingModule } from './user-routing.module';
import { LibrariesModule } from 'app/shared/libraries/libraries.module';
import { AuthenticationModule } from 'app/auth/authentication.module';
import { Register2FAComponent } from './register/register-2fa/register-2fa.component';
import { LoginOTPComponent } from './login/login-otp/login-otp.component';



@NgModule({
  declarations: [
    LoginComponent,
    LoginOTPComponent,
    RegisterComponent,
    Register2FAComponent
  ],
  imports: [
    CommonModule,
    LibrariesModule,
    UserRoutingModule,
    AuthenticationModule
  ]
})
export class UserModule { }
