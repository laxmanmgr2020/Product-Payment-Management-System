import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {AxiosService} from '../../axios.service';
import {AuthService} from "../../auth.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent {
  username: string = '';
  password: string = '';

  constructor(private router: Router, private authService: AuthService) {
  }

  onSubmit() {
    this.authService.login(this.username, this.password);
  }

}
