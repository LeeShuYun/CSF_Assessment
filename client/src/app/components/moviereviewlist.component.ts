import { Component, OnDestroy, OnInit } from '@angular/core';
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
export class MoviereviewlistComponent implements OnInit, OnDestroy {
  query = "";
  queryParams$!: Subscription;

  reviewList: Review[] = [];
  isSearchResultReady: boolean = false;

  placeholderImg: string = "/assets/placeholder.jpg";

  hardcodedImg: string = "https://static01.nyt.com/images/2022/12/14/multimedia/14avatar1-1-6cf3/14avatar1-1-6cf3-mediumThreeByTwo440-v3.jpg"

  constructor(
    private activatedRoute: ActivatedRoute,
    private dataSvc: DataService) {
  }


  ngOnInit(): void {
    this.isSearchResultReady = false;

    console.log("loading movie review list");

    this.query = this.activatedRoute.snapshot.queryParams["query"];
    localStorage.setItem("query", this.query)
    // console.log("moviereviewlist query>>>", this.query)

    this.dataSvc.getSearch(this.query)
      .then(reviews => {
        this.reviewList = reviews;
        console.log("search result>>> ", this.reviewList)
        this.isSearchResultReady = true;
      });
  }
  ngOnDestroy(): void {
    // this.queryParams$.unsubscribe();
    // this.movieSub.unsubscribe();
    console.log("destroyed sub");
  }

}
