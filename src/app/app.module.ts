import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { HeaderComponent } from './header/header.component';
import { RegisterComponent } from './register/register.component';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { LoginComponent } from './login/login.component';
import { DoctorsComponent } from './home/doctors/doctors.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { UpdateProfileComponent } from './update-profile/update-profile.component';
import { AppointHistoryComponent } from './appoint-history/appoint-history.component';
import { AppointDashboardComponent } from './appoint-dashboard/appoint-dashboard.component';
import { ListComponent } from './appoint-dashboard/list/list.component';
import { AdminDashboardComponent } from './admin-dashboard/admin-dashboard.component';
import { SideNavComponent } from './admin-dashboard/side-nav/side-nav.component';
import { DoctorComponent } from './admin-dashboard/doctor/doctor.component';
import { PatientComponent } from './admin-dashboard/patient/patient.component';
import { PatientListComponent } from './admin-dashboard/patient-list/patient-list.component';
import { UsersComponent } from './admin-dashboard/users/users.component';
import { DoctorListComponent } from './admin-dashboard/doctor/doctor-list/doctor-list.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    HeaderComponent,
    RegisterComponent,
    LoginComponent,
    DoctorsComponent,
    UpdateProfileComponent,
    AppointHistoryComponent,
    AppointDashboardComponent,
    ListComponent,
    AdminDashboardComponent,
    SideNavComponent,
    DoctorComponent,
    PatientComponent,
    PatientListComponent,
    UsersComponent,
    DoctorListComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    NgbModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
