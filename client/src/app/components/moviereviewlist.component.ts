import { Component, OnInit } from '@angular/core';
import { Review } from '../models';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { DataService } from '../services/data.service';

@Component({
  selector: 'app-moviereviewlist',
  templateUrl: './moviereviewlist.component.html',
  styleUrls: ['./moviereviewlist.component.css']
})
//view 1
export class MoviereviewlistComponent implements OnInit {
  movieList: Review[] = [{
    title: "Godfather",
    rating: 1,
    summary: "YES",
    reviewUrl: "https://www.google.com/",
    numberOfComments: 2,
    imageUrl: "https://hips.hearstapps.com/hmg-prod/images/dog-puppy-on-garden-royalty-free-image-1586966191.jpg?crop=0.752xw:1.00xh;0.175xw,0&resize=1200:*"
  }];
  imageUrl: string = "/assets/placeholder.jpg";
  movieName = "";
  param$!: Subscription;
  characters!: Review[]

  constructor(private router: Router, private activatedRoute: ActivatedRoute,
    private dataSvc: DataService) {

  }

  ngOnInit(): void {
    this.param$ = this.activatedRoute.params.subscribe(
      async (params) => {
        this.movieName = params['movieName'];
        console.log(this.movieName);
        const search = await this.dataSvc.getSearch(this.movieName);
        const comments = await this.dataSvc.getNumberOfComments(this.movieName);
        console.log(search);
        if (search === undefined || search.length == 0) {
          this.router.navigate(['/'])
        } else {
          this.movieList = search;
        }

      }
    );
  }

  ngOnDestroy(): void {
    console.log("destroy sub");
    this.param$.unsubscribe();
  }

  // goToComments() {
  //   this.router.navigate(['comment']);
  // }
  // goBackToSearch() {
  //   this.router.navigate(['']);
  // }
}
