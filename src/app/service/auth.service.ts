import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {Observable} from 'rxjs';

const API = 'http://localhost:8500/api/auth/';
const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json'})
};



@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) { }

  login(username: string, password: string): Observable<any> {
    return this.http.post(API + 'signin',{
      username,
      password
    }, httpOptions);
  }

  register(username: string, email:string, role:any, password: string): Observable<any> {
    return this.http.post(API + 'signup',{
      username,
      email,
      role,
      password
    }, httpOptions);
  }

  registerForPatient(username: string, email:string, password: string): Observable<any> {
    return this.http.post(API + 'signup',{
      username,
      email,
      password
    }, httpOptions);
  }

  getUsers(){
    return this.http.get(API + 'users');
  }

  deleteUsers(username:string){
    return this.http.delete(API + 'delete/'+username).subscribe((data)=>{
      console.log("Deleted SuccessFully");
    })
  }

}
