import { Component, ElementRef, HostListener, ViewChild } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Form } from '../models';
import { DataService } from '../services/data.service';
import { firstValueFrom } from 'rxjs';

@Component({
  selector: 'app-view0',
  templateUrl: './view0.component.html',
  styleUrls: ['./view0.component.css']
})
export class View0Component {
  form!: FormGroup;
  //for the dropdown menu
  cityList: string[] = [
    "Singapore",
    "Malaysia",
    "Paris"
  ];


  constructor(private fb: FormBuilder,
    private dataSvc: DataService,
    //private repo: SomeRepo,
    private router: Router,
    // private aRoute: ActivatedRoute
  ) {
    this.form = this.createForm();
  }


  submitForm() {
    const task: Form = {
      name: this.form.get('name')?.value,
      email: this.form.get('email')?.value,
      date: this.form.get('date')?.value,
      priority: this.form.get('priority')?.value,
      // image: this.form.get('image')?.value,
      choices: this.form.get('choices')?.value,
    };

    //do something with Task object
    console.log(task)

    this.form.reset();

    //this works!
    this.router.navigate(['/view1']);
  }



  isControlInvalid(ctrlName: string) {
    const ctrl = this.form.get(ctrlName) as FormControl;
    return ctrl.invalid && !ctrl.pristine;
  }

  createForm(): FormGroup {
    // this.taskArray = this.fb.array([]);
    return this.fb.group({
      name: this.fb.control('', [
        // Validators.required,
        // Validators.minLength(5),
        // this.nonWhiteSpace,

      ]),
      email: this.fb.control("", [
        // Validators.required,
        // Validators.email
      ]),

      date: this.fb.control("", [
        Validators.required,
        // this.isPastDate,
        // this.isolderThanThirteen
      ]), //end of date

      // priority default is low
      priority: this.fb.control('low',
        // Validators.required
      ),
      // image: this.fb.control("", [Validators.required]),
      choices: this.fb.control("", [
        // Validators.required
      ]),
      // 'image-file': this.fb.control('', [Validators.required]),
    });
  }

  //custom validators ============
  readonly nonWhiteSpace = (ctrl: AbstractControl) => {
    if (ctrl.value.trim().length > 0) return null;
    return { nonWhiteSpace: true } as ValidationErrors;
  };

  readonly isolderThanThirteen = (ctrl: AbstractControl) => {
    console.log(ctrl)
    const selectedDate: number = new Date(ctrl.value).getFullYear();
    const currentDate = new Date().getFullYear();
    let age = currentDate - selectedDate;
    return (age > 13) ? null : { invalidDate: true };
  };

  readonly isPastDate = (ctrl: AbstractControl) => {
    const selectedDate = new Date(ctrl.value);
    const currentDate = new Date();
    //if selected Date is future, return set error
    return selectedDate <= currentDate ? null : { invalidDate: true };
  };
}

