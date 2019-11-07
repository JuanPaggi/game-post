import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { CrearUsuarioDto } from '../providers/dto/dtoCrear/CrearUsuarioDto';
import { UsuariosService } from '../services/usuarios/usuarios.service';
import { Router } from '@angular/router';
import { User } from '../providers/model/user.model';

@Component({
  selector: 'app-registro',
  templateUrl: './registro.component.html',
  styleUrls: ['./registro.component.css']
})
export class RegistroComponent implements OnInit {

  usuario: String;
  clave: String;
  email: String;
  nombre: String;
  apellido: String;
  pais: String;
  formAddNoticia: FormGroup;

  htmlToAdd: String;

  user: User;

  constructor(
    private router: Router,
    private usuariosSrv: UsuariosService,
  ) { }

  ngOnInit() {
    this.formAddNoticia = new FormGroup({
      usuario: new FormControl(Validators.required),
      clave: new FormControl(Validators.required),
      email: new FormControl(Validators.required),
      nombre: new FormControl(Validators.required),
      pais: new FormControl(Validators.required),
    });
    this.user = this.usuariosSrv.getUserLoggedIn();
  }

  agregarUsuario(){
    if (this.formAddNoticia.valid) {
      const usuario = new CrearUsuarioDto();
      usuario.usuario = this.usuario;
      usuario.clave = this.clave;
      usuario.email = this.email;
      usuario.nombre = this.nombre;
      usuario.apellido = this.apellido;
      usuario.pais = this.pais;
      this.usuariosSrv.addUsuario(usuario).subscribe(
        response => {
          this.router.navigateByUrl(`/login`);
        }, err => {
          if(err.status === 400){
            this.htmlToAdd = '<p class="list-group-item">Datos Incorrectos<p>';
          }
        }
      )
    } else {
      console.log('Formulario invalido');
    }
  }

}
