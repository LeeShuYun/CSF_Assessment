import { Component, OnInit } from '@angular/core';
import { Review } from '../models';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable, Subscription, firstValueFrom } from 'rxjs';
import { DataService } from '../services/data.service';

@Component({
  selector: 'app-moviereviewlist',
  templateUrl: './moviereviewlist.component.html',
  styleUrls: ['./moviereviewlist.component.css']
})
//view 1
export class MoviereviewlistComponent implements OnInit {
  // movieList: Review[] = [{
  //   title: "Godfather",
  //   rating: 1,
  //   summary: "YES",
  //   reviewUrl: "https://www.google.com/",
  //   numberOfComments: 2,
  //   imageUrl: "https://hips.hearstapps.com/hmg-prod/images/dog-puppy-on-garden-royalty-free-image-1586966191.jpg?crop=0.752xw:1.00xh;0.175xw,0&resize=1200:*"
  // }];
  query = "";
  queryParams$!: Subscription;
  // movieSub!: Subscription;

  fids!: string[];
  movieList: Review[] = [];

  imageUrl: string = "/assets/placeholder.jpg";
  // searchDirect!: Observable<any>;

  constructor(
    private activatedRoute: ActivatedRoute,
    private dataSvc: DataService) {
  }

  ngOnInit(): void {
    console.log("loading movie review list");

    this.queryParams$ = this.activatedRoute.queryParams.subscribe(
      async (queryParams) => {
        //storing query params for when we come back from view 2 comment page
        if (this.query == null) {
          this.query = queryParams['query'];
        }
        console.log("query>>>", this.query);

        //grab the data from API. should come back as Review[]
        await this.dataSvc.getSearch(this.query)
          .then(server_response => {
            if (server_response === undefined || server_response.length == 0) {
              //do nothing
              console.log("no response...?")
            } else {
              this.movieList = server_response
              console.log("search result>>> ", server_response);
            }
          }
          )
          .catch(error =>
            console.log("error", error)
          )

      }
    );
    //subscribe to the Observable of the matrix that is the activated Route
  } //end nginit

  ngOnDestroy(): void {
    this.queryParams$.unsubscribe();
    // this.movieSub.unsubscribe();
    console.log("destroyed sub");
  }
}
