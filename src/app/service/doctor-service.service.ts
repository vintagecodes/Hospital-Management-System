import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class DoctorServiceService {

  constructor(private http: HttpClient) { }

  DOCTOR_URL = 'http://localhost:5000/doctor/'

  getDoctorsList(){
    return this.http.get(this.DOCTOR_URL+'viewAll');
  }

  postDoctor(doctor:any){
    return this.http.post(this.DOCTOR_URL+'created',doctor);
  }

  deleteDoctorPerName(name:string){
    return this.http.delete(this.DOCTOR_URL+'deleteByName/'+name);
  }
}
