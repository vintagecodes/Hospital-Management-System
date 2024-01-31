import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../service/auth.service';
import { TokenStorageService } from '../service/token-storage.service';
import { PatientService } from '../service/patient.service';
import { DoctorServiceService } from '../service/doctor-service.service';

@Component({
  selector: 'app-update-profile',
  templateUrl: './update-profile.component.html',
  styleUrls: ['./update-profile.component.scss']
})
export class UpdateProfileComponent {

  form:any = {
    "name":'',
    "email":'',
    "phoneNo":'',
    "address":'',
    "age":''
  }

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
  isLoggedIn = false;
  currentUser: any;
  public roles: string[] = [];
  showPatientBoard = false;
  showDoctorBoard = false;
  showAdminBoard = false;
  username?: string;

  constructor(private route:Router,private authService: AuthService, private tokenStorageService: TokenStorageService, private patientService: PatientService,
    private doctorService:DoctorServiceService) { }

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

  onSubmit(f:any) {
    this.form = {
      name:f.value.name,
      email:f.value.email,
      phoneNo:f.value.phoneNo,
      address:f.value.address,
      age:f.value.age
    };

    this.patientService.postPatientFrom(this.form).subscribe((data) => {
      console.log(data);
    })
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
    })

  }

}
