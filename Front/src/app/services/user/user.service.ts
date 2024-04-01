import { Injectable } from '@angular/core';
import {Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private router:Router) { }

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
}
