import { Component, OnInit } from '@angular/core';
import { User } from '../providers/model/user.model';
import { UsuarioItem } from '../providers/entities/UsuarioItem.entity';
import { UsuariosService } from '../services/usuarios/usuarios.service';
import { UsuariosDto } from '../providers/dto/UsuariosDto';
import { Router } from '@angular/router';

@Component({
  selector: 'app-panel-usuarios',
  templateUrl: './panel-usuarios.component.html',
  styleUrls: ['./panel-usuarios.component.css']
})
export class PanelUsuariosComponent implements OnInit {

  usuarios: UsuarioItem[];

  id_usuario: number;
	nombre: String;
	apellido: String;
	email: String;
	usuario: String;
	clave: String;
  pais: String;
  fecha_inscripcion: Date;
  nivel: number;
  puntos: number;
  privilegios: String[];

  user: User;

  constructor(
    private router: Router,
    private usuarioSrv: UsuariosService,
  ) {}

  ngOnInit() {
    this.user = this.usuarioSrv.getUserLoggedIn();
    this.getUsuarios();
  }

  getUsuarios() {
    this.usuarioSrv.getUsuarios(new UsuariosDto()).subscribe(
      response => {
        this.usuarios = response;
      }
    );
  }

  borrarUsuario(id:number){
    this.usuarioSrv.deleteUsuario(id).subscribe();
    for (let index = 0; index < this.usuarios.length; index++) {
      if (this.usuarios[index].id_usuario === id) {
        this.usuarios.splice(index,1);
      }
    }
  }

  volverPanel(){
    this.router.navigateByUrl(`panel`);
  }

}
