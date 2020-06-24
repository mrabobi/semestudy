import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http'

@Injectable({
  providedIn: 'root'
})
export class ScheduleService {
  private _http:HttpClient
  constructor(http:HttpClient) {
    this._http = http;
   }
   ReadJsonSchedule(){
     return this._http.get("./assets/semestudy_export.json")
   }
}
