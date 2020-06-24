import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomePageComponent } from './home-page/home-page.component';
import { ProfessorPageComponent } from './professor-page/professor-page.component';
import { StudentPageComponent } from './student-page/student-page.component';
import { FirstPageComponent } from './first-page/first-page.component';
import { ClassesPageComponent } from './classes-page/classes-page.component';
import { AnnouncementsPageComponent } from './announcements-page/announcements-page.component';
import { HttpClientModule } from '@angular/common/http';
import { ScheduleService } from './schedule.service';
import { StudentIdComponent } from './student-id/student-id.component';
import { ProfessorIdComponent } from './professor-id/professor-id.component';
import { FormsModule } from '@angular/forms';
import { SearchComponent } from './search/search.component';
import { ClassesIdComponent } from './classes-id/classes-id.component';


@NgModule({
  declarations: [
    AppComponent,
    HomePageComponent,
    ProfessorPageComponent,
    StudentPageComponent,
    FirstPageComponent,
    ClassesPageComponent,
    AnnouncementsPageComponent,
    StudentIdComponent,
    ProfessorIdComponent,
    SearchComponent,
    ClassesIdComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule

  ],
  providers: [ScheduleService],
  bootstrap: [AppComponent]
})
export class AppModule { }
