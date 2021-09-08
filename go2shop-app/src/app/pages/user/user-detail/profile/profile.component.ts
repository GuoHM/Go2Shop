import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from 'app/auth/authentication.service';
import { Subscription } from 'rxjs';
import { User } from '../../user.model';
import { UserService } from '../../user.service';

@Component({
  selector: 'go2shop-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  user: User;
  private subscription: Subscription = new Subscription();

  constructor(
    private userService: UserService,
    private authenticationService: AuthenticationService
  ) { }

  ngOnInit(): void {
    const userId = this.authenticationService.getCurrentUser().userId;
    this.subscription.add(this.userService.getUserByUserId(userId).subscribe(
      res => {
        this.user = res.body;
      }
    ));
  }

}
