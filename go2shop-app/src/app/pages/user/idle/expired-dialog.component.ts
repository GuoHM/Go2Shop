import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'expired-dialog',
  templateUrl: './expired-dialog.component.html'
})

export class ExpiredDialogComponent{

  constructor(
    public activeModal: NgbActiveModal,
  ) {}

}
  