import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../environments/environment";
import {Observable} from "rxjs";
import {Assessment} from "./Assessment";

@Injectable({
  providedIn: 'root'
})
export class AssessService {
  private apiServerUrl = environment.apiAssessBaseUrl;

  constructor(private http: HttpClient) {
  }

  public getAssessmentWithId(id: number): Observable<Assessment> {
    return this.http.post<Assessment>(`${this.apiServerUrl}/assess/id?patientId=${id}`, null);
  }
}
