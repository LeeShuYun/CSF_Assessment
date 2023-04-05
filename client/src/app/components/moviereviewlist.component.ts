import { Component, OnInit } from '@angular/core';
import { Review } from '../models';
import { Router } from '@angular/router';

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

  constructor(private router: Router) { }

  ngOnInit(): void {

  }

  goToComments() {
    this.router.navigate(['comment']);
  }
  goBackToSearch() {
    this.router.navigate(['']);
  }
}
