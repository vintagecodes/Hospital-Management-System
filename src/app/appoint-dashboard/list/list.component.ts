import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AppointmentService } from 'src/app/service/appointment.service';
import { AuthService } from 'src/app/service/auth.service';
import { TokenStorageService } from 'src/app/service/token-storage.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.scss']
})
export class ListComponent {

  isLoggedIn = false;
  currentUser: any;
  public roles: string[] = [];
  showPatientBoard = false;
  showDoctorBoard = false;
  showAdminBoard = false;
  username?: string;
  appList:any =[];
  appointId:string;
  usernames:string;
  allUserList:any = [];


  form:any={
    "status":"confirmed"
  };

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

    this.appointmentService.getByDoctorUsername(this.currentUser).subscribe((data) => {
      this.appList = data;
      console.log(this.appList);
    })

    this.appointmentService.getAll().subscribe((data) => {
      this.allUserList = data;
      console.log(this.allUserList);
    })

  }

  confirmStatus(id:any){
    this.appointmentService.updateAppointByStatus(id,this.form).subscribe((data) => {
      console.log(data);
      this.showSuccess();
      window.location.reload();
    }, (err) => {
      this.showError();
      window.location.reload();
    })
  }

  showSuccess(){
    const Toast = Swal.mixin({
      toast: true,
      position: 'top-end',
      showConfirmButton: false,
      timer: 3000,
      timerProgressBar: true,
      didOpen: (toast) => {
        toast.addEventListener('mouseenter', Swal.stopTimer)
        toast.addEventListener('mouseleave', Swal.resumeTimer)
      }
    })
    
    Toast.fire({
      icon: 'success',
      title: 'Confirmed Appointment.'
    })
  }

  showError(){
    const Toast = Swal.mixin({
      toast: true,
      position: 'top-end',
      showConfirmButton: false,
      timer: 3000,
      timerProgressBar: true,
      didOpen: (toast) => {
        toast.addEventListener('mouseenter', Swal.stopTimer)
        toast.addEventListener('mouseleave', Swal.resumeTimer)
      }
    })
    
    Toast.fire({
      icon: 'error',
      title: 'Something Went Wrong!'
    })
  }

  showConfirmButton(i:any){
    if(i=="confirmed"){
      return false;
    }
    return true;
  }

}
