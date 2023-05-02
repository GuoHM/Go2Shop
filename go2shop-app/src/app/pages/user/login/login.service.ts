import { Injectable } from '@angular/core';
import { UserLogin } from '../user.model';
import { UserService } from '../user.service';
import { AuthenticationService } from 'app/auth/authentication.service';
import { ShoppingCartService } from 'app/pages/cart/shopping-cart.service';
import { MessageService } from 'primeng/api';
import { Router } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { IUserToken } from 'app/auth/authentication.model';
import { UserIdleService } from '../idle/user-idle.service';
import * as crypto from 'crypto-js';
import { environment } from 'environments/environment';

@Injectable({
  providedIn: 'root'
})
export class LoginService {
    private userlogin: UserLogin;

    constructor(
        private userService: UserService,
        private authenticationService: AuthenticationService,
        private cartService: ShoppingCartService,
        private messageService: MessageService,
        private userIdleService: UserIdleService,
        private router: Router
    ) {}
    
    setUserLoginDetails(userlogin: UserLogin): void {
        this.userlogin = userlogin;
    }

    getUserLoginDetails(): UserLogin {
        return this.userlogin;
    }

    login(): void {
        if(this.userlogin) {
            const loginDetails: UserLogin = this.createUserLogin();
            this.userService.login(loginDetails).subscribe(
                (res: HttpResponse<boolean>) => {
                    if(res.body) {
                        this.userService.retrieveToken(loginDetails).subscribe(
                            (tokenRes: HttpResponse<IUserToken>) => {
                                this.userIdleService.startUserIdle();
                                this.authenticationService.handleLoginSuccess(tokenRes.body);
                                this.messageService.add({key: 'tc', severity:'success', summary:'Success', detail: 'You have successful login!'});
                                this.cartService.updateCartSize();
                                setTimeout(() => {
                                    this.router.navigate(['/catalogue']);
                                }, 2000);
                            }
                        )
                    }
                },
                (err) => {
                    if (err.error.errCode === 'B102' || err.error.errCode === 'B105') {
                        this.messageService.add({ key: 'tc', severity: 'error', summary: 'Fail', detail: err.error.errMsg });
                    }
                }
            );
        }
    }

    private createUserLogin(): UserLogin {
        const copiedLoginDetails: UserLogin = { ...this.userlogin };
        this.encryptDetails(copiedLoginDetails);
        return copiedLoginDetails;
    }

    private encryptDetails(loginDetails: UserLogin) {
        if(loginDetails) {
            const key = crypto.enc.Base64.parse(environment.secret);
            loginDetails.password = crypto.AES.encrypt(loginDetails.password, key, {
                mode: crypto.mode.ECB,
                padding: crypto.pad.Pkcs7
            }).toString();
            if(loginDetails.otp) {
                loginDetails.otp = crypto.AES.encrypt(loginDetails.otp, key, {
                    mode: crypto.mode.ECB,
                    padding: crypto.pad.Pkcs7
                }).toString();
            }
        }
    }
}