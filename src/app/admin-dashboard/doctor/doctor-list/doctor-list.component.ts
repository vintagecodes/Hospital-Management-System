import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/service/auth.service';
import { DoctorServiceService } from 'src/app/service/doctor-service.service';
import { TokenStorageService } from 'src/app/service/token-storage.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-doctor-list',
  templateUrl: './doctor-list.component.html',
  styleUrls: ['./doctor-list.component.scss']
})
export class DoctorListComponent {

  isLoggedIn = false;
  currentUser: any;
  public roles: string[] = [];
  showPatientBoard = false;
  showDoctorBoard = false;
  showAdminBoard = false;
  username?: string;
  doctorList:any = [];


  constructor(private route:Router,private authService: AuthService, private tokenStorageService: TokenStorageService, private doctorService: DoctorServiceService) { }


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

    this.doctorService.getDoctorsList().subscribe((data) => {
      this.doctorList = data;
      console.log(data);
    })

  }

  deleteDoctor(name:string){
    this.doctorService.deleteDoctorPerName(name).subscribe((data) => {
      console.log(data);
      this.showSuccess();
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
      title: 'Deleted SuccessFully.'
    })
  }




}
