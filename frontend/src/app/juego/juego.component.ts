import { Component, OnInit } from '@angular/core';
import { JuegosService } from '../services/juegos/juegos.service';
import { TagsService } from '../services/tags/tags.service';
import { ActivatedRoute } from '@angular/router';
import { LocationStrategy } from '@angular/common';
import { TagItem } from '../providers/entities/TagItem.entity';
import { environment } from 'src/environments/environment';
import { JuegoByIdDto } from '../providers/dto/JuegoByIdDto';
import { JuegoItem } from '../providers/entities/juegoItem.entity';
import { TagsDto } from '../providers/dto/TagsDto';

@Component({
  selector: 'app-juego',
  templateUrl: './juego.component.html',
  styleUrls: ['./juego.component.css']
})
export class JuegoComponent implements OnInit {

  tags: TagItem[];
  tagsEtiquetas: String[];

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

  constructor(
    private juegosSrv: JuegosService,
    private tagsSrv: TagsService,
    private route: ActivatedRoute,
    private location: LocationStrategy)
  {
    this.tags = [];
    this.tagsEtiquetas = [];

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

  showDataTags(juego: JuegoItem) {

    let i = 0;
    for (let index = 0; index < this.tags.length; index++) {
      if (this.tags[index].id_tag === juego.tags[i]) {
        i++;
        this.tagsEtiquetas.push(this.tags[index].etiqueta);
      }
    }

  }

  borrarJuego(){
    this.juegosSrv.deleteJuego(this.juego.id_juego).subscribe();
  }
}
