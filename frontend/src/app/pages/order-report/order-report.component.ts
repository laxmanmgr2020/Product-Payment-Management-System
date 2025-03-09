import {Component, OnInit} from '@angular/core';
import {AxiosService} from "../../axios.service";

@Component({
  selector: 'app-order-report',
  templateUrl: './order-report.component.html',
  styleUrl: './order-report.component.css'
})

export class OrderReportComponent implements OnInit {
  order = {
    daily: '',
    weekly: '',
    monthly: ''
  };

  constructor(private axiosService: AxiosService) {
  }

  ngOnInit(): void {
    this.axiosService.request(
      "GET",
      "/admin/orderReport"
    ).then(
      response => {
        this.order = response.data;
      }).catch(
      error => {
        console.error('Order report fetch failed', error);
      }
    );
  }
}



