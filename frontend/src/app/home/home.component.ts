import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import '../services/usuarios/usuarios.service';
import { UsuariosService } from '../services/usuarios/usuarios.service';
import { User } from '../providers/model/user.model';
import { AdminService } from '../services/admin/admin.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  user: User;
  userAdm: User;

  constructor(
    private router: Router,
    private usuariosSrv: UsuariosService,
    private AdminSrv: AdminService,
  ) {}

  ngOnInit() {
    this.user = this.usuariosSrv.getUserLoggedIn();
    this.userAdm = this.AdminSrv.getUserLoggedIn();
  }

  clickedJuegos(){
    this.router.navigateByUrl(`/juegos`);
  }

  clickedNoticias(){
    this.router.navigateByUrl(`/noticias`);
  }

  clickedLogin(){
    this.router.navigateByUrl(`/login`);
  }

  clickedLoginAdmin(){
    this.router.navigateByUrl(`/loginAdmin`);
  }

  clickedRegistrarse(){
    this.router.navigateByUrl(`/registro`);
  }
  clickedPanel(){
    this.router.navigateByUrl(`/panel`);
  }

  clickedSalir(){
    this.usuariosSrv.setUserLoggedOut();
    this.AdminSrv.setUserLoggedOut();
    window.location.reload();
  }

}
