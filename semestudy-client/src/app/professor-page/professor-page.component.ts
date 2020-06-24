import { Component, OnInit } from '@angular/core';
import { ScheduleService } from '../schedule.service';

@Component({
  selector: 'app-professor-page',
  templateUrl: './professor-page.component.html',
  styleUrls: ['./professor-page.component.css']
})
export class ProfessorPageComponent implements OnInit {
  professors:any;
  students:any;
  schedule:any;

  constructor(service:ScheduleService) {
    service.ReadJsonSchedule().subscribe(data => this.schedule = data);
    service.ReadJsonSchedule().subscribe(data => this.professors = data['Professors']);
    service.ReadJsonSchedule().subscribe(data => this.students = data['Students']);
   }

  ngOnInit(): void {
  }

}
