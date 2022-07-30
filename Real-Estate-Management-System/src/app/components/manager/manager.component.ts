import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Executive } from 'src/app/models/executive';
import { ManagerService } from 'src/app/services/manager.service';

@Component({
  selector: 'app-manager',
  templateUrl: './manager.component.html',
  styleUrls: ['./manager.component.css']
})
export class ManagerComponent implements OnInit {

  execid!:number;
  custid!:number;
  execid1!:number;
  custid1!:number;
  locality!:string;
  customer1:any;
  executive1:any;
  customers:any;
  executives:any;

  executive:Executive = new Executive('','','','');

  constructor(private service:ManagerService, private router:Router) { }

  ngOnInit(): void {
  }

  createExecutive(){
    this.service.createExecutive(this.executive).subscribe((res:any)=>{
      this.service.data=res;
      console.log(res);
      alert("Executive Created Successfully!");
    },
    (error:any)=>{
      console.log(error);
      alert("Executive Creation Failed!");
    })
  }

  getAllExecutives(){
    this.service.getAllExecutives().subscribe((res:any)=>{
      this.executives = res;
      console.log(res);
    })
  }

  getAllExecutivesByLocality(){
    this.service.getAllExecutivesByLocality(this.locality).subscribe((res:any)=>{
      this.executives = res;
      console.log(res);
    })
  }

  getAllCustomers(){
    this.service.getAllCustomers().subscribe((res:any)=>{
      this.customers = res;
      console.log(res);
    })
  }

  assignExecutive(){
    this.service.assignExecutive(this.execid, this.custid).subscribe((res:any)=>{
      console.log(res);
    },
      (error:any)=>{
      console.log(error);
      if(error.status==200){
        alert("Customer Assigned Successfully!");
      }
      else{
        alert("Customer Assignment Failed!");
      }
    })
  }

  getCustomersById(){
    this.service.getCustomersById(this.custid1).subscribe((res:any)=>{
      this.customer1 = res;
      console.log(res);
    })
  }

  getExecutiveDetails(){
    this.service.getExecutiveDetails(this.execid1).subscribe((res:any)=>{
      this.executive1 = res;
      console.log(res);
    })
  }

}
