import { ComponentFixture, TestBed } from '@angular/core/testing';
import { AllUsersComponent } from './all-users.component';
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {UserService} from "../../services/user/user.service";
import {DialogService} from "primeng/dynamicdialog";
import {UserDto} from "../../../open-api";
import {of, throwError} from "rxjs";
describe('AllUsersComponent', () => {
  let component: AllUsersComponent;
  let fixture: ComponentFixture<AllUsersComponent>;
  let userService: jasmine.SpyObj<UserService>;
  let dialogService: jasmine.SpyObj<DialogService>;

  beforeEach(async () => {
    const userServiceSpy = jasmine.createSpyObj('UserService', ['getAllUsers']);
    const dialogServiceSpy = jasmine.createSpyObj('DialogService', ['open']);

    await TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [AllUsersComponent],
      providers: [
        { provide: UserService, useValue: userServiceSpy },
        { provide: DialogService, useValue: dialogServiceSpy }
      ]
    }).compileComponents();

    userService = TestBed.inject(UserService) as jasmine.SpyObj<UserService>;
    dialogService = TestBed.inject(DialogService) as jasmine.SpyObj<DialogService>;
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AllUsersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should fetch all active users', () => {
    const mockUserList: UserDto[] = [
      { id: 1, firstname: 'John', lastname: 'Doe', email: 'john@example.com', active: true },
      { id: 2, firstname: 'Jane', lastname: 'Doe', email: 'jane@example.com', active: true }
    ];

    userService.getAllUsers.and.returnValue(of(mockUserList));

    component.getAllUsers();

    expect(userService.getAllUsers).toHaveBeenCalled();
    expect(component.userList).toEqual(mockUserList);
  });

  it('should handle error when fetching users fails', () => {
    userService.getAllUsers.and.returnValue(throwError('Error'));

    spyOn(console, 'error');

    component.getAllUsers();

    expect(console.error).toHaveBeenCalled();
  });

  it('should open dialog for updating user', () => {
    const mockUser: UserDto = { id: 1, firstname: 'John', lastname: 'Doe', email: 'john@example.com', active: true };
    component.selectedUser = mockUser;

    component.updateUser(mockUser);

    expect(dialogService.open).toHaveBeenCalled();
  });

  it('should open dialog for deleting user', () => {
    const mockUser: UserDto = { id: 1, firstname: 'John', lastname: 'Doe', email: 'john@example.com', active: true };
    component.selectedUser = mockUser;

    component.deleteUser(mockUser);

    expect(dialogService.open).toHaveBeenCalled();
  });

  it('should open dialog for locking user', () => {
    const mockUser: UserDto = { id: 1, firstname: 'John', lastname: 'Doe', email: 'john@example.com', active: true };
    component.selectedUser = mockUser;

    component.lockUser(mockUser);

    expect(dialogService.open).toHaveBeenCalled();
  });
});
