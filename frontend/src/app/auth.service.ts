import {Injectable} from '@angular/core';
import {Subject} from "rxjs";
import {Router} from "@angular/router";
import {AxiosService} from "./axios.service";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  // Use a Subject to notify about login state changes
  private loginStateChanged = new Subject<void>();
  loginStateChanged$ = this.loginStateChanged.asObservable(); // Expose as observable

  constructor(private router: Router, private axiosService: AxiosService) {
  }

  // Method to handle login
  login(username: string, password: string) {

    this.axiosService.request(
      "POST",
      "/login",
      {
        username: username,
        password: password
      }).then(response => {
      this.axiosService.setAuthentication(response.data);

      // Notify subscribers about the login state change
      this.loginStateChanged.next();

      this.router.navigate(['/product']);
    }).catch(
      error => {
        this.axiosService.setAuthentication(null);
        console.error('Login failed', error);
      }
    );
  }

  // Method to handle logout
  logout() {
    // Clear the token and role from localStorage
    localStorage.removeItem('token');
    localStorage.removeItem('role');

    // Notify subscribers about the login state change
    this.loginStateChanged.next();

    // Redirect to the login page
    this.router.navigate(['/login']);
  }

  // Method to check if the user is logged in
  isUserLoggedIn(): boolean {
    return !!localStorage.getItem('token');
  }

  // Method to get the user's role
  getRole(): string | null {
    return localStorage.getItem('role');
  }
}
