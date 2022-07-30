import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/models/user';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  selRole!:string;
  user:User = new User('','');

  constructor(private service:AuthService, private router:Router) { }

  ngOnInit(): void {
  }

  register(){
    this.service.register(this.user).subscribe((res:any)=>{
      this.service.data=res;
      this.service.role = this.selRole;
      console.log(res);
      alert("Registration successful!");
      this.router.navigate(['login']);
    },
    (error:any)=>{
      console.log(error);
      alert("Registration Failed!");
    })
  }

}
