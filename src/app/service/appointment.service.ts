import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AppointmentService {

  constructor(private http: HttpClient) { }

  APPOINTMENT_URL = 'http://localhost:5002/appointment/';

  PAYMENT_url = "http://localhost:8181/payment/";

  createAppointment(doctorId:string, patientName:string,appointment:any){
    return this.http.post(this.APPOINTMENT_URL+'created/'+doctorId+'/'+patientName,appointment);
  }

  getByUsername(username:string){
    return this.http.get(this.APPOINTMENT_URL+'listPerUser/'+username);
  }

  getByDoctorUsername(username:string){
    return this.http.get(this.APPOINTMENT_URL+'listPerDoctorName/'+username);
  }

  createPayment(appointId:string, username:string, payment:any){
    return this.http.post(this.PAYMENT_url+'create/'+appointId+'/'+username, payment);
  }

  updateAppoint(appointId:string,obj:any){
    return this.http.put(this.APPOINTMENT_URL+'update/paymentstatus/'+appointId, obj);
  }

  updateAppointByStatus(appointId:string,obj:any){
    return this.http.put(this.APPOINTMENT_URL+'update/status/'+appointId, obj);
  }

  getAll(){
    return this.http.get(this.APPOINTMENT_URL+'/listAllUsers');
  }


}
