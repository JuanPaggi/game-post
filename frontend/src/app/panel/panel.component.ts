import { Component, OnInit } from '@angular/core';
import { CrearRequisitoDto } from '../providers/dto/dtoCrear/CrearRequisitoDto';
import { FormControl, Validators, FormGroup } from '@angular/forms';
import { RequisitosService } from '../services/requisitos/requisitos.service';
import { Router } from '@angular/router';
import { UsuariosService } from '../services/usuarios/usuarios.service';
import { User } from '../providers/model/user.model';

@Component({
  selector: 'app-panel',
  templateUrl: './panel.component.html',
  styleUrls: ['./panel.component.css']
})
export class PanelComponent implements OnInit {
  
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
