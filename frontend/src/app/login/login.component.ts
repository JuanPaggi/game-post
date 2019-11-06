import { Component, OnInit } from '@angular/core';
import { UsuariosService } from '../services/usuarios/usuarios.service';
import { UsuarioItem } from '../providers/entities/UsuarioItem.entity';
import { UsuariosDto } from '../providers/dto/UsuariosDto';
import { Router } from '@angular/router';
import { User } from '../providers/model/user.model';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  usuario: String;
  clave: String;

  usuarios: UsuarioItem[];

  constructor(
    private router: Router,
    private usuariosSrv: UsuariosService,
  ) { }

  ngOnInit() {
    this.getUsuarios();
  }

  getUsuarios(){
    this.usuariosSrv.getUsuarios(new UsuariosDto()).subscribe(
      response => {
        this.usuarios = response;
      }
    )
  }

  ComprobarUsuario(){
    this.usuarios.forEach(usuario => {
      if(usuario.usuario === this.usuario && usuario.clave === this.clave){
        this.logIn(this.usuario, usuario.id_usuario, event);
        this.router.navigateByUrl(`/`);
      }
    });
  }

  logIn(username: String, id_usuario: number, event: Event) {
    event.preventDefault(); // Avoid default action for the submit button of the login form

    let u: User = {username, id_usuario};  
    this.usuariosSrv.setUserLoggedIn(u);

  }

}
