import { Component, OnInit } from '@angular/core';
import { User } from '../providers/model/user.model';
import { AdminService } from '../services/admin/admin.service';
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

  userAdm: User;

  constructor(
    private router: Router,
    private adminSrv: AdminService,
    private usuariosSrv: UsuariosService,
  ) {}

  ngOnInit() {
    this.userAdm = this.adminSrv.getUserLoggedIn();
    this.getUsuarios();
  }

  getUsuarios() {
    this.usuariosSrv.getUsuarios(new UsuariosDto()).subscribe(
      response => {
        this.usuarios = response;
      }
    );
  }

  borrarUsuario(id:number){
    this.usuariosSrv.deleteUsuario(id).subscribe();
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
