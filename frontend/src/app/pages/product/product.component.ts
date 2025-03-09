import {Component, OnInit} from '@angular/core';
import {AxiosService} from "../../axios.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css']
})
export class ProductComponent implements OnInit {
  products: any[] = [];
  isLoggedIn: boolean = false;

  orderDetails = {
    productId: null,
    quantity: 1,
    location: '',
    totalAmount: 0
  };

  constructor(private router: Router, private axiosService: AxiosService) {
  }

  ngOnInit() {
    if (this.axiosService.getAuthToken() != null) {
      this.isLoggedIn = true;
    }

    this.axiosService.request(
      "GET",
      "/products"
    ).then(
      response => {
        this.products = response.data;
      }).catch(
      error => {
        console.error('Product fetch failed', error);
      }
    );
  }

  openOrderModal(product: any): void {
    this.orderDetails.productId = product.id;
    this.orderDetails.quantity = 1;
    this.orderDetails.location = '';
    this.orderDetails.totalAmount = product.price; // Initialize total amount with the product price
  }

  updateTotalAmount(): void {
    const product = this.products.find(p => p.id === this.orderDetails.productId);
    if (product) {
      this.orderDetails.totalAmount = product.price * this.orderDetails.quantity;
    }
  }

  placeOrder(): void {
    const product = this.products.find(p => p.id === this.orderDetails.productId);
    if (product) {
      this.orderDetails.totalAmount = product.price * this.orderDetails.quantity;

      // Make API call to save the order

      this.axiosService.request(
        "POST",
        `/orders/save/${this.orderDetails.productId}`,
        {
          quantity: this.orderDetails.quantity,
          address: this.orderDetails.location
        }).then(
        response => {
          console.log('Order saved successfully', response);
          // Close the modal manually
          const modal = document.getElementById('orderModal');
          if (modal) {
            modal.classList.remove('show');
            modal.setAttribute('aria-hidden', 'true');
            modal.style.display = 'none';
            document.body.classList.remove('modal-open');
            const modalBackdrop = document.querySelector('.modal-backdrop');
            if (modalBackdrop) {
              modalBackdrop.remove();
            }
            this.router.navigate(['/order'])
          }
        }).catch(
        error => {
          console.error('Error saving order', error);
        }
      );
    }
  }

}

