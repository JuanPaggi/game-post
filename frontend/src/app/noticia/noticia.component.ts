import { Component, OnInit } from '@angular/core';
import { NoticiasService } from '../services/noticias.service';
import { NoticiaItem } from '../providers/entities/NoticiaItem.entity';
import { NoticiaByIdDto } from '../providers/dto/noticiaByIdDto';
import { ActivatedRoute } from '@angular/router';
import { environment } from 'src/environments/environment';
import { TagsService } from '../services/tags.service';
import { TagsDto } from '../providers/dto/TagsDto';
import { TagItem } from '../providers/entities/TagItem.entity';

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
  tags: TagItem[] = [];
  tagsEtiquetas: String[] = [];
  comentarios: number[]= [];

  constructor(
    private noticiasSrv: NoticiasService,
    private tagsSrv: TagsService,
    private route: ActivatedRoute)
  {}

  ngOnInit() {
    this.apiURL = environment.apiEndpoint;
    this.route.params.subscribe(params => {
        this.id_noticia = parseInt(params.id_noticia, 10);
    });
    this.getNoticia();
    this.getTags();
    console.log(this.tagsEtiquetas);
  }

  getNoticia() {
    this.noticiasSrv.getNoticia(new NoticiaByIdDto(this.id_noticia)).subscribe(
      response => {
        if (response) {
        this.noticia = response;
        this.showDataNoticia(this.noticia);
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
        this.tags = response;
        this.showDataTags(this.noticia);
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
