import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import '../services/usuarios/usuarios.service';
import { UsuariosService } from '../services/usuarios/usuarios.service';
import { User } from '../providers/model/user.model';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  user: User;

  constructor(
    private router: Router,
    private usuariosSrv: UsuariosService,
  ) {}

  ngOnInit() {
    this.user = this.usuariosSrv.getUserLoggedIn();
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

  clickedRegistrarse(){
    this.router.navigateByUrl(`/registro`);
  }
  clickedPanel(){
    this.router.navigateByUrl(`/panel`);
  }

  clickedSalir(){
    this.usuariosSrv.setUserLoggedOut();
    window.location.reload();
  }

}
