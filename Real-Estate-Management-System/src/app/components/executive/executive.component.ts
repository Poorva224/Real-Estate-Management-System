import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ManagerService } from 'src/app/services/manager.service';

@Component({
  selector: 'app-executive',
  templateUrl: './executive.component.html',
  styleUrls: ['./executive.component.css']
})
export class ExecutiveComponent implements OnInit {

  execid!:number;
  custid!:number;
  customer:any;
  executive:any;

  constructor(private service:ManagerService, private router:Router) { }

  ngOnInit(): void {
  }

  getCustomersById(){
    this.service.getCustomersById(this.custid).subscribe((res:any)=>{
      this.customer = res;
      console.log(res);
    })
  }

  getExecutiveDetails(){
    this.service.getExecutiveDetails(this.execid).subscribe((res:any)=>{
      this.executive = res;
      console.log(res);
    })
  }

}
