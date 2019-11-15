import { Component, OnInit } from '@angular/core';
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
    private usuariosSrv: UsuariosService,
  ) {}

  ngOnInit() {
    this.user = this.usuariosSrv.getUserLoggedIn();
  }

}
