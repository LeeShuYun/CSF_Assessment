import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'myApp';

  constructor() {
    // TODO
    // DO NOT PUT ANY data fetching methods here!! constructor is normally for initing static and reusable classes like services or routing
    // stays in the memory of the app for the whole runtime
    // park data fetching code inside NgOnInit instead
    //try using NgAfterView for leaving the page.
  }
  //do routing in app-routing.module. Wire them all up

  //delete any extra components by going to app.module.ts and components folder.

  //start with creating the service you're going to use
  //ng g s --skip-tests services/contact

  //serve with proxy to test with localhost:
  // ng serve --proxy-config proxy-config.js

  //add PWA  if need
  // ng add @angular/pwa

  //add webshare if need
  //npm install --save ng-web-share

  //add dexie  if need
  //npm install dexie

  //I already added the ng build files to the /static folder, just delete when you build a new one

}
