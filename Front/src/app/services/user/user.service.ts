import {Injectable} from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Router} from "@angular/router";
import {
    AuthenticationControllerService,
    AuthenticationRequest, AuthenticationResponse,
    UserControllerService, AddRequest
} from "../../../app-api";
import {jwtDecode} from "jwt-decode";


@Injectable({
    providedIn: 'root'
})
export class UserService {

    token!: any;
    email!: string;
    name!: string;
    id!: number;
    roles!: any;

    constructor(private authenticationService: AuthenticationControllerService, private http: HttpClient,
                private router: Router, private userService: UserControllerService
    ) {
    }

    login(authenticationRequest: AuthenticationRequest) {
        return this.authenticationService.authenticate(authenticationRequest);
    }

    addUser(addRequest: AddRequest) {
        return this.userService.add(addRequest);
    }

    getUserDetails() {
        this.token = localStorage.getItem('accessToken')
        let decodedJwt: any = jwtDecode(this.token);
        console.log(decodedJwt) ;
        this.email = decodedJwt.sub;
        this.roles = decodedJwt.role;
        this.name = decodedJwt.sub;
        this.id = decodedJwt.id;
        return {
            'id': this.id,
            'email': this.email,
            'roles': this.roles,
            'name': this.name
        }
    }

    setConnectedUser(user: any): void {
        localStorage.setItem('connectedUser', JSON.stringify(user));
    }

    getConnectedUser() {
        const storedUserString = localStorage.getItem('connectedUser');
        if (storedUserString) {
            const storedUser = JSON.parse(storedUserString);
            return storedUser;
        } else {
            console.log('No user data found in localStorage');
        }
    }


    isUserLoggedAndAccessTokenValid(): boolean {
        const accessToken = localStorage.getItem('accessToken');
        if (accessToken) {
            if (this.isValidAccessToken(accessToken)) {
                return true;
            }
        }
        localStorage.clear();
        this.router.navigate(['/signIn']);
        return false;
    }

    isValidAccessToken(token: string): boolean {
        try {
            const tokenData = JSON.parse(atob(token.split('.')[1]));

            const currentTimeInSeconds = Math.floor(Date.now() / 1000);
            if (tokenData.exp && tokenData.exp > currentTimeInSeconds) {
                return true;

            }
        } catch (error) {
        }

        return false;
    }


    setToken(data: any) {
        localStorage.setItem('accessToken', data['access_token']);
    }

    logout(): void {
        localStorage.removeItem('connectedUser');
        localStorage.removeItem('accessToken');
        this.router.navigate(['/SignIn']);
    }

    getAllUsers() {
        return this.userService.getusers();
    }

    getUserByEmail(email:string) {
        return this.userService.getUser(email) ;
    }

}

