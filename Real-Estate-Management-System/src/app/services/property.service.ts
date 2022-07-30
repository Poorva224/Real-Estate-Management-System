import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class PropertyService {

  data:any;
  propertyUrl:string = "http://localhost:8082/property/";

  constructor(private _httpClient:HttpClient) { }

  createProperty(property:any):any{
    return this._httpClient.post(this.propertyUrl+"createProperty", property, {responseType:'text'});
  }

  getAllProperties():any{
    return this._httpClient.get(this.propertyUrl+"getAllProperties");
  }

  getAllPropertiesByType(propertyType:string):any{
    return this._httpClient.get(this.propertyUrl+"getAllPropertiesByType/"+propertyType);
  }

  getAllPropertiesByLocality(locality:string):any{
    return this._httpClient.get(this.propertyUrl+"getAllPropertiesByLocality/"+locality);
  }
}
