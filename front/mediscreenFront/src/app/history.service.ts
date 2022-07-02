import {Injectable} from '@angular/core';
import {environment} from "../environments/environment";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {History} from "./History";

@Injectable({
  providedIn: 'root'
})
export class HistoryService {
  private apiServerUrl = environment.apiHistoryBaseUrl;

  constructor(private http: HttpClient) {
  }

  public getHistory(familyName:string,givenName:string):Observable<History>{
    return this.http.get<History>(`${this.apiServerUrl}/patHistory/name/get?familyName=${familyName}&givenName=${givenName}`)
  }
  public updateHistory(history:History):Observable<History>{
    return this.http.put<History>(`${this.apiServerUrl}/patHistory/update`,history)
  }
  public addHistory(history:History):Observable<History>{
    return this.http.post<History>(`${this.apiServerUrl}/patHistory/add`,history)
  }
  public addNote(note:History):Observable<History> {
    return this.http.post<History>(`${this.apiServerUrl}/patHistory/note/add`,note);
  }

  public updateNote(editNote: any):Observable<History>  {
    return this.http.put<History>(`${this.apiServerUrl}/patHistory/note/update`,editNote)
  }
}
