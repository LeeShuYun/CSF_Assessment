import { Component, ElementRef, HostListener, OnInit, ViewChild } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { DataService } from '../services/data.service';
import { Observable, debounceTime, firstValueFrom, map, startWith, tap } from 'rxjs';

// const nonWhiteSpace = (ctrl: AbstractControl) => {
//   if (ctrl.value.trim().length > 0) return null;
//   return { nonWhiteSpace: true } as ValidationErrors;
// };

@Component({
  selector: 'app-searchreview',
  templateUrl: './searchreview.component.html',
  styleUrls: ['./searchreview.component.css']
})
export class SearchreviewComponent implements OnInit {
  form!: FormGroup;
  // printtest!: Observable<any>;

  constructor(private fb: FormBuilder,
    private router: Router,
    private aRoute: ActivatedRoute,
    private dataSvc: DataService
  ) {
  }
  ngOnInit(): void {
    this.form = this.createForm();
  }


  submitForm() {
    const query = this.form.get('name')?.value;
    this.dataSvc.setSearch(query)
    console.log("view0 queryString>>> ", query);
    this.form.reset();
    this.router.navigate(['/search'], {
      queryParams: { query }
    });
  }


  createForm(): FormGroup {
    return this.fb.group({
      name: this.fb.control('', [
        Validators.required,
        Validators.minLength(2),
        // nonWhiteSpace,
      ])
    });
  }

  isControlInvalid(ctrlName: string) {
    const ctrl = this.form.get(ctrlName) as FormControl;
    return ctrl.invalid && !ctrl.pristine;
  }


}
