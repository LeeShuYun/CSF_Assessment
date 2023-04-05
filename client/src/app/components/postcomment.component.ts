import { Component, OnDestroy, OnInit } from '@angular/core';
import { AbstractControl, Form, FormBuilder, FormControl, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { DataService } from '../services/data.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Comment, Review } from '../models';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-postcomment',
  templateUrl: './postcomment.component.html',
  styleUrls: ['./postcomment.component.css']
})
export class PostcommentComponent implements OnInit, OnDestroy {
  form!: FormGroup;
  urlParams$!: Subscription;
  movieName!: string;
  movieList! : Review[]

  constructor(private fb: FormBuilder,
    private dataSvc: DataService,
    //private repo: SomeRepo,
    private router: Router,
    private aRoute: ActivatedRoute
  ) {

  }

  ngOnInit(): void {
    this.form = this.createForm();
    this.urlParams$ = this.aRoute.params.subscribe(
      async (params) => {
        this.movieName = params['movie'];
        console.log(this.movieName);
        const l = await this.dataSvc.getSearch(this.movieName);
        console.log(l);
        if (l === undefined || l.length == 0) {
          this.router.navigate(['/'])
        }else{
            this.movieList = l;
        }

      }
    );
  }


  postComment() {
    const comment: Comment = {
      movieName: this.movieName,
      name: this.form.get('name')?.value,
      rating: this.form.get('rating')?.value,
      comment: this.form.get('comment')?.value,
    };

    //do something with Task object
    console.log(comment)

    this.form.reset();

    //this works!
    this.router.navigate(['/search']);
  }



  isControlInvalid(ctrlName: string) {
    const ctrl = this.form.get(ctrlName) as FormControl;
    return ctrl.invalid && !ctrl.pristine;
  }

  createForm(): FormGroup {


    return this.fb.group({
      name: this.fb.control('', [
        // Validators.required,
        // Validators.minLength(5),
        // this.nonWhiteSpace,

      ]),
      rating: this.fb.control("", [
        // Validators.required,
        // Validators.email
      ]),

      comment: this.fb.control("", [
        Validators.required,

      ]), //end of date
    });
  }

  //custom validators ============
  readonly nonWhiteSpace = (ctrl: AbstractControl) => {
    if (ctrl.value.trim().length > 0) return null;
    return { nonWhiteSpace: true } as ValidationErrors;
  };

  //destroy ==================
  ngOnDestroy() {
    this.urlParams$.unsubscribe();
  }
}
