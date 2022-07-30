import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  data:any;
  customerUrl:string = "http://localhost:8081/customer/";

  constructor(private _httpClient:HttpClient) { }

  createCustomer(customer:any):any{
    return this._httpClient.post(this.customerUrl+"createCustomer", customer, {responseType:'text'});
  }

  getAllRequirements():any{
    return this._httpClient.get(this.customerUrl+"getAllRequirements");
  }

  getAllCustomers():any{
    return this._httpClient.get(this.customerUrl+"getAllCustomers");
  }

  getProperties():any{
    return this._httpClient.get(this.customerUrl+"getProperties");
  }

  getCustomerDetails(custid1:number):any{
    return this._httpClient.get(this.customerUrl+"getCustomerDetails/"+custid1);
  }

  assignRequirements(custid:number, reqid:number):any{
    return this._httpClient.put(this.customerUrl+custid+"/assignRequirements/"+reqid, {responseType:'text'});
  }
}
