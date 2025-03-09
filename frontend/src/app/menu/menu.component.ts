import {Component, OnDestroy, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import { Subscription } from 'rxjs';
import {AuthService} from "../auth.service";

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrl: './menu.component.css'
})
export class MenuComponent implements OnInit, OnDestroy {
  constructor(private router: Router, private authService: AuthService) {
  }

  isLoggedIn: boolean = false;
  isAdmin: boolean = false;

  private subscription: Subscription | undefined;

  ngOnInit(): void {
    // Initialize the menu state
    this.checkLoginStatus();

    // Subscribe to login state changes
    this.subscription = this.authService.loginStateChanged$.subscribe(() => {
      this.checkLoginStatus();
    });
  }


  ngOnDestroy() {
    // Unsubscribe to avoid memory leaks
    this.subscription?.unsubscribe();
  }

  checkLoginStatus(): void {
    // Retrieve values from localStorage
    const authToken = window.localStorage.getItem('auth_token');
    const userRole = window.localStorage.getItem('role');

    this.isLoggedIn = !!authToken;
    this.isAdmin = userRole === 'ADMIN';

  }

  logout(): void {
    // Clear localStorage
    window.localStorage.removeItem('auth_token');
    window.localStorage.removeItem('role');

    // Redirect to login page
    this.router.navigate(['./product']);

    this.checkLoginStatus();

  }

}

