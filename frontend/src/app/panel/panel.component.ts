import { Component, OnInit, ElementRef, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { User } from '../providers/model/user.model';
import { UsuariosService } from '../services/usuarios/usuarios.service';

@Component({
  selector: 'app-panel',
  templateUrl: './panel.component.html',
  styleUrls: ['./panel.component.css']
})
export class PanelComponent implements OnInit {

  user: User;

  constructor(
    private usuarioSrv: UsuariosService,
    private router: Router,
  ) { }

  ngOnInit() {
    this.user = this.usuarioSrv.getUserLoggedIn();
  }

  volverHome(){
    this.router.navigateByUrl(`/`);
  }

  irPanelJuegos(){
    this.router.navigateByUrl(`panel/panel-juegos`);
  }

  irPanelNoticias(){
    this.router.navigateByUrl(`panel/panel-noticias`);
  }

  irPanelUsuarios(){
    this.router.navigateByUrl(`panel/panel-usuarios`);
  }

}
