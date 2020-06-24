import { Component, OnInit } from '@angular/core';
import { ScheduleService } from '../schedule.service';

@Component({
  selector: 'app-announcements-page',
  templateUrl: './announcements-page.component.html',
  styleUrls: ['./announcements-page.component.css']
})
export class AnnouncementsPageComponent implements OnInit {
  title:any;
  students:any;
  schedule:any;
  announcements:any;
  constructor(service:ScheduleService) {  
    service.ReadJsonSchedule().subscribe(data => this.schedule = data);
    service.ReadJsonSchedule().subscribe(data => this.title = data['Title']);
    service.ReadJsonSchedule().subscribe(data => this.students = data['Students']);
    service.ReadJsonSchedule().subscribe(data => this.announcements = data['Announcements']);
  }

  ngOnInit(): void {
  }

}
