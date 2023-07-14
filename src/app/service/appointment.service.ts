import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AppointmentService {

  constructor(private http: HttpClient) { }

  APPOINTMENT_URL = 'http://localhost:5002/appointment/';

  createAppointment(doctorId:string, patientName:string,appointment:any){
    return this.http.post(this.APPOINTMENT_URL+'created/'+doctorId+'/'+patientName,appointment);
  }

  getByUsername(username:string){
    return this.http.get(this.APPOINTMENT_URL+'listPerUser/'+username);
  }


}
