import { Component, OnInit } from '@angular/core';
import { UserService } from "../../services/user/user.service";
import { Router } from "@angular/router";
import { FormBuilder, FormGroup } from "@angular/forms";
import { MessageService } from 'primeng/api';
import { UserDto } from "../../../app-api";
import { FileUploadService } from '../../services/file-upload.service';

@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.scss'],
  providers: [MessageService]
})
export class AddUserComponent implements OnInit {

  errorFound: boolean = false;
  formSave!: FormGroup;

  constructor(private userService: UserService,
              private router: Router,
              private fb: FormBuilder,
              private messageService: MessageService,
              private fileUploadService: FileUploadService) { }

  ngOnInit(): void {
    this.formSave = this.fb.group({
      firstname: this.fb.control(""),
      lastname: this.fb.control(""),
      email: this.fb.control(""),
      phone: this.fb.control(""),
      company: this.fb.control(""),
      role: '',
      password: this.fb.control(""),
      file: null
    });
    this.formSave.get('role')!.valueChanges.subscribe((role: UserDto.RoleEnum) => {
      switch (role) {
        case UserDto.RoleEnum.Admin:
          this.formSave.get('role')!.setValue(UserDto.RoleEnum.Admin);
          break;
        case UserDto.RoleEnum.Businessexpert:
          this.formSave.get('role')!.setValue(UserDto.RoleEnum.Businessexpert);
          break;
        case UserDto.RoleEnum.Consultant:
          this.formSave.get('role')!.setValue(UserDto.RoleEnum.Consultant);
          break;
        default:
          this.formSave.get('role')!.setValue(UserDto.RoleEnum.Admin);
      }
    });
  }

  addUser() {
    const formData = new FormData();
    formData.append('file', this.formSave.get('file')!.value);
    formData.append('firstname', this.formSave.get('firstname')!.value);
    formData.append('lastname', this.formSave.get('lastname')!.value);
    formData.append('email', this.formSave.get('email')!.value);
    formData.append('phone', this.formSave.get('phone')!.value);
    formData.append('company', this.formSave.get('company')!.value);
    formData.append('role', this.formSave.get('role')!.value);
    formData.append('password', this.formSave.get('password')!.value);

    this.fileUploadService.addUserWithFile(formData).subscribe({
      next: data => {
        this.router.navigate(['users']);
        this.messageService.add({ severity: 'success', summary: 'Success', detail: 'User successfully added' });
      },
      error: error => {
        this.errorFound = true;
        this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Failed to add user' });
      }
    });
  }

  onFileSelected(event: any): void {
    const file = event.target.files[0];
    this.formSave.patchValue({
      file: file
    });
  }
}
