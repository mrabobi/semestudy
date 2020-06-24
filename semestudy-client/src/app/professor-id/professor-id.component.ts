import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ScheduleService } from '../schedule.service';

@Component({
  selector: 'app-professor-id',
  templateUrl: './professor-id.component.html',
  styleUrls: ['./professor-id.component.css']
})
export class ProfessorIdComponent implements OnInit {
  title:any;
  professors:any;
  schedule:any;
  profId:string;
  week:any;
  classId:string;
  students:any;
   
  constructor(private route: ActivatedRoute, service:ScheduleService) {
    service.ReadJsonSchedule().subscribe(data => this.schedule = data);
    service.ReadJsonSchedule().subscribe(data => {this.professors = data['Professors'];});
    service.ReadJsonSchedule().subscribe(data=> this.students = data['Students']);
    this.week = this.generateMap();  
   }

  ngOnInit(): void {
    //this.profId = this.route.snapshot.paramMap.get('id');
    this.route.params.subscribe(routeParams => this.profId = routeParams.id);
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

  existStudent(actor:string){
    for(let key in this.students){
      if(this.students[key][0]['id'] == actor){
        this.classId = key;
        return true;
      }
    }
  };
}
