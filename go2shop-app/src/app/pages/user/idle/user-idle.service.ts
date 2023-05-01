import { Component, Injectable } from '@angular/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ExpiredDialogComponent } from './expired-dialog.component';
import { WarnDialogComponent } from './warn-dialog.component';
import { DEFAULT_INTERRUPTSOURCES, Idle } from '@ng-idle/core';
import { AuthenticationService } from 'app/auth/authentication.service';


@Injectable({
  providedIn: 'root'
})

export class UserIdleService {
  idleTime: number;
  maxTime: number;
  idleTimeSetting = { WARN: 3, MAX: 8 };
  private idleModalRef: NgbModalRef;

  constructor(
    private modalService: NgbModal,
    private authService: AuthenticationService,
    private idle: Idle
  ){
    this.idleModalRef = null;
  }

  startUserIdle(): void {
    if (!this.idle.isRunning() && !this.idle.isIdling()) {
      this.constructUserIdle();
    } else {
      this.resetUserIdle();
    }
  }

  private resetUserIdle(): void {
    this.idle.watch();
  }

  private async constructUserIdle(): Promise<void>{
    if(this.idleTimeSetting != null){
      this.idleTime = Number(this.idleTimeSetting.WARN);
      this.maxTime = Number(this.idleTimeSetting.MAX);

      const leftTimeSeconds  = this.maxTime - this.idleTime;
      this.idle.setIdle(this.idleTime);
      this.idle.setTimeout(leftTimeSeconds);
      this.idle.setInterrupts(DEFAULT_INTERRUPTSOURCES);

      this.idle.onIdleStart.subscribe(() => {
        if(this.idleModalRef){
          this.idleModalRef.dismiss('cancel');
        }
        this.idleModalRef = this.modalService.open(WarnDialogComponent as Component, { centered: true, size: 'lg', windowClass: 'xlModal', backdrop: 'static'});
        this.idleModalRef.componentInstance.isIdle = true;
      });

      this.idle.onIdleEnd.subscribe(() => {
        if(this.idleModalRef){
          this.idleModalRef.dismiss('cancel');
        }
      });

      this.idle.onTimeoutWarning.subscribe(seconds => {
        if(this.idleModalRef){
          this.idleModalRef.componentInstance.updateSeconds(seconds);
        }
      });

      this.idle.onTimeout.subscribe(() => {
        this.modalService.dismissAll();
        const expiredDialog = this.modalService.open(ExpiredDialogComponent as Component, { centered: true, size: 'lg', windowClass: 'xlModal', backdrop: 'static', });
        setTimeout(() => {
          if(expiredDialog) {
            expiredDialog.dismiss();
          }
        }, 1000);
        this.logout();   
      });

      this.resetUserIdle();
    }
  }

  logout(): void {
    this.authService.logout();
  }
}
