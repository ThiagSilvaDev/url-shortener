import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

export interface ShortenRequest {
  longUrl: string;
}

export interface ShortenResponse {
  shortUrl: string;
}

@Injectable({providedIn: 'root'})
export class UrlService {
  private base = '/api/v1';

  constructor(private http: HttpClient) {
  }

  shorten(longUrl: string): Observable<ShortenResponse> {
    return this.http.post<ShortenResponse>(`${this.base}/shortener`, {longUrl} as ShortenRequest);
  }
}
