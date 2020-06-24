import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ScheduleService } from '../schedule.service';

@Component({
  selector: 'app-classes-id',
  templateUrl: './classes-id.component.html',
  styleUrls: ['./classes-id.component.css']
})
export class ClassesIdComponent implements OnInit {
  students:any;
  schedule:any;
  week:any;
  professors:any;
  classId:any;
  studId:any;
  waiting:boolean = true;
  constructor(private route: ActivatedRoute, service:ScheduleService) { 
    service.ReadJsonSchedule().subscribe(data => this.schedule = data);
    service.ReadJsonSchedule().subscribe(data => this.students = data['Students']);
    service.ReadJsonSchedule().subscribe(data => this.professors = data['Professors']);
    this.week = this.generateMap();  
  }

  ngOnInit(): void {
    this.route.params.subscribe(routeParams => this.classId = routeParams.id);
  }

  existSubstring(first:string, second:string){
    return (first.indexOf(second) !== -1)
  };

  splitString(actors:string){
    let elem = actors.split("@");
    return elem;
  };

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

  existStudent(actor:string){
    for(let key in this.students){
      if(this.students[key][0]['id'] == actor){
        this.studId = key;
        return true;
      }
    }
  };
  returnProfsKeys(){
    return Object.keys(this.professors);
  };
  setWaiting(){
    this.waiting = false;
  }
}
