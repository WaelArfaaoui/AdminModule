import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {UserService} from "../../services/user/user.service";
import {Router} from "@angular/router";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

    formLogin!:FormGroup ;
    errorFound:boolean = false ;


    constructor(
        private userService: UserService,
        private router: Router ,private fb:FormBuilder , private _snackBar: MatSnackBar) { }

    ngOnInit(): void {
        this.formLogin = this.fb.group(
            {
                email : this.fb.control("") ,
                password : this.fb.control("")
            }
        )
    }
    handleLogin() {

        this.userService.login(this.formLogin.value).subscribe({
                next: data => {
                    this.errorFound = false ;
                    console.log(this.errorFound) ;
                    console.log(data) ;
                    this.userService.setToken(data) ;
                    this.connectUser(data) ;

                }, error: error => {
                    this.errorFound=true ;
                    this._snackBar.open('Wrong email or password', 'Dismiss', {
                        duration: 1000
                    });
                }
            }
        ) ;

    }

    connectUser(data:any){
        this.userService.setConnectedUser(this.userService.getUserDetails());
        this.router.navigate(['/']) ;
    }

}
