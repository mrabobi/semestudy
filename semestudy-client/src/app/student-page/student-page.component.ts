import { Component, OnInit } from '@angular/core';
import { ScheduleService } from '../schedule.service';

@Component({
  selector: 'app-student-page',
  templateUrl: './student-page.component.html',
  styleUrls: ['./student-page.component.css']
})
export class StudentPageComponent implements OnInit {
  students:any;
  schedule:any;
  professors:any;
  
  constructor(service:ScheduleService){
    service.ReadJsonSchedule().subscribe(data => this.schedule = data);
    service.ReadJsonSchedule().subscribe(data => this.students = data['Students']);
    service.ReadJsonSchedule().subscribe(data => this.professors = data['Professors']);
  }

  ngOnInit(): void {
  }
  
}
