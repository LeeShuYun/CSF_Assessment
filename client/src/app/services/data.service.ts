import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { firstValueFrom } from 'rxjs';
import { Comment, Review } from '../models';

@Injectable({
  providedIn: 'root'
})
export class DataService {
  movieName: string = "";
  // private ENDPOINT: string = "/api";
  constructor(private httpClient: HttpClient) { }

  //GET /api/search?query=<movie name>
  //accept json
  getSearch(movieName: string): Promise<any> {
    const headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');
    const params = new HttpParams()
      .set("query", movieName);
    this.movieName = movieName;
    return firstValueFrom(
      // this.httpClient.get('http://localhost:8080/api/search', { params, headers }));
      this.httpClient.get('/api/search', { params, headers }));


  }

  //GET /api/comment. returns number
  getNumberOfComments(movieName: string): Promise<any> {
    console.log("getting Comment count")
    return firstValueFrom(
      // this.httpClient.get('http://localhost:8080/api/comment'));
      this.httpClient.get('/api/commentNum'));
  }

  //POST /api/commentinsert
  postComment(comment: Comment): Promise<any> {
    let body = new URLSearchParams();
    body.set('movieName', this.movieName);
    body.set('name', comment.name);
    body.set('rating', comment.rating.toString());
    body.set('comment', comment.comment);

    const headers = new HttpHeaders().set('Content-Type', 'application/x-www-form-urlencoded');

    // const payload = JSON.stringify(comment);
    // console.log("dataSvc comment payload>>", payload)

    return firstValueFrom(
      // this.httpClient.post('http://localhost:8080/api/insertcomment', body));
      this.httpClient.post('api/comment', body, { headers }));
  }
}

