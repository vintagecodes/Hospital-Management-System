import { Component } from '@angular/core';
import { AuthService } from '../service/auth.service';
import { Register } from '../model/register';
import Swal from 'sweetalert2';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {

form:Register = {
  username: '',
  email: '',
  password: '',
  roles: undefined
}
  isSuccessful = false;
  isSignUpFailed = false;
  errorMessage = '';
  roles1 = new Set<String>();
  r:Set<String> | undefined

  constructor(private authService:AuthService, private router:Router){}


  onSubmit(){

    const {username, email,roles,password} = this.form;
    this.roles1.add(roles);

    console.log(this.roles1.add(roles));
    let arr = Array.from(this.roles1.add(roles));
    console.log(arr);
    console.log(this.form);
    this.authService.register(username, email, arr, password).subscribe(data =>{
      console.log(data);
        this.isSuccessful = true;
        this.isSignUpFailed = false;
        Swal.fire('Good Job!','SuccessFully Registered, Taking you to the Login Page','success');
        {this.router.navigateByUrl('/login');}
      console.log(data);
    }, err =>{
      this.errorMessage = err.error.message;
        this.isSignUpFailed = true;
        Swal.fire('Oops! Something went wrong','error');
      console.log(err);
    })
  }



}
