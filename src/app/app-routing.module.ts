import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';
import { DoctorsComponent } from './home/doctors/doctors.component';
import { UpdateProfileComponent } from './update-profile/update-profile.component';
import { AppointHistoryComponent } from './appoint-history/appoint-history.component';
import { AppointDashboardComponent } from './appoint-dashboard/appoint-dashboard.component';
import { ListComponent } from './appoint-dashboard/list/list.component';
import { AdminDashboardComponent } from './admin-dashboard/admin-dashboard.component';
import { PatientComponent } from './admin-dashboard/patient/patient.component';
import { UsersComponent } from './admin-dashboard/users/users.component';
import { PatientListComponent } from './admin-dashboard/patient-list/patient-list.component';
import { DoctorListComponent } from './admin-dashboard/doctor/doctor-list/doctor-list.component';
import { DoctorComponent } from './admin-dashboard/doctor/doctor.component';

const routes: Routes = [
  {path: '', redirectTo: '/home', pathMatch: 'full'},
  { path: 'home', component: HomeComponent},
  { path: 'signup', component: RegisterComponent},
  {path: 'login', component: LoginComponent},
  { path: 'doctors', component:DoctorsComponent},
  { path: 'update-profile', component: UpdateProfileComponent},
  { path: 'appoint-history', component: AppointHistoryComponent},
  { path:'appoint-dashboard', component: AppointDashboardComponent},
  { path : 'appoint-dashboard/list', component: ListComponent},
  { path : 'admin-dashboard', component: AdminDashboardComponent},
  { path : 'admin-dashboard/create-new-patient', component:PatientComponent},
  { path: 'users', component: UsersComponent},
  { path: 'admin-dashboard/patient-list', component: PatientListComponent}, 
  { path: 'admin-dashboard/doctor-list', component: DoctorListComponent},
  { path: 'admin-dashboard/create-new-doctor', component: DoctorComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
