import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { LibrariesModule } from '../libraries/libraries.module';
import { HeaderComponent } from './header/header.component';

@NgModule({
  imports: [
    LibrariesModule,
    CommonModule,
  ],
  exports: [HeaderComponent],
  declarations: [
    HeaderComponent
  ],
  providers: [],
})
export class HeaderModule { }
