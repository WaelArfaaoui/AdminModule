import {Component, OnInit} from '@angular/core';
import {DynamicDialogConfig, DynamicDialogRef} from "primeng/dynamicdialog";
import {UserControllerService} from "../../../app-api/api/userController.service";
import {MessageService} from "primeng/api";
import {Router} from "@angular/router";

@Component({
  selector: 'app-delete-user',
  templateUrl: './delete-user.component.html',
  styleUrls: ['./delete-user.component.scss']
})
export class DeleteUserComponent implements OnInit {

    private user: any;

  constructor(public ref: DynamicDialogRef,
              public config: DynamicDialogConfig,
              public messageService:MessageService  ,
              private userService:UserControllerService,
              private router:Router) { }

  ngOnInit(): void {
      this.user = this.config.data ;
  }

  closeDialog() {
    this.ref.close() ;
  }

    deleteUser() {
        let userId = this.config.data.id ;
        console.log(userId) ;
        this.userService._delete(userId)
            .subscribe(
                () => {
                    this.ref.close(true);
                    this.messageService.add({severity:'success', summary:'User deleted !', detail:'User deleted successfully'});
                },
                error => {
                    console.error('Error deleting user:', error);
                    // Handle error, show error message, etc.
                }
            );
    }

}
