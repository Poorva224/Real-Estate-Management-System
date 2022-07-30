import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Customer } from 'src/app/models/customer';
import { CustomerService } from 'src/app/services/customer.service';

@Component({
  selector: 'app-customer',
  templateUrl: './customer.component.html',
  styleUrls: ['./customer.component.css']
})
export class CustomerComponent implements OnInit {

  custid!:number;
  custid1!:number;
  reqid!:number;
  customer1:any;
  customers:any;
  requirements:any;
  properties:any;

  customer:Customer = new Customer('','','','');

  constructor(private service:CustomerService, private router:Router) { }

  ngOnInit(): void {
  }

  createCustomer(){
    this.service.createCustomer(this.customer).subscribe((res:any)=>{
      this.service.data=res;
      console.log(res);
      alert("Customer Created Successfully!");
    },
    (error:any)=>{
      console.log(error);
      alert("Customer Creation Failed!");
    })
  }

  getAllRequirements(){
    this.service.getAllRequirements().subscribe((res:any)=>{
      this.requirements = res;
      console.log(res);
    })
  }

  getAllCustomers(){
    this.service.getAllCustomers().subscribe((res:any)=>{
      this.customers = res;
      console.log(res);
    })
  }

  assignRequirements(){
    this.service.assignRequirements(this.custid, this.reqid).subscribe((res:any)=>{
      console.log(res);
    },
      (error:any)=>{
      console.log(error);
      if(error.status==200){
        alert("Requirement Assigned Successfully!");
      }
      else{
        alert("Requirement Assignment Failed!");
      }
    })
  }

  getCustomerDetails(){
    this.service.getCustomerDetails(this.custid1).subscribe((res:any)=>{
      this.customer1 = res;
      console.log(res);
    })
  }

  getProperties(){
    this.service.getProperties().subscribe((res:any)=>{
      this.properties = res;
      console.log(res);
    })
  }
}
