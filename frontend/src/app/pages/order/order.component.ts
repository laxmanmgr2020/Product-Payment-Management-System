import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {AxiosService} from "../../axios.service";

@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.css']
})
export class OrderComponent implements OnInit {
  orders: any[] = [];

  selectedOrder: any;
  paymentAmount: number = 0;
  orderId: number = 0;
  isInvalidAmount: boolean = false;

  constructor(private router: Router, private axiosService: AxiosService) {
  }

  ngOnInit() {
    this.axiosService.request(
      "GET",
      "/orders"
    ).then(
      response => {
        this.orders = response.data;
      }).catch(
      error => {
        console.error('Order fetch failed', error);
        this.axiosService.cleanStorage();
        this.router.navigate(['/login']);
      }
    );

  }

  // Set the selected order when the "Make Payment" button is clicked
  selectOrder(order: any) {
    this.selectedOrder = order;
    this.paymentAmount = 0; // Reset payment amount
    this.orderId = order.id;
    this.isInvalidAmount = false; // Reset validation flag
  }

  get selectedOrderTotal(): number {
    return this.selectedOrder ? this.selectedOrder.product.price * this.selectedOrder.quantity : 0;
  }


  // Process the payment
  processPayment() {
    if (this.paymentAmount === this.selectedOrderTotal) {
      this.isInvalidAmount = false;
      this.callPayment();
    } else {
      this.isInvalidAmount = true;
    }
  }

  callPayment() {
    this.axiosService.request(
      "POST",
      `/payment/save/${this.orderId}`,
      {
        amount: this.paymentAmount
      }).then(
      response => {
        // Call your payment service here
        console.log('Payment processed successfully');
        // Close the modal manually
        const modal = document.getElementById('paymentModal');
        if (modal) {
          modal.classList.remove('show');
          modal.setAttribute('aria-hidden', 'true');
          modal.style.display = 'none';
          document.body.classList.remove('modal-open');
          const modalBackdrop = document.querySelector('.modal-backdrop');
          if (modalBackdrop) {
            modalBackdrop.remove();
          }

          this.router.navigate(['/payment'])
        }

      }).catch(
      error => {
        console.error('Error saving order', error);
      }
    );
  }
}
