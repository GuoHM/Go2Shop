import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { LibrariesModule } from './shared/libraries/libraries.module';
import { HeaderModule } from './shared/header/header.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { CommonModule } from '@angular/common';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AuthenticationInterceptor } from './auth/authentication.interceptor';
import { NgIdleModule } from '@ng-idle/core';
import { ExpiredDialogComponent } from './pages/user/idle/expired-dialog.component';
import { WarnDialogComponent } from './pages/user/idle/warn-dialog.component';

@NgModule({
  declarations: [
    AppComponent,
    WarnDialogComponent,
    ExpiredDialogComponent
  ],
  imports: [
    CommonModule,
    BrowserModule,
    AppRoutingModule,
    LibrariesModule,
    HeaderModule,
    BrowserAnimationsModule,
    HttpClientModule,
    NgIdleModule.forRoot()
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: AuthenticationInterceptor, multi: true },
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
