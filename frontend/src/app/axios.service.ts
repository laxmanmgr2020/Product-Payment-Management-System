import {Injectable} from '@angular/core';
import axios from "axios";

@Injectable({
  providedIn: 'root'
})
export class AxiosService {

  constructor() {
    axios.defaults.baseURL = 'http://localhost:8080';
    axios.defaults.headers.post['Content-Type'] = 'application/json';
  }

  getAuthToken(): string | null {
    return window.localStorage.getItem("auth_token");
  }

  cleanStorage(){
    window.localStorage.removeItem("auth_token");
    window.localStorage.removeItem("role");
    window.localStorage.removeItem("username");
  }

  setAuthentication(response: { token: string; role: string; username: string } | null): void {
    if (response !== null) {
      window.localStorage.setItem("auth_token", response.token);
      window.localStorage.setItem("role", response.role);
      window.localStorage.setItem("username", response.username);
    } else {
      window.localStorage.removeItem("auth_token");
      window.localStorage.removeItem("role");
      window.localStorage.removeItem("username");
    }
  }


  request(method: string, url: string, data: any = null): Promise<any> {
    let headers: any = {};

    if (this.getAuthToken() !== null) {
      headers = {"Authorization": "Bearer " + this.getAuthToken()};
    }

    return axios({
      method: method,
      url: url,
      data: data,
      headers: headers
    });
  }
}
