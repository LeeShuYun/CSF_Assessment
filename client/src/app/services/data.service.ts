import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { firstValueFrom } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DataService {

  constructor(private httpClient: HttpClient) { }

  getDataFromAPI(formData: FormData): Promise<any> {
    return firstValueFrom(
      this.httpClient.post('/firstapi', formData));
  }

  uploadData(formData: FormData): Promise<any> {
    return firstValueFrom(
      this.httpClient.post('/upload', formData));
  }
}

