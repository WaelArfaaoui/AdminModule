import { Component, OnInit } from '@angular/core';
import {DynamicDialogConfig, DynamicDialogRef} from "primeng/dynamicdialog";
import {UserControllerService} from "../../../app-api/api/userController.service";
import {MessageService} from "primeng/api";

@Component({
  selector: 'app-delete-user',
  templateUrl: './delete-user.component.html',
  styleUrls: ['./delete-user.component.scss']
})
export class DeleteUserComponent implements OnInit {

    private user: any;

  constructor(public ref: DynamicDialogRef ,public config: DynamicDialogConfig, public messageService:MessageService  , private userService:UserControllerService) { }

  ngOnInit(): void {
      this.user = this.config.data ;
  }

  closeDialog() {
    this.ref.close() ;
  }

  deleteUser() {
      this.userService._delete(this.user.id) ;
      this.ref.close();
      this.messageService.add({severity:'success', summary:'User deleted !', detail:'User deleted successfully'});
  }
}
