import { Component, OnInit } from '@angular/core';
import { JuegosService } from '../services/juegos/juegos.service';
import { TagsService } from '../services/tags/tags.service';
import { ActivatedRoute, Router } from '@angular/router';
import { LocationStrategy } from '@angular/common';
import { TagItem } from '../providers/entities/TagItem.entity';
import { environment } from 'src/environments/environment';
import { JuegoByIdDto } from '../providers/dto/dtoById/JuegoByIdDto';
import { JuegoItem } from '../providers/entities/juegoItem.entity';
import { TagsDto } from '../providers/dto/TagsDto';
import { User } from '../providers/model/user.model';
import { UsuariosService } from '../services/usuarios/usuarios.service';
import { ModoItem } from '../providers/entities/ModoItem.entity';
import { ModosService } from '../services/modos/modos.service';
import { ModosDto } from '../providers/dto/ModosDto';
import { AnalisisItem } from '../providers/entities/AnalisisItem.entity';
import { AnalisisService } from '../services/analisis/analisis.service';
import { AnalisisDto } from '../providers/dto/AnalisisDto';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { CrearAnalisisDto } from '../providers/dto/dtoCrear/CrearAnalisisDto';
import { UsuarioItem } from '../providers/entities/UsuarioItem.entity';
import { UsuarioByIdDto } from '../providers/dto/dtoById/UsuarioByIdDto';

@Component({
  selector: 'app-juego',
  templateUrl: './juego.component.html',
  styleUrls: ['./juego.component.css']
})
export class JuegoComponent implements OnInit {

  tags: TagItem[];
  tagsEtiquetas: String[];

  modos: ModoItem[];
  modosLista: String[];

  usuario: UsuarioItem;
  id_usuario: number;

  titulo: String;
  descripcion: String;
  genero: String;
	desarrollador: String;
  fecha_lanzamiento: Date;
  fecha:String;
  analisis_positivos: number;
  analisis_negativos: number;
  id_requisitos: number;
  id_admin_creado: number;
  
  sistema_operativo: String;
  procesador: String;
  memoria: String;
  grafica: String;
  almacenamiento: String;
  
  juego: JuegoItem;
  id_juego: number;
  apiURL: String;

  analisis: AnalisisItem[];
  analisisTexto: String[];

  analisisIngresado: String;
  formAddAnalisis: FormGroup;

  urlImagen: String[];
  hayImagen: boolean;

  user: User;

  constructor(
    private juegosSrv: JuegosService,
    private tagsSrv: TagsService,
    private modosSrv: ModosService,
    private usuariosSrv: UsuariosService,
    private analisisSrv: AnalisisService,
    private route: ActivatedRoute,
    private router: Router,
    private location: LocationStrategy)
  {
    this.tags = [];
    this.tagsEtiquetas = [];

    this.modos = [];
    this.modosLista = [];

    this.analisis = [];
    this.analisisTexto = [];


    this.location.onPopState(() => {
      if (location.back) {
          this.getJuego();
      }
      });
  }

  ngOnInit() {
    this.apiURL = environment.apiEndpoint;
    this.route.params.subscribe(params => {
        this.id_juego = parseInt(params.id_juego, 10);
    });
    this.formAddAnalisis = new FormGroup({
      comentarioIngresado: new FormControl(Validators.required),
    });
    this.getJuego();
    this.user = this.usuariosSrv.getUserLoggedIn();
    if(this.user){
      this.id_usuario = this.user.id_usuario;
      this.getUsuario();
    }
  }

  getJuego(){
    this.juegosSrv.getJuego(new JuegoByIdDto(this.id_juego)).subscribe(
      response => {
        if (response) {
        this.juego = response;
        if (this.juego.archivoImagen != null) {
          this.urlImagen = this.juego.archivoImagen;
          this.hayImagen = true;
        } else {
          this.hayImagen = false;
        }
        this.showDataJuego(this.juego);
        this.getTags();
        this.getAnalisis();
        this.getModos();
      }
    },
    err => {
        console.log(err);
    }
    );
  }

  showDataJuego(juego: JuegoItem) {
    this.titulo = juego.titulo;
    this.genero = juego.genero;
    this.descripcion = juego.descripcion;
    this.desarrollador = juego.desarrollador;
    this.sistema_operativo = juego.sistema_operativo;
    this.procesador = juego.procesador;
    this.memoria = juego.memoria;
    this.grafica = juego.grafica;
    this.almacenamiento = juego.almacenamiento;
    this.analisis_negativos = juego.analisis_negativos;
    this.analisis_positivos = juego.analisis_positivos;
    this.id_admin_creado = juego.id_usuario_juego;
    this.id_requisitos = juego.id_requisitos;
    this.urlImagen = juego.archivoImagen;
    
    this.fecha_lanzamiento = juego.fecha_lanzamiento;
    this.fecha = this.fecha_lanzamiento.toString();
    this.fecha = this.fecha.slice(0, 10);
  }

  
  getTags(){
    this.tagsSrv.getAllTags(new TagsDto()).subscribe(
      response => {
        if (response) {
        this.tags = response;
        this.showDataTags(this.juego);
      }
    },
    err => {
      console.log(err);
    }
    );
  }

  getModos(){
    this.modosSrv.getAllModos(new ModosDto()).subscribe(
      response => {
        if (response) {
        this.modos = response;
        this.showDataModos(this.juego);
      }
    },
    err => {
      console.log(err);
    }
    );
  }

  showDataTags(juego: JuegoItem) {

    let i = 0;
    for (let index = 0; index < this.tags.length; index++) {
      if (this.tags[index].id_tag === juego.tags[i]) {
        i++;
        this.tagsEtiquetas.push(this.tags[index].etiqueta);
      }
    }

  }

  showDataModos(juego: JuegoItem){

    let i = 0;
    for (let index = 0; index < this.modos.length; index++) {
      if (this.modos[index].id_modo === juego.modos[i]) {
        i++;
        this.modosLista.push(this.modos[index].modo);
      }
    }

  }

  volverJuegos(){
    this.router.navigateByUrl(`/juegos`);
  }

  getAnalisis(){
    this.analisisSrv.getAllAnalisis(new AnalisisDto()).subscribe(
      response => {
        if (response) {
        this.analisis = response;
        this.showDataAnalisis(this.juego);
      }
    },
    err => {
      console.log(err);
      if (err === 401) {
      }
    }
    );
  }

  showDataAnalisis(juego: JuegoItem) {

    let i = 0;
    for (let index = 0; index < this.analisis.length; index++) {
      if (this.analisis[index].id_analisis === juego.analisis[i]) {
        i++;
        this.analisisTexto.push(this.analisis[index].analisis);
      }
    }

  }

  agregarAnalisis(){
    if (this.formAddAnalisis.valid) {
      const analisisIn = new CrearAnalisisDto();
      analisisIn.analisis = this.analisisIngresado;
      analisisIn.fecha_publicacion = new Date();
      analisisIn.id_juego = this.juego.id_juego;
      analisisIn.valoracion = this.valoracion;
      analisisIn.id_usuario = this.usuario.id_usuario;
      this.analisisSrv.addAnalisis(analisisIn).subscribe(
        response => {
          this.analisisTexto.push(analisisIn.analisis);
        }
      );
    } else {
      console.log('Formulario invalido');
    }
  }

  getUsuario(){
    this.usuariosSrv.getUsuario(new UsuarioByIdDto(this.id_usuario)).subscribe(
      response => {
        this.usuario = response;
      }
    );
  }

  valoracion = false;

  cambiarValoracion(){
    if (this.valoracion) {
      this.valoracion = false;
    }else{
      this.valoracion = true;
    }
  }
}


