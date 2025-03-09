import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from './pages/login/login.component';
import {ProductComponent} from './pages/product/product.component';
import {OrderComponent} from './pages/order/order.component';
import {DashboardComponent} from './pages/dashboard/dashboard.component';
import {PaymentComponent} from "./pages/payment/payment.component";

const routes: Routes = [
  {path: '', component: ProductComponent},
  {path: 'payment', component: PaymentComponent},
  {path: 'login', component: LoginComponent},
  {path: 'order', component: OrderComponent},
  {path: 'dashboard', component: DashboardComponent},
  {path: '**', redirectTo: '', pathMatch: 'full'}, // Default route
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}

