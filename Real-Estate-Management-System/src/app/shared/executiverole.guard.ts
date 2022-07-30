import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from '../services/auth.service';

@Injectable({
  providedIn: 'root'
})
export class ExecutiveroleGuard implements CanActivate {
  constructor(private service:AuthService, private router:Router){}
  canActivate(){
    if(this.service.hasRoleExecutive()){
      return true;
    }
    else{
      alert("Not Allowed. Only For Executive!");
      return false;
    }
   
  }
  
}
