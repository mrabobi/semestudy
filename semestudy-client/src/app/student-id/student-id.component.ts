import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ScheduleService } from '../schedule.service';

@Component({
  selector: 'app-student-id',
  templateUrl: './student-id.component.html',
  styleUrls: ['./student-id.component.css']
})
export class StudentIdComponent implements OnInit {
  students:any;
  schedule:any;
  studId:string;
  week:any;
  professors:any;
  
  constructor(private route: ActivatedRoute, service:ScheduleService) { 
    service.ReadJsonSchedule().subscribe(data => this.schedule = data);
    service.ReadJsonSchedule().subscribe(data => this.students = data['Students']);
    service.ReadJsonSchedule().subscribe(data => this.professors = data['Professors']);
    this.week = this.generateMap();  
  }

  ngOnInit(): void {
    //this.studId = this.route.snapshot.paramMap.get('id');
    this.route.params.subscribe(routeParams => this.studId = routeParams.id);
  }

  generateMap(){
    let weekend = new Map<number,string>();
    weekend.set(0,'Luni');
    weekend.set(1,'Marti');
    weekend.set(2,'Miercuri');
    weekend.set(3,'Joi');
    weekend.set(4,'Vineri');
    weekend.set(5,'Sambata');
    weekend.set(6,'Duminica');
    return weekend;
  };

  splitString(actors:string){
    let elem = actors.split("@");
    return elem;
  }

  existProfessor(actor:string){
    for(let key in this.professors){
      if(key == actor){
        return true;
      }
    }
  }


}
