import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { firstValueFrom } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DataService {

  // private ENDPOINT: string = "/api";
  constructor(private httpClient: HttpClient) { }

  //GET /api/search?query=<movie name>
  //accept json
  getSearch(movieName: string): Promise<any> {
    const headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');
    const params = new HttpParams()
      .set("query", movieName);

    return firstValueFrom(
      this.httpClient.get('http://localhost:8080/api/search', { params, headers }));
  }

  //POST /api/comment
  getNumberOfComments(comment: Comment): Promise<any> {
    const headers = new HttpHeaders().set('Content-Type', 'application/x-www-form-urlencoded; charset=utf-8');

    const payload = JSON.stringify(comment);

    return firstValueFrom(
      this.httpClient.post('http://localhost:8080/api/comment', payload));
  }
}

