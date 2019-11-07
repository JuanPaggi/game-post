import { Component, OnInit } from '@angular/core';
import { User } from '../providers/model/user.model';
import { UsuariosService } from '../services/usuarios/usuarios.service';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { CrearRequisitoDto } from '../providers/dto/dtoCrear/CrearRequisitoDto';
import { RequisitosService } from '../services/requisitos/requisitos.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-requisitos',
  templateUrl: './requisitos.component.html',
  styleUrls: ['./requisitos.component.css']
})
export class RequisitosComponent implements OnInit {

  sistema_operativo: String;
  procesador: String;
  memoria: String;
  grafica: String;
  almacenamiento: String;

  formAddRequisito: FormGroup;

  user: User;

  constructor(
    private usuariosSrv: UsuariosService,
    private router: Router,
    private requisitoSrv: RequisitosService,
  ) { }

  ngOnInit() {
    this.user = this.usuariosSrv.getUserLoggedIn();
    this.formAddRequisito = new FormGroup({
      sistema_operativo: new FormControl(Validators.required),
      procesador: new FormControl(Validators.required),
      memoria: new FormControl(Validators.required),
      grafica: new FormControl(Validators.required),
      almacenamiento: new FormControl(Validators.required),
    });
  }

  agregarRequisito(){
    if (this.formAddRequisito.valid) {
      const requisito = new CrearRequisitoDto();
      requisito.sistema_operativo = this.sistema_operativo;
      requisito.procesador = this.procesador;
      requisito.memoria = this.memoria;
      requisito.grafica = this.grafica;
      requisito.almacenamiento = this.almacenamiento;
      this.requisitoSrv.addRequisito(requisito).subscribe();
    } else {
      console.log('Formulario invalido');
    }
  }

  volverHome(){
    this.router.navigateByUrl(`/`);
  }

}
