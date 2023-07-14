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
}
