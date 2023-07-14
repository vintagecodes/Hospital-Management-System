import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../service/auth.service';
import { TokenStorageService } from '../service/token-storage.service';
import { AppointmentService } from '../service/appointment.service';

@Component({
  selector: 'app-appoint-history',
  templateUrl: './appoint-history.component.html',
  styleUrls: ['./appoint-history.component.scss']
})
export class AppointHistoryComponent {

  isLoggedIn = false;
  currentUser: any;
  public roles: string[] = [];
  showPatientBoard = false;
  showDoctorBoard = false;
  showAdminBoard = false;
  username?: string;
  appList:any =[];

  constructor(private route:Router,private authService: AuthService, private tokenStorageService: TokenStorageService, private appointmentService: AppointmentService) { }

  ngOnInit(): void {
    this.isLoggedIn = !!this.tokenStorageService.getToken();

    if(this.isLoggedIn) {
      const user = this.tokenStorageService.getUser();
      this.roles = user.roles;
      console.log(this.roles[0]);
      this.username = user.username;
      this.showAdminBoard = this.roles.includes('ROLE_ADMIN');
      this.showPatientBoard = this.roles.includes('ROLE_PATIENT');
      this.showDoctorBoard = this.roles.includes('ROLE_DOCTOR');
    }
    this.currentUser = this.tokenStorageService.getUser().username;
    console.log(this.currentUser);

    this.appointmentService.getByUsername(this.currentUser).subscribe((data) => {
      this.appList = data;
      console.log(this.appList);
    })

  }

  payNow(id:any, username:string){
    console.log(id+" "+username);
  }

}
