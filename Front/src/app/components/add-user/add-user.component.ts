import { Component, OnInit } from '@angular/core';
import { UserService } from "../../services/user/user.service";
import { Router } from "@angular/router";
import { FormBuilder, FormGroup } from "@angular/forms";
import { MatSnackBar } from "@angular/material/snack-bar";
import { UserDto } from "../../../app-api";

@Component({
    selector: 'app-add-user',
    templateUrl: './add-user.component.html',
    styleUrls: ['./add-user.component.scss']
})
export class AddUserComponent implements OnInit {

    errorFound: boolean = false;
    formSave!: FormGroup;

    constructor(private userService: UserService,
                private router: Router, private fb: FormBuilder, private _snackBar: MatSnackBar) { }

    ngOnInit(): void {
        this.formSave = this.fb.group({
            firstname: this.fb.control(""),
            lastname: this.fb.control(""),
            email: this.fb.control(""),
            phone: this.fb.control(""),
            company: this.fb.control(""),
            role: '',
            password: this.fb.control(""),
        });

        // Subscribe to role changes to set default value dynamically
        this.formSave.get('role')!.valueChanges.subscribe((role: UserDto.RoleEnum) => {
            // Set default value based on the selected role
            switch (role) {
                case UserDto.RoleEnum.Admin:
                    // Set default value for admin
                    this.formSave.get('role')!.setValue(UserDto.RoleEnum.Admin);
                    break;
                case UserDto.RoleEnum.Businessexpert:
                    // Set default value for business expert
                    this.formSave.get('role')!.setValue(UserDto.RoleEnum.Businessexpert);
                    break;
                case UserDto.RoleEnum.Consultant:
                    // Set default value for consultant
                    this.formSave.get('role')!.setValue(UserDto.RoleEnum.Consultant);
                    break;
                default:
                    // Set default value to empty
                    this.formSave.get('role')!.setValue(UserDto.RoleEnum.Admin);
            }
        });
    }

    addUser() {
        this.userService.addUser(this.formSave.value).subscribe({
            next: data => {
                this.router.navigate(['users']);
                this._snackBar.open('User successfully added ', 'Dismiss', {
                    duration: 1000
                });
            }, error: error => {
                this.errorFound = true;
                this._snackBar.open('Wrong information', 'Dismiss', {
                    duration: 1000
                });
            }
        });
    }
}
