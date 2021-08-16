import { NgModule } from '@angular/core';
import { LibrariesModule } from '../libraries/libraries.module';
import { HeaderComponent } from './header/header.component';



@NgModule({
  imports: [LibrariesModule],
  exports: [HeaderComponent],
  declarations: [
    HeaderComponent
  ],
  providers: [],
})
export class HeaderModule { }
