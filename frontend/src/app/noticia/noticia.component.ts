import { Component, OnInit } from '@angular/core';
import { NoticiasService } from '../services/noticias.service';
import { NoticiaItem } from '../providers/entities/NoticiaItem.entity';
import { NoticiaByIdDto } from '../providers/dto/noticiaByIdDTO';
import { ActivatedRoute } from '@angular/router';
import { LocationStrategy } from '@angular/common';
import { environment } from 'src/environments/environment';

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

  constructor(private noticiasSrv: NoticiasService,
    private route: ActivatedRoute,
    private location: LocationStrategy) {
      this.location.onPopState(() => {
        if (location.back) {
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

  showDataNoticia(noticia: NoticiaItem) {
    this.titulo = noticia.titulo;
    this.descripcion = noticia.descripcion;
    this.cuerpo = noticia.cuerpo;
    this.fecha_publicacion = noticia.fecha_publicacion;
  }

}
