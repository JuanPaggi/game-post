import { Component, OnInit } from '@angular/core';
import { UsuariosService } from '../services/usuarios/usuarios.service';
import { User } from '../providers/model/user.model';
import { AdminService } from '../services/admin/admin.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  user: User;
  userAdm: User;
  usuario: String;

  constructor(
    private usuariosSrv: UsuariosService,
    private adminSrv: AdminService,
    private router: Router,
  ) { }

  ngOnInit() {
    this.user = this.usuariosSrv.getUserLoggedIn();
    this.userAdm = this.adminSrv.getUserLoggedIn();
    if(this.user){
      this.usuario = this.user.username;
    }
    if(this.userAdm){
      this.usuario = this.userAdm.username;
    }
  }

  clickedLogin(){
    this.router.navigateByUrl(`/login`);
  }
  
  clickedRegistrarse(){
    this.router.navigateByUrl(`/registro`);
  }

  clickedSalir(){
    this.usuariosSrv.setUserLoggedOut();
    this.adminSrv.setUserLoggedOut();
    window.location.reload();
  }

  clickedPanel(){
    this.router.navigateByUrl(`/panel`);
  }
}
