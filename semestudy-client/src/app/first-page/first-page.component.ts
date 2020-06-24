import { Component, OnInit } from '@angular/core';
import { ScheduleService } from '../schedule.service';
import {Router} from '@angular/router'
@Component({
  selector: 'app-first-page',
  templateUrl: './first-page.component.html',
  styleUrls: ['./first-page.component.css']
})
export class FirstPageComponent implements OnInit {
  title:any;
  students:any;
  schedule:any;

  constructor(service:ScheduleService){
    service.ReadJsonSchedule().subscribe(data => this.schedule = data);
    service.ReadJsonSchedule().subscribe(data => this.title = data['Title']);
    service.ReadJsonSchedule().subscribe(data => this.students = data['Students']);
    
  }

  ngOnInit(): void {
    
  }



}
