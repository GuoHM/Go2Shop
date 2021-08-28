import {Directive, Input, TemplateRef, ViewContainerRef} from '@angular/core';
import {AuthenticationService} from './authentication.service';

@Directive({
  selector: '[hasAnyRole]'
})
export class HasAnyRoleDirective {
  constructor(private templateRef: TemplateRef<any>, private viewContainer: ViewContainerRef, private authenticationService: AuthenticationService) {
  }

  @Input()
  set hasAnyRole(roles: string[]) {
    for (const role of roles) {
      if (this.authenticationService.hasRole(role)) {
        this.viewContainer.createEmbeddedView(this.templateRef);
        return;
      } 
    }
    this.viewContainer.clear();
  }
}