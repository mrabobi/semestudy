import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ScheduleService } from '../schedule.service';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {
  searchId:string;
  input:boolean = false;
  students:any;
  schedule:any;
  studId:string;
  week:any;
  professors:any;
  constructor(private route: ActivatedRoute, service:ScheduleService, private router: Router) { 
    service.ReadJsonSchedule().subscribe(data => this.schedule = data);
    service.ReadJsonSchedule().subscribe(data => this.students = data['Students']);
    service.ReadJsonSchedule().subscribe(data => this.professors = data['Professors']);
    this.week = this.generateMap();  
  }

  ngOnInit(): void {
    this.route.params.subscribe(routeParams => {this.searchId = routeParams.id; });
    
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

  redirectSearch(){
    for(let key in this.students){
      if(this.searchId == key)
        this.router.navigate(['/students', this.searchId]);
    }
    
    for(let key in this.professors){
        if(this.searchId.toLowerCase() === this.professors[key][0]['Name'].toLowerCase()){
          this.router.navigate(['/professors', key]);
        }
    }
        
  }

}
