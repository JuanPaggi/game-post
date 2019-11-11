import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AdminItem } from '../providers/entities/AdminItem.entity';
import { AdminService } from '../services/admin/admin.service';
import { User } from '../providers/model/user.model';
import { AdminDto } from '../providers/dto/AdminDto';

@Component({
  selector: 'app-panel-admin',
  templateUrl: './panel-admin.component.html',
  styleUrls: ['./panel-admin.component.css']
})
export class PanelAdminComponent implements OnInit {

  admins: AdminItem[];

  id_admin: number;
	usuario: String;
  clave: String;
  
  userAdm: User;

  constructor(
    private router: Router,
    private adminSrv: AdminService,
  ) { }

  ngOnInit() {
    this.userAdm = this.adminSrv.getUserLoggedIn();
    this.getAdmins();
  }

  getAdmins(){
    this.adminSrv.getAdmins(new AdminDto()).subscribe(
      response => {
        this.admins = response;
      }
    );
  }

  volverPanel(){
    this.router.navigateByUrl(`panel`);
  }

}
