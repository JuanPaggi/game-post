import { Component, OnInit } from '@angular/core';
import { AdminItem } from '../providers/entities/AdminItem.entity';
import { AdminService } from '../services/admin/admin.service';
import { UsuariosService } from '../services/usuarios/usuarios.service';
import { Router } from '@angular/router';
import { User } from '../providers/model/user.model';
import { LoginDto } from '../providers/dto/dtoLogin/LoginDto';

@Component({
  selector: 'app-login-admin',
  templateUrl: './login-admin.component.html',
  styleUrls: ['./login-admin.component.css']
})
export class LoginAdminComponent implements OnInit {

  usuario: String;
  clave: String;
  htmlToAdd: String;

  id_admin:number;

  usuarios: AdminItem[];

  user: User;
  userAdm: User;

  constructor(
    private router: Router,
    private usuariosSrv: UsuariosService,
    private adminSrv: AdminService,
  ) { }

  ngOnInit() {
    this.user = this.usuariosSrv.getUserLoggedIn();
    this.userAdm = this.adminSrv.getUserLoggedIn();

  }

  ComprobarAdmin(){
    let login = new LoginDto();
    login.usuario = this.usuario;
    login.clave = this.clave;
    this.adminSrv.verificarAdmin(login).subscribe(
      response => {
        if(response != 0){
          this.id_admin = response;
          this.logIn(this.usuario, this.id_admin, event);
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
    this.adminSrv.setUserLoggedIn(u);
  }

}
