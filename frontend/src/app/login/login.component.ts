import { Component, OnInit } from '@angular/core';
import { UsuariosService } from '../services/usuarios/usuarios.service';
import { UsuarioItem } from '../providers/entities/UsuarioItem.entity';
import { Router } from '@angular/router';
import { User } from '../providers/model/user.model';
import { LoginDto } from '../providers/dto/dtoLogin/LoginDto';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  usuario: String;
  clave: String;
  htmlToAdd: String;

  id_usuario:number;

  usuarios: UsuarioItem[];

  user: User;

  constructor(
    private router: Router,
    private usuariosSrv: UsuariosService,
  ) { }

  ngOnInit() {
    this.user = this.usuariosSrv.getUserLoggedIn();

  }

  ComprobarUsuario(){
    let login = new LoginDto();
    login.usuario = this.usuario;
    login.clave = this.clave;
    this.usuariosSrv.verificarUsuario(login).subscribe(
      response => {
        if(response != 0){
          this.id_usuario = response;
          this.logIn(this.usuario, this.id_usuario, event);
          window.location.href = "/";
        } else{
          this.htmlToAdd = '<p class="list-group-item">Datos Incorrectos<p>';
        }
      }
    )
  }

  logIn(username: String, id_usuario: number, event: Event) {
    event.preventDefault(); 
    let u: User = {username, id_usuario};  
    this.usuariosSrv.setUserLoggedIn(u);
  }

}
