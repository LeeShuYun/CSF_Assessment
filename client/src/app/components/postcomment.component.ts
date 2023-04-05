import { Component, OnDestroy, OnInit } from '@angular/core';
import { AbstractControl, Form, FormBuilder, FormControl, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { DataService } from '../services/data.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Comment, Review } from '../models';
import { Observable, Subscription, debounceTime } from 'rxjs';

@Component({
  selector: 'app-postcomment',
  templateUrl: './postcomment.component.html',
  styleUrls: ['./postcomment.component.css']
})
export class PostcommentComponent implements OnInit, OnDestroy {
  form!: FormGroup;
  movieName!: string;
  movieList!: Review[]
  printtest!: Observable<any>;

  constructor(private fb: FormBuilder,
    private dataSvc: DataService,
    //private repo: SomeRepo,
    private router: Router,
    private aRoute: ActivatedRoute
  ) {

  }

  ngOnInit(): void {
    this.form = this.createForm();
    this.printtest = this.form.valueChanges.pipe(debounceTime(500));
  }

  postComment() {
    const comment: Comment = {
      movieName: this.movieName,
      name: this.form.get('name')?.value,
      rating: this.form.get('rating')?.value,
      comment: this.form.get('comment')?.value,
    };

    console.log(comment)

    this.dataSvc.postComment(comment);

    this.form.reset();

    this.router.navigate(['/view1', this.movieName]);

  }

  isControlInvalid(ctrlName: string) {
    const ctrl = this.form.get(ctrlName) as FormControl;
    return ctrl.invalid && !ctrl.pristine;
  }

  createForm(): FormGroup {
    return this.fb.group({
      name: this.fb.control('', [
        Validators.required,
        Validators.minLength(3),
        this.nonWhiteSpace,

      ]),
      rating: this.fb.control("", [
        Validators.required,
        this.between1to5,
      ]),

      comment: this.fb.control("", [
        Validators.required,
      ])
    });
  }

  //custom validators ============
  readonly nonWhiteSpace = (ctrl: AbstractControl) => {
    if (ctrl.value.trim().length > 0) return null;
    return { nonWhiteSpace: true } as ValidationErrors;
  };
  readonly between1to5 = (ctrl: AbstractControl) => {
    if (ctrl.value >= 1 && ctrl.value <= 5) return null;
    return { ratingNot1to5: true } as ValidationErrors;
  };

  //destroy ==================
  ngOnDestroy() {
  }
}
