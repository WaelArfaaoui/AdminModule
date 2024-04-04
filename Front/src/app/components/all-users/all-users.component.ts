import { Component, OnInit } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {UserService} from "../../services/user/user.service";
import {UserDto} from "../../../app-api";
import {UpdateUserComponent} from "../update-user/update-user.component";
import {DialogService} from "primeng/dynamicdialog";
import {LockUserComponent} from "../lock-user/lock-user.component";
import {DeleteUserComponent} from "../delete-user/delete-user.component";
@Component({
  selector: 'app-all-users',
  templateUrl: './all-users.component.html',
  styleUrls: ['./all-users.component.scss'], providers: [DialogService]
})
export class AllUsersComponent implements OnInit {
    selectedUser: any;

    userList!:UserDto[] ;

  constructor(private http: HttpClient , private userService:UserService ,private dialogService: DialogService) { }

  ngOnInit(): void {
    this.getAllUsers();
  }

  getAllUsers() {
    this.userService.getAllUsers()
        .subscribe(
            users => {
              console.log(users);
              this.userList = users;
            },
            error => {
              console.error("Error fetching users:", error);
            }
        );
  }

    updateUser(user: any) {
        this.selectedUser = user;
        const ref = this.dialogService.open(UpdateUserComponent, {
        header: 'Update User',
        width: '900px',
        height: '600px',
        contentStyle: {"background-color": "var(--color-white)","color": "var(--color-dark)"},
        data: this.selectedUser
        });
    }

  deleteUser() {
    const ref = this.dialogService.open(DeleteUserComponent, {
      header: 'Delete User',
      width: '500px',
      contentStyle: {"background-color": "var(--color-white)","color": "var(--color-dark)"},

    });
  }

  lockUser() {
    const ref = this.dialogService.open(LockUserComponent, {
      header: 'Lock User',
      width: '500px',
      contentStyle: {"background-color": "var(--color-white)","color": "var(--color-dark)"},

    });
  }
}
