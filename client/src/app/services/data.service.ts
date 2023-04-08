import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, firstValueFrom, lastValueFrom } from 'rxjs';
import { Comment, Review } from '../models';

@Injectable({
  providedIn: 'root'
})
export class DataService {
  //save the search
  searchTerm!: string;
  apiKey = "yo7GlM2J6KUIeCpoSt3AX4Gmt82aPoD7";
  movieList: Review[] = [];

  constructor(private httpClient: HttpClient) { }

  setSearch(searchTerm: string) {
    this.searchTerm = searchTerm;
  }

  //GET /api/search?query=<user's search term>
  getSearch(query: string): Promise<any> {
    const headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');
    const params = new HttpParams()
      .set("query", query);
    return lastValueFrom(
      // this.httpClient.get('http://localhost:8080/api/search', { params: params, headers: headers }));
      this.httpClient.get<Review[]>('/api/search', { params: params, headers: headers }));
  }

  //testing my hypothesis TODO
  // getSearchDirect(query: string) {
  //   //if we already have a prior search
  //   if (this.searchTerm == query) {
  //     return this.movieList;
  //   }

  //   console.log("dataSvc> searchTerm>> ", this.searchTerm)
  //   //store our last query
  //   if (this.searchTerm) {
  //   } else {
  //     this.searchTerm = query; //store for later
  //   }

  //   //send out API request
  //   const headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');
  //   const params = new HttpParams()
  //     .set("query", query)
  //     .set("api-key", this.apiKey);

  //   const promiseResult: Promise<Object> = firstValueFrom(
  //     this.httpClient.get('https://api.nytimes.com/svc/movies/v2/reviews/search.json', { params: params, headers :headers }));

  //   //parse our result
  //   promiseResult
  //     .then((result) => {
  //       //store the results for later
  //       this.movieList = result as Review[];
  //       return this.movieList;
  //     })
  //     .catch((error) => {
  //       console.log(error);
  //       return [];
  //     })

  // }

  //POST /api/comment
  // postComment(comment: Comment): Promise<any> {
  //   let body = new URLSearchParams();
  //   body.set('movieName', this.query);
  //   body.set('name', comment.name);
  //   body.set('rating', comment.rating.toString());
  //   body.set('comment', comment.comment);

  //   const headers = new HttpHeaders().set('Content-Type', 'application/x-www-form-urlencoded');

  //   // const payload = JSON.stringify(comment);
  //   // console.log("dataSvc comment payload>>", payload)

  //   return firstValueFrom(
  //     // this.httpClient.post('http://localhost:8080/api/insertcomment', body));
  //     this.httpClient.post('api/comment', body, { headers }));
  // }

  //POST /api/comment
  postComment(comment: Comment): Promise<any> {
    const headers = new HttpHeaders().set('Content-Type', 'application/x-www-form-urlencoded');
    const body = new HttpParams().appendAll({ ...comment })
    console.log("posting comment >>", body);

    return lastValueFrom(
      this.httpClient.post<Comment>(
        "/api/comment", body.toString(), { headers }));
  }

}

