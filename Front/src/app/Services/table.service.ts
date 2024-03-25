import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class TableService {
  private baseUrl = 'http://localhost:8090/api/tables';

  constructor(private http : HttpClient) {


  }
  retrieveAllTablesAndColumns(): Observable<any> {
    const url = `${this.baseUrl}`; // Replace with your actual API endpoint
    return this.http.get<any>(url);

  }
}
