import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { Register2FAComponent } from './register/register-2fa/register-2fa.component';
import { LoginOTPComponent } from './login/login-otp/login-otp.component';

const routes: Routes = [
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'login/otp',
    component: LoginOTPComponent
  },
  {
    path: 'register2fa',
    component: Register2FAComponent
  },
  {
    path: 'register',
    component: RegisterComponent
  },
  {
    path: 'detail',
    loadChildren: () => import('./user-detail/user-detail.module').then(m => m.UserDetailModule)
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UserRoutingModule { }
