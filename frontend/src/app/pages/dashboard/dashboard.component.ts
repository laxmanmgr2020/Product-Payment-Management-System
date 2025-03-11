import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  constructor(private router: Router) {
  }

  ngOnInit(): void {
    const authToken = window.localStorage.getItem('auth_token');
    const userRole = window.localStorage.getItem('role');

    if (!(!!authToken && userRole == 'ADMIN')) {
      this.router.navigate(['/products'])
    }

  }
}
