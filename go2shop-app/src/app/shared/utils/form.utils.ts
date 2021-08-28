import { AbstractControl } from '@angular/forms';

/**
 * This method is used to provde standardised behavior for FormControl validation check
 */
export const isControlValid = (control: AbstractControl): boolean => {
  return control.valid && (control.dirty || control.touched);
};

/**
 * This method is used to provde standardised behavior for FormControl validation check
 */
export const isControlInvalid = (control: AbstractControl): boolean => {
  return control.invalid && (control.dirty || control.touched);
};


