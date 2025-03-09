import {Component, OnInit} from '@angular/core';
import {AxiosService} from "../../axios.service";

@Component({
  selector: 'app-payment-report',
  templateUrl: './payment-report.component.html',
  styleUrl: './payment-report.component.css'
})
export class PaymentReportComponent implements OnInit {
  payment = {
    daily: '',
    weekly: '',
    monthly: ''
  };

  constructor(private axiosService: AxiosService) {
  }

  ngOnInit(): void {
    this.axiosService.request(
      "GET",
      "/admin/paymentReport"
    ).then(
      response => {
        this.payment = response.data;
      }).catch(
      error => {
        console.error('Order report fetch failed', error);
      }
    );
  }
}
