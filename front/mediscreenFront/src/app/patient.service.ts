import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Patient} from "./patient";
import {environment} from "../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class PatientService {

  private apiServerUrl = environment.apiInfoBaseUrl;

  constructor(private http: HttpClient) {
  }

  public getPatients(): Observable<Patient[]> {
    return this.http.get<Patient[]>(`${this.apiServerUrl}/patient/all`);
  }

  public getPatient(familyName: string, givenName: string): Observable<Patient> {
    return this.http.get<Patient>(`${this.apiServerUrl}/patient/get?family=${familyName}&given=${givenName}`);
  }

  public addPatient(patient: Patient): Observable<Patient> {
    return this.http.post<Patient>(`${this.apiServerUrl}/patient/add`, patient);
  }

  public updatePatient(patient: Patient): Observable<Patient> {
    return this.http.put<Patient>(`${this.apiServerUrl}/patient/update`, patient);
  }
}
