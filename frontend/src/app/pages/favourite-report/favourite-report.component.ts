import {Component, OnInit} from '@angular/core';
import {AxiosService} from "../../axios.service";

@Component({
  selector: 'app-favourite-report',
  templateUrl: './favourite-report.component.html',
  styleUrl: './favourite-report.component.css'
})
export class FavouriteReportComponent implements OnInit {
  favs = {
    daily: '',
    weekly: '',
    monthly: ''
  };

  constructor(private axiosService: AxiosService) {
  }

  ngOnInit(): void {
    this.axiosService.request(
      "GET",
      "/admin/mostlyOrdered"
    ).then(
      response => {
        this.favs = response.data;
      }).catch(
      error => {
        console.error('Favourite report fetch failed', error);
        this.axiosService.cleanStorage();
      }
    );
  }
}
