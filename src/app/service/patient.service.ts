import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class PatientService {

  constructor(private http: HttpClient) { }

  PATIENT_URL = 'http://localhost:5001/patient/'

  getPatientFromName(id: string){
    return this.http.get(this.PATIENT_URL+'getByName/'+id);
  }

  postPatientFrom(form:any){
    return this.http.post(this.PATIENT_URL+'created',form);
  }

  viewAllPatient(){
    return this.http.get(this.PATIENT_URL+'viewALL');
  }

  deleteByName(name:string){
    return this.http.delete(this.PATIENT_URL+'deleteByName/'+name);
  }
}
