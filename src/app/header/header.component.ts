import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../service/auth.service';
import { TokenStorageService } from '../service/token-storage.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {

  isLoggedIn = false;
  currentUser: any;
  public roles: string[] = [];
  showPatientBoard = false;
  showDoctorBoard = false;
  showAdminBoard = false;
  username?: string;

  constructor(private route:Router,private authService: AuthService, private tokenStorageService: TokenStorageService) { }

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


  logout(): void {
    this.tokenStorageService.signOut();
    window.location.reload();
  }

}
