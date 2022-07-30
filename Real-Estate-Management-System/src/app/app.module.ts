import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import {HttpClientModule, HTTP_INTERCEPTORS} from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { AuthService } from './services/auth.service';
import { CustomerComponent } from './components/customer/customer.component';
import { PropertyComponent } from './components/property/property.component';
import { ManagerComponent } from './components/manager/manager.component';
import { ExecutiveComponent } from './components/executive/executive.component';
import { TokenInterceptorService } from './services/token-interceptor.service';
import { PropertyService } from './services/property.service';
import { CustomerService } from './services/customer.service';
import { ManagerService } from './services/manager.service';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    CustomerComponent,
    PropertyComponent,
    ManagerComponent,
    ExecutiveComponent  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [AuthService, {provide:HTTP_INTERCEPTORS, useClass:TokenInterceptorService, multi: true}, PropertyService, CustomerService, ManagerService],
  bootstrap: [AppComponent]
})
export class AppModule { }
