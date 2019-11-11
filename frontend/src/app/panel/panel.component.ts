import { Component, OnInit, ElementRef, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { User } from '../providers/model/user.model';
import { AdminService } from '../services/admin/admin.service';

@Component({
  selector: 'app-panel',
  templateUrl: './panel.component.html',
  styleUrls: ['./panel.component.css']
})
export class PanelComponent implements OnInit {

  userAdm: User;

  constructor(
    private adminSrv: AdminService,
    private router: Router,
  ) { }

  ngOnInit() {
    this.userAdm = this.adminSrv.getUserLoggedIn();
  }

  volverHome(){
    this.router.navigateByUrl(`/`);
  }

  irPanelJuegos(){
    this.router.navigateByUrl(`panel/panel-juegos`);
  }

  irPanelNoticias(){
    this.router.navigateByUrl(`panel/panel-noticias`);
  }

}
