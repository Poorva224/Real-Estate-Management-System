import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ManagerService {

  data:any;
  managerUrl:string = "http://localhost:8083/manager/";

  constructor(private _httpClient:HttpClient) { }

  createExecutive(manager:any):any{
    return this._httpClient.post(this.managerUrl+"createExecutive", manager, {responseType:'text'});
  }

  getAllExecutives():any{
    return this._httpClient.get(this.managerUrl+"getAllExecutives");
  }

  getAllCustomers():any{
    return this._httpClient.get(this.managerUrl+"getAllCustomers");
  }

  getAllExecutivesByLocality(locality:string):any{
    return this._httpClient.get(this.managerUrl+"getAllExecutivesByLocality/"+locality);
  }

  getCustomersById(custid1:number):any{
    return this._httpClient.get(this.managerUrl+"getCustomersById/"+custid1);
  }

  getExecutiveDetails(execid1:number):any{
    return this._httpClient.get(this.managerUrl+"getExecutiveDetails/"+execid1);
  }

  assignExecutive(execid:number, custid:number):any{
    return this._httpClient.put(this.managerUrl+execid+"/assignExecutive/"+custid, {responseType:'text'});
  }
}
