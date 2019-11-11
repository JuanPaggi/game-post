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
import { AdminService } from '../services/admin/admin.service';

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

  titulo: String;
  descripcion: String;
  genero: String;
	tipo: String;
	desarrollador: String;
  fecha_lanzamiento: Date;
  fecha:String;
  analisis_positivos: number;
  analisis_negativos: number;
  id_requisitos: number;
	id_admin_creado: number;
  
  juego: JuegoItem;
  id_juego: number;
  apiURL: String;

  urlImagen: String[];
  hayImagen: boolean;

  user: User;
  userAdm: User;

  constructor(
    private juegosSrv: JuegosService,
    private tagsSrv: TagsService,
    private modosSrv: ModosService,
    private route: ActivatedRoute,
    private router: Router,
    private adminSrv: AdminService,
    private location: LocationStrategy)
  {
    this.tags = [];
    this.tagsEtiquetas = [];

    this.modos = [];
    this.modosLista = [];


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
    this.getJuego();
    this.userAdm = this.adminSrv.getUserLoggedIn();
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
    this.tipo = juego.tipo;
    this.analisis_negativos = juego.analisis_negativos;
    this.analisis_positivos = juego.analisis_positivos;
    this.id_admin_creado = juego.id_admin_creado;
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
}
