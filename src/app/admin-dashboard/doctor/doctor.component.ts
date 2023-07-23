import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/service/auth.service';
import { DoctorServiceService } from 'src/app/service/doctor-service.service';
import { TokenStorageService } from 'src/app/service/token-storage.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-doctor',
  templateUrl: './doctor.component.html',
  styleUrls: ['./doctor.component.scss']
})
export class DoctorComponent {


  isLoggedIn = false;
  currentUser: any;
  public roles: string[] = [];
  showPatientBoard = false;
  showDoctorBoard = false;
  showAdminBoard = false;
  username?: string;

  doctorForm:any = {
    "name":'',
    "email":'',
    "specialization":'',
    "phoneNo":'',
    "fees":'',
    "qualification":'',
    "yearsOfExp":'',
    "daysOfAvail":''
  }
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

  }

  onSubmitProfileForDoctor(f:any){
    this.doctorForm = {
      name:f.value.name,
      email:f.value.email,
      specialization:f.value.specialization,
      phoneNo:f.value.phoneNo,
      fees:f.value.fees,
      qualification:f.value.qualification,
      yearsOfExp:f.value.yearsOfExp,
      daysOfAvail:f.value.daysOfAvail
    };

    this.doctorService.postDoctor(this.doctorForm).subscribe((data) =>{
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
      title: 'Registered SuccessFully.'
    })
  }

}
