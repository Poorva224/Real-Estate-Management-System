import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Property } from 'src/app/models/property';
import { PropertyService } from 'src/app/services/property.service';

@Component({
  selector: 'app-property',
  templateUrl: './property.component.html',
  styleUrls: ['./property.component.css']
})
export class PropertyComponent implements OnInit {

  propertyType!:string;
  locality!:string;
  properties:any;

  property:Property = new Property('','','');

  constructor(private service:PropertyService, private router:Router) { }

  ngOnInit(): void {
  }

  createProperty(){
    this.service.createProperty(this.property).subscribe((res:any)=>{
      this.service.data=res;
      console.log(res);
      alert("Property Created Successfully!");
    },
    (error:any)=>{
      console.log(error);
      alert("Property Creation Failed!");
    })
  }

  getAllProperties(){
    this.service.getAllProperties().subscribe((res:any)=>{
      this.properties = res;
      console.log(res);
    })
  }

  getAllPropertiesByType(){
    this.service.getAllPropertiesByType(this.propertyType).subscribe((res:any)=>{
      this.properties = res;
      console.log(res);
    })
  }

  getAllPropertiesByLocality(){
    this.service.getAllPropertiesByLocality(this.locality).subscribe((res:any)=>{
      this.properties = res;
      console.log(res);
    })
  }

}
