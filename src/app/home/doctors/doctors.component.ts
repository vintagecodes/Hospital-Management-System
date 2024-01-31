import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { AppointmentService } from 'src/app/service/appointment.service';
import { DoctorServiceService } from 'src/app/service/doctor-service.service';
import { PatientService } from 'src/app/service/patient.service';
import { TokenStorageService } from 'src/app/service/token-storage.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-doctors',
  templateUrl: './doctors.component.html',
  styleUrls: ['./doctors.component.css']
})
export class DoctorsComponent {
  doctorList:any = [];
  isLoggedIn = false;
  currentUser: any;
  public roles: string[] = [];
  showPatientBoard = false;
  showDoctorBoard = false;
  showAdminBoard = false;
  username?: string;
  patDetials:any = [];
  showModalBox: boolean = false;
  arrPat:any = new Set;
  arr:any = [];
  Id:string;


  constructor(private doctorsService: DoctorServiceService,private patientService: PatientService, private tokenStorageService: TokenStorageService, private route:Router, private appointmentService: AppointmentService){}

  displayedColumns: string[] = ['Doctor Name', 'Qualification', 'Experience', 'Fees ', 'Specialization', 'Available'];

  form:any = {
    "cause":'',
    "status":'',
    "paymentStatus":'',
    "schedule":''
  };

  ngOnInit(){
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

    this.doctorsService.getDoctorsList().subscribe((data) =>{
      this.doctorList = data;
      console.log(data);
    })

  }

  outsider(){
    Swal.fire({
      title: 'Oops...!',
      text: 'Please Signin For Booking Appointments',
      icon: 'error',
      timer: 2000,
  })
  .then(() => {
    { this.route.navigateByUrl("/login")}
  })
   
  }

  patient(user:string, Ids:string){
    this.Id = Ids;
    let f:NgForm;
    this.patientService.getPatientFromName(user).subscribe(patient => {
      console.log(patient);
      this.patDetials = patient;
      console.log(this.patDetials);
      if(patient==null){
        { this.route.navigateByUrl("/update-profile")}
      }
      else if( this.patDetials[0].name!==user){
      { this.route.navigateByUrl("/update-profile")}
      }else{
        this.bookAppointment(this.Id, user,f);
      }

    })
  }

  bookAppointment(Ids: string, user:string, f:any){
    this.showModalBox = true;
    console.log(Ids);
    this.form = {
      cause:f.value.cause,
      status:"processing",
      paymentStatus:"not paid",
      schedule:f.value.schedule
    }
this.appointmentService.createAppointment(Ids,user,this.form).subscribe((data) =>{
  console.log(data);
})
  }



   


}

