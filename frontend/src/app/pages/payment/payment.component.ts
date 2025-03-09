import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {AxiosService} from "../../axios.service";

@Component({
  selector: 'app-payment',
  templateUrl: './payment.component.html',
  styleUrl: './payment.component.css'
})
export class PaymentComponent implements OnInit {
  payments: any[] = [];

  constructor(private router: Router, private axiosService: AxiosService) {
  }

  ngOnInit() {
    this.axiosService.request(
      "GET",
      "/payment"
    ).then(
      response => {
        this.payments = response.data;
      }).catch(
      error => {
        console.error('Payment fetch failed', error);
        this.axiosService.cleanStorage();
        this.router.navigate(['/login']);
      }
    );
  }
}
