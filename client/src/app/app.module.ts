import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { SearchreviewComponent } from './components/searchreview.component';
import { MoviereviewlistComponent } from './components/moviereviewlist.component';
import { PostcommentComponent } from './components/postcomment.component';
@NgModule({
  declarations: [
    AppComponent,
    SearchreviewComponent,
    MoviereviewlistComponent,
    PostcommentComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
