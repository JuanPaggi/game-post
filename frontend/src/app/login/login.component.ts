import { Component, OnInit } from '@angular/core';
import { UsuariosService } from '../services/usuarios/usuarios.service';
import { UsuarioItem } from '../providers/entities/UsuarioItem.entity';
import { Router } from '@angular/router';
import { User } from '../providers/model/user.model';
import { LoginDto } from '../providers/dto/dtoLogin/LoginDto';
import { JuegoByIdDto } from '../providers/dto/dtoById/JuegoByIdDto';
import { UsuarioByIdDto } from '../providers/dto/dtoById/UsuarioByIdDto';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  usuario: String;
  clave: String;
  privilegios: number[];
  htmlToAdd: String;

  check:boolean;

  id_usuario:number;

  Usuario: UsuarioItem;

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
          this.usuariosSrv.getUsuario( new UsuarioByIdDto(this.id_usuario)).subscribe(
            response=>{
              this.Usuario = response;
              this.logIn(this.usuario, this.id_usuario, this.Usuario.privilegios, event);
              window.location.href = "/";
            }
          );
        } else{
          this.htmlToAdd = '<p>Datos Incorrectos<p>';
        }
      }
    )
  }

  logIn(username: String, id_usuario: number, privilegios: number[], event: Event) {
    event.preventDefault(); 
    let u: User = {username, id_usuario, privilegios};  
    this.usuariosSrv.setUserLoggedIn(u);
  }

  volverHome(){
    this.router.navigateByUrl(`/`);
  }

}
