import { Component, OnInit } from '@angular/core';
import { UsuariosService } from '../services/usuarios/usuarios.service';
import { User } from '../providers/model/user.model';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  user: User;
  usuario: String;

  constructor(
    private usuariosSrv: UsuariosService,
  ) { }

  ngOnInit() {
    this.user = this.usuariosSrv.getUserLoggedIn();
    if(this.user){
      this.usuario = this.user.username;
    }
  }
}
