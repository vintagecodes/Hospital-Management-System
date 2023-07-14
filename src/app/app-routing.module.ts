import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';
import { DoctorsComponent } from './home/doctors/doctors.component';
import { UpdateProfileComponent } from './update-profile/update-profile.component';
import { AppointHistoryComponent } from './appoint-history/appoint-history.component';

const routes: Routes = [
  {path: '', redirectTo: '/home', pathMatch: 'full'},
  { path: 'home', component: HomeComponent},
  { path: 'signup', component: RegisterComponent},
  {path: 'login', component: LoginComponent},
  { path: 'doctors', component:DoctorsComponent},
  { path: 'update-profile', component: UpdateProfileComponent},
  { path: 'appoint-history', component: AppointHistoryComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
