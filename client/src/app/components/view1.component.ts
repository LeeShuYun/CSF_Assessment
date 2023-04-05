import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-view1',
  templateUrl: './view1.component.html',
  styleUrls: ['./view1.component.css']
})
export class View1Component implements OnInit {
  params$ !: Subscription;

  constructor(private activatedRoute: ActivatedRoute) {

  }

  ngOnInit(): void {
    //for routing to the different city params
    this.params$ = this.activatedRoute.params.subscribe(
      (params) => {
        this.city = params['city'];
      }
    );
    //get the weather
    this.getWeatherFromAPI(this.city);
  }
  imageUrl: string = "https://hips.hearstapps.com/hmg-prod/images/dog-puppy-on-garden-royalty-free-image-1586966191.jpg?crop=0.752xw:1.00xh;0.175xw,0&resize=1200:*";


}
