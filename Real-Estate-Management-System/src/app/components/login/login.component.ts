import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/models/user';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  selRole1!:string;
  user:User = new User('','');

  constructor(private service:AuthService, private router:Router) { }

  ngOnInit(): void {
  }

  login(){
    this.service.authenticate(this.user).subscribe((res:any)=>{
      this.service.data=res;
      this.service.role1=this.selRole1;
      if(this.service.validatingRole()){
        console.log(res);
        localStorage.setItem('token', res.token);

        if(this.service.role1 === 'Manager')
        {
        this.router.navigate(['manager']);
      }

        else if(this.service.role1 === 'Customer')
        {
        this.router.navigate(['customer']);
        }
        else 
        {
        this.router.navigate(['executive']);
        
        }
      }
      else{
        alert("Login Failed!");
      }
    })
  }

}