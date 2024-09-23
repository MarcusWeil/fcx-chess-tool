import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class FcxService {

  constructor(private http: HttpClient) {}

  fetchTournaments(): Observable<any> {
    return this.http.get(`${environment.baseUrl}/tournaments`, { withCredentials: true });
  }

  registerTournament(tournament: any): Observable<any> {
    return this.http.post(`${environment.baseUrl}/tournaments`, tournament, { withCredentials: true });
  }

  submitResults(results: any): Observable<any> {
    return this.http.post(`${environment.baseUrl}/tournament/results`, results, { withCredentials: true });
  }
  exportResults(tournament: string): Observable<any> {
    return this.http.get(`${environment.baseUrl}/tournament/export/${tournament}`, { responseType: 'blob', withCredentials: true });
  }
}
