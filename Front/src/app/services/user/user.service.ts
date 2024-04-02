import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {jwtDecode} from "jwt-decode";
import {
    AuthenticationControllerService,
    AuthenticationRequest,
    RegisterDto,
    UserControllerService,
} from "../../../app-api";


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

    addUser(addRequest: RegisterDto) {
        return this.userService.add(addRequest);
    }

    getUserDetails() {
        this.token = localStorage.getItem('accessToken')
        let decodedJwt: any = jwtDecode(this.token);
        console.log(decodedJwt) ;
        this.email = decodedJwt.sub;
        return {
            'email': this.email,
        }
    }

    setConnectedUser(user: any): void {
        localStorage.setItem('connectedUser', JSON.stringify(user));
        console.log(localStorage.getItem('connectedUser')) ;
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
        console.log("access token set")
        localStorage.setItem('accessToken', data['access_token']);
    }

    getAllUsers() {
        return this.userService.getusers();
    }

}

