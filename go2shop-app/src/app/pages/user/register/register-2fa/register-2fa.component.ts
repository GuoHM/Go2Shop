import { Location } from '@angular/common';
import { Component } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';

@Component({
  selector: 'go2shop-register',
  template: `
  <div class="row">
    <div class="col-10 col-sm-10 col-md-8 col-lg-5 mx-auto">
      <div class="card card-signin my-5">
        <div class="card-body">
          <div id="qr" class="d-flex justify-content-center flex-column">
            <p class="text-align-center">
                Scan this Barcode using Google Authenticator app on your phone 
                to use it later in login
            </p>
            <div class="d-flex justify-content-center flex-column">
              <div class="d-flex justify-content-center">
                <img [src]="qrCode"/>
              </div>
              <div class="d-flex justify-content-center">
                <button class="btn btn-primary" (click)="return()">{{ isRouteToLogin ? 'Go To Login Page' : 'Return' }}</button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  `,
  styleUrls: ['./register-2fa.component.css']
})
export class Register2FAComponent {
  public qrCode: string;
  public isRouteToLogin: boolean = true;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private location: Location
  ) { 
    this.route.queryParams.subscribe((param: Params) => {
      this.qrCode = param.qrCode
      this.isRouteToLogin = JSON.parse(param.isRouteToLogin);
    });
  }

  public return(): void {
    if(this.isRouteToLogin) {
      this.router.navigate(['/user', 'login']);
    } else {
      this.location.back();
    }
  }
}
