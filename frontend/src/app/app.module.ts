import {NgModule} from '@angular/core';
import {BrowserModule, provideClientHydration} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {LoginComponent} from './pages/login/login.component';
import {ProductComponent} from './pages/product/product.component';
import {OrderComponent} from './pages/order/order.component';
import {DashboardComponent} from './pages/dashboard/dashboard.component';
import {PaymentComponent} from './pages/payment/payment.component';
import {FormsModule} from "@angular/forms";
import {NgOptimizedImage} from "@angular/common";

import {AxiosService} from './axios.service';
import {MenuComponent} from './menu/menu.component';
import { OrderReportComponent } from './pages/order-report/order-report.component';
import { PaymentReportComponent } from './pages/payment-report/payment-report.component';
import { FavouriteReportComponent } from './pages/favourite-report/favourite-report.component';


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    ProductComponent,
    OrderComponent,
    DashboardComponent,
    PaymentComponent,
    MenuComponent,
    OrderReportComponent,
    PaymentReportComponent,
    FavouriteReportComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    NgOptimizedImage
  ],
  providers: [
    provideClientHydration(),
    AxiosService
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
