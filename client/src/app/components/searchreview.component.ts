import { Component, ElementRef, HostListener, OnInit, ViewChild } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { DataService } from '../services/data.service';
import { Observable, debounceTime, firstValueFrom, map, startWith, tap } from 'rxjs';

@Component({
  selector: 'app-searchreview',
  templateUrl: './searchreview.component.html',
  styleUrls: ['./searchreview.component.css']
})
export class SearchreviewComponent implements OnInit {
  form!: FormGroup;
  printtest!: Observable<any>;

  constructor(private fb: FormBuilder,
    private dataSvc: DataService,
    //private repo: SomeRepo,
    private router: Router,
    private aRoute: ActivatedRoute
  ) {
    this.form = this.createForm();
  }
  ngOnInit(): void {
    this.printtest = this.form.valueChanges.pipe(debounceTime(500));
    
  }


  submitForm() {
    const name = this.form.get('name')?.value;
    console.log("name>>> ", name);

    this.dataSvc.getSearch(name);
    console.log()
    this.form.reset();
    this.router.navigate(['/view1', name]);


  }


  createForm(): FormGroup {
    // this.taskArray = this.fb.array([]);
    return this.fb.group({
      name: this.fb.control('', [
        Validators.required,
        Validators.minLength(2),
        // this.nonWhiteSpace,
      ])
    });
  }

  isControlInvalid(ctrlName: string) {
    const ctrl = this.form.get(ctrlName) as FormControl;
    return ctrl.invalid && !ctrl.pristine;
  }

  readonly nonWhiteSpace = (ctrl: AbstractControl) => {
    if (ctrl.value.trim().length > 0) return null;
    return { nonWhiteSpace: true } as ValidationErrors;
  };
}
