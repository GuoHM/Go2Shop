import { Component, EventEmitter, Output } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'warn-dialog',
  templateUrl: './warn-dialog.component.html'
})

export class WarnDialogComponent{

  leftTime: string;
  isIdle: boolean;

  constructor(
        public activeModal: NgbActiveModal
  )
  {}

    @Output()
      okOnClick: EventEmitter<any> = new EventEmitter();
    
    updateSeconds(seconds: number): void{
      if(seconds >= 0){
        this.leftTime = new Date(seconds * 1000).toISOString().substring(14,19);
      }
    }

    close(): void{
      this.activeModal.dismiss('cancel');
      this.okOnClick.emit(true);
    }
    
}