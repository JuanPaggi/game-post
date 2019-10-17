import { Component, OnInit } from '@angular/core';
import { NoticiasService } from '../services/noticias.service';
import { NoticiaItem } from '../providers/entities/NoticiaItem.entity';
import { NoticiaByIdDto } from '../providers/dto/noticiaByIdDto';
import { ActivatedRoute } from '@angular/router';
import { environment } from 'src/environments/environment';
import { TagsService } from '../services/tags.service';
import { TagsDto } from '../providers/dto/TagsDto';
import { TagItem } from '../providers/entities/TagItem.entity';
import { LocationStrategy } from '@angular/common';

@Component({
  selector: 'app-noticia',
  templateUrl: './noticia.component.html',
  styleUrls: ['./noticia.component.css']
})
export class NoticiaComponent implements OnInit {

  noticia: NoticiaItem;
  titulo: string;
  descripcion: string;
  cuerpo: string;
  fecha_publicacion: Date;
  apiURL: string;
  id_noticia: number;
  tags: TagItem[];
  tagsEtiquetas: String[];
  comentarios: number[];

  urlImagen: String[];
  hayImagen: boolean;

  constructor(
    private noticiasSrv: NoticiasService,
    private tagsSrv: TagsService,
    private route: ActivatedRoute,
    private location: LocationStrategy)
  {
    this.tags = [];
    this.tagsEtiquetas = [];
    this.comentarios = [];

    this.location.onPopState(() => {
      if (location.back) {
          // window.location.reload();
          this.getNoticia();
      }
      });
  }

  ngOnInit() {
    this.apiURL = environment.apiEndpoint;
    this.route.params.subscribe(params => {
        this.id_noticia = parseInt(params.id_noticia, 10);
    });
    this.getNoticia();
  }

  getNoticia() {
    this.noticiasSrv.getNoticia(new NoticiaByIdDto(this.id_noticia)).subscribe(
      response => {
        if (response) {
        this.noticia = response;
        if (this.noticia.imagenes != null) {
          this.urlImagen = this.noticia.imagenes;
          this.hayImagen = true;
        } else {
          this.hayImagen = false;
        }
        this.showDataNoticia(this.noticia);
        this.getTags();
      }
    },
    err => {
        console.log(err);
        if (err === 401) {
        }
    }
    );
  }

  getTags(){
    this.tagsSrv.getAllTags(new TagsDto()).subscribe(
      response => {
        if (response) {
        //setTimeout( () => {
        this.tags = response;
        this.showDataTags(this.noticia);
        //},500);
      }
    },
    err => {
      console.log(err);
      if (err === 401) {
      }
    }
    );
  }

  showDataNoticia(noticia: NoticiaItem) {
    this.titulo = noticia.titulo;
    this.descripcion = noticia.descripcion;
    this.cuerpo = noticia.cuerpo;
    this.fecha_publicacion = noticia.fecha_publicacion;
    this.comentarios = noticia.comentarios;
    this.urlImagen = noticia.imagenes;
  }

  showDataTags(noticia: NoticiaItem) {

    for (let index = 0; index < this.tags.length; index++) {
      if (this.tags[index].id_tag == noticia.tags[index]) {
        this.tagsEtiquetas.push(this.tags[index].etiqueta);
      }
    }

  }

  borrarNoticia(){
    this.noticiasSrv.deleteNoticia(this.noticia.id_noticia).subscribe();
  }

}
