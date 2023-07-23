import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../service/auth.service';
import { TokenStorageService } from '../service/token-storage.service';
import { AppointmentService } from '../service/appointment.service';
import { CardDetails } from '../model/card-details';
import Swal from 'sweetalert2';

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
  appointId:string;
  usernames:string;
  totalAmount:number;

  pay:any ={
    "cardDetails":{
      "name": "",
      "cardNumber":"",
      "cvv":""
    },
    "paymentStatus":""
  }

  form:any = {
    name:null,
    cardNumber:null,
    cvv:null
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

    this.appointmentService.getByUsername(this.currentUser).subscribe((data) => {
      this.appList = data;
      console.log(this.appList);
    })

  }

  payNow(id:any, username:string,fees:any){
    this.appointId = id;
    this.usernames = username;
    this.totalAmount = fees;
    this.form = {

    }
    console.log(id+" "+username);
  }

  onSubmit(){
    const{name,cardNumber,cvv} = this.form;
    this.pay = {
      cardDetails:{
        name:name,
        cardNumber:cardNumber,
        cvv:cvv
      },
      paymentStatus:"paid"
    };
    let update = {
      "paymentStatus":"paid"
    }
    this.appointmentService.createPayment(this.appointId,this.usernames,this.pay).subscribe((data) => {
      this.appointmentService.updateAppoint(this.appointId,update).subscribe((appoint) => {
        console.log(appoint);
      })
      console.log(data);
      this.showSuccess();
      window.location.reload();
    },
    (err) => {
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
      title: 'Thankyou for making Payment.'
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

  showPayButton(i:string){
    if(i==="paid"){
      return false;
    }
    return true;
  }

}
