import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { HasAnyRoleDirective } from './has-any-role.directive';

@NgModule({
  imports: [CommonModule],
  exports: [HasAnyRoleDirective],
  declarations: [HasAnyRoleDirective],
  providers: [],
})
export class AuthenticationModule { }
