import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ScheduleService } from '../schedule.service';

@Component({
  selector: 'app-classes-page',
  templateUrl: './classes-page.component.html',
  styleUrls: ['./classes-page.component.css']
})
export class ClassesPageComponent implements OnInit {
  students:any;
  schedule:any;
  week:any;
  professors:any;

  

  constructor(private route: ActivatedRoute, service:ScheduleService) {
    service.ReadJsonSchedule().subscribe(data => this.schedule = data);
    service.ReadJsonSchedule().subscribe(data => this.students = data['Students']);
    service.ReadJsonSchedule().subscribe(data => this.professors = data['Professors']);
   }

  ngOnInit(): void {
     
  }

  getClassrooms(){
    var days = this.generateMap();
    var elems = []
    for(let key in this.professors){
      for(let day of days.keys()){
        for(let materii in this.professors[key][0]['ORAR'][day][days.get(day)]){
          for(let materie in this.professors[key][0]['ORAR'][day][days.get(day)][materii])
            if(this.professors[key][0]['ORAR'][day][days.get(day)][materii][materie]['Resources'] !== null)
              for(let clasa of this.splitString(this.professors[key][0]['ORAR'][day][days.get(day)][materii][materie]['Resources']))
                if(clasa !== "")  
                  elems.push(clasa);
        }
      }
    }
    var unique = elems.filter(function(elem, index, self) {
      return index === self.indexOf(elem);})

    return unique;

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
  };

}

