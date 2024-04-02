import { Component, OnInit } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {UserService} from "../../services/user/user.service";
import {UserDto} from "../../../app-api";
@Component({
  selector: 'app-all-users',
  templateUrl: './all-users.component.html',
  styleUrls: ['./all-users.component.scss']
})
export class AllUsersComponent implements OnInit {

  userList!:UserDto[] ;

  constructor(private http: HttpClient , private userService:UserService) { }

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
}
