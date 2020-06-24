import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomePageComponent } from './home-page/home-page.component';
import { ProfessorPageComponent } from './professor-page/professor-page.component';
import { StudentPageComponent } from './student-page/student-page.component';
import { FirstPageComponent } from './first-page/first-page.component';
import { ClassesPageComponent } from './classes-page/classes-page.component';
import { StudentIdComponent } from './student-id/student-id.component';
import { AnnouncementsPageComponent } from './announcements-page/announcements-page.component';
import { ProfessorIdComponent } from './professor-id/professor-id.component';
import { SearchComponent } from './search/search.component';
import { ClassesIdComponent } from './classes-id/classes-id.component';


const routes: Routes = [
  {path: '', component:HomePageComponent, children: [
    {path: '', component: FirstPageComponent},
    {path: 'professors', component: ProfessorPageComponent},
    {path: 'professors/:id', component: ProfessorIdComponent},
    {path: 'students', component: StudentPageComponent},
    {path: 'students/:id', component: StudentIdComponent},
    {path: 'classrooms', component: ClassesPageComponent},
    {path: 'classrooms/:id', component: ClassesIdComponent},
    {path: 'announcements', component: AnnouncementsPageComponent},
    {path: 'search/:id', component: SearchComponent}
  ]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
