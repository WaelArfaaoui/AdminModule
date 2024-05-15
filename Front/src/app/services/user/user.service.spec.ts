import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { UserService } from './user.service';
import { AuthenticationService, AuthenticationRequest, UserControllerService, UserDto, RegisterDto } from '../../../open-api';
import { RouterTestingModule } from '@angular/router/testing';
import {jwtDecode} from "jwt-decode";

describe('UserService', () => {
  let service: UserService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule],
      providers: [UserService, AuthenticationService, UserControllerService]
    });
    service = TestBed.inject(UserService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should add user', () => {
    const dto = {} as RegisterDto;
    const file = new File([''], 'mockFile.txt', { type: 'text/plain' });

    service.addUser(dto, file).subscribe(() => {});

    const req = httpMock.expectOne('http://localhost:8090/api/users');
    expect(req.request.method).toBe('POST');
    expect(req.request.body instanceof FormData).toBeTruthy();
    expect(req.request.body.get('file')).toEqual(file);
    req.flush({});
  });

  it('should login', () => {
    const authRequest: AuthenticationRequest = { /* Provide valid auth request */ };

    service.login(authRequest).subscribe(() => {});

    const req = httpMock.expectOne('http://localhost:8090/api/auth/authenticate');
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(authRequest);
    req.flush({});
  });

  it('should get all users', () => {
    service.getAllUsers().subscribe(() => {});

    const req = httpMock.expectOne('http://localhost:8090/api/users');
    expect(req.request.method).toBe('GET');
    req.flush([]);
  });

  it('should check if user is logged in and access token is valid', () => {
    spyOn(service, 'isUserLoggedAndAccessTokenValid').and.returnValue(true); // Spy on the service method
    expect(service.isUserLoggedAndAccessTokenValid()).toBeTruthy();
  });

  it('should update user', () => {
    const id = 1;
    const registerDto: RegisterDto = {}; // Provide valid RegisterDto
    const file = new File([''], 'mockFile.txt', { type: 'text/plain' });

    service.updateUser(id, registerDto, file).subscribe(() => {});

    const req = httpMock.expectOne(`http://localhost:8090/api/users/${id}`);
    expect(req.request.method).toBe('PUT');
    expect(req.request.body instanceof FormData).toBeTruthy();
    expect(req.request.body.get('file')).toEqual(file);
    req.flush({});
  });

  xit('should get user details', () => {
    localStorage.setItem('accessToken', 'validToken');
    spyOn(window as any, 'jwtDecode').and.returnValue({ sub: 'test@example.com' });
    const userDetails = service.getUserDetails();
    expect(userDetails).toEqual({ email: 'test@example.com' });
  });
});
