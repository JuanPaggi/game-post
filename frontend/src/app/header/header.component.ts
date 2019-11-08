import { Component, OnInit } from '@angular/core';
import { UsuariosService } from '../services/usuarios/usuarios.service';
import { User } from '../providers/model/user.model';
import { AdminService } from '../services/admin/admin.service';

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
}
