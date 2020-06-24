import { Component, ChangeDetectionStrategy } from '@angular/core';
import { ScheduleService } from './schedule.service';

@Component({
  changeDetection: ChangeDetectionStrategy.Default,
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'SEMESTUDY';
  schedule:any;
  constructor(service:ScheduleService){
    service.ReadJsonSchedule().subscribe(data => this.schedule = data);
  }
}
