import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from 'app/auth/authentication.service';
import { Subscription } from 'rxjs';
import { User } from '../../user.model';
import { UserService } from '../../user.service';
import { ILoginUser } from 'app/auth/authentication.model';
import { MatSlideToggleChange } from '@angular/material/slide-toggle';
import { Router } from '@angular/router';
import * as crypto from 'crypto-js';
import { environment } from 'environments/environment';

@Component({
  selector: 'go2shop-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  user: User;
  loginUserDetails: ILoginUser;
  private subscription: Subscription = new Subscription();

  constructor(
    private router: Router,
    private userService: UserService,
    private authenticationService: AuthenticationService
  ) { 
    this.retrieve2faState();
  }

  ngOnInit(): void {
    const userId = this.authenticationService.getCurrentUser().userId;
    this.subscription.add(this.userService.getUserByUserId(userId).subscribe(
      res => {
        this.user = res.body;
        this.decryptDetails(this.user);
      }
    ));
  }

  public toggle2fa(value: MatSlideToggleChange): void {
    this.userService.update2fa({ username: this.loginUserDetails.username, authEnabled: value.checked }).subscribe(
      (res: String) => {
        localStorage.setItem('currentUser', JSON.stringify({ ...this.loginUserDetails, authEnabled: value.checked }));
        if(res) {
          this.router.navigate(['/user', 'register2fa'], { queryParams: { qrCode: res, isRouteToLogin: false } })
        }
      }
    );
  }

  private retrieve2faState(): void {
    const details = localStorage.getItem('currentUser');
    if(details) {
      this.loginUserDetails = JSON.parse(details);
    }
  }

  private decryptDetails(user: User) {
    if(user) {
      const key = crypto.enc.Base64.parse(environment.secret);
      user.cardNumber = crypto.AES.decrypt(user.cardNumber, key, {
          mode: crypto.mode.ECB,
          padding: crypto.pad.Pkcs7
      }).toString(crypto.enc.Utf8);
      user.contactDetail = crypto.AES.decrypt(user.contactDetail, key, {
        mode: crypto.mode.ECB,
        padding: crypto.pad.Pkcs7
      }).toString(crypto.enc.Utf8);
      user.address = crypto.AES.decrypt(user.address, key, {
        mode: crypto.mode.ECB,
        padding: crypto.pad.Pkcs7
      }).toString(crypto.enc.Utf8); 
    }
  }
}
