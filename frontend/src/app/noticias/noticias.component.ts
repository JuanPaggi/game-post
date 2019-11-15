import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { NoticiasService } from '../services/noticias/noticias.service';
import { NoticiaItem } from '../providers/entities/NoticiaItem.entity';
import { NoticiasDto } from '../providers/dto/NoticiasDto';
import { FormGroup } from '@angular/forms';
import { TagItem } from '../providers/entities/TagItem.entity';
import { Router } from '@angular/router';

@Component({
  selector: 'app-noticias',
  templateUrl: './noticias.component.html',
  styleUrls: ['./noticias.component.css']
})
export class NoticiasComponent implements OnInit {

  noticias: NoticiaItem[];
  tagsNoticia: TagItem[];
  tagsIdNoticia: String;

  noticia: NoticiaItem;
  tituloNoticia: String;
  descripcionNoticia: String;
  cuerpoNoticia: String;
  fecha_publicacionNoticia: Date;
  fechaNoticia: String;
  formAddNoticia: FormGroup;

  imageFileNoticia: number[][];
  imagenesUrlNoticia: String[];

  @ViewChild('imageUpload', {static: false}) imagInput: ElementRef;

  constructor(
    private noticiasSrv: NoticiasService,
    private router: Router,
  ) {}

  ngOnInit() {
    this.getNoticias();
  }

  getNoticias() {
    this.noticiasSrv.getAllNoticias(new NoticiasDto()).subscribe(
      response => {
        this.noticias = response;
      }
    );
  }

  clicked(noticia: NoticiaItem) {
    this.noticia = noticia;
    this.router.navigateByUrl(`/noticias/${noticia.id_noticia}`);
  }

  getFecha(noticia: NoticiaItem){
    this.fechaNoticia = noticia.fecha_publicacion.toString();
    this.fechaNoticia = this.fechaNoticia.slice(0,10);
    return this.fechaNoticia;
  }

  volverHome(){
    this.router.navigateByUrl(`/`);
  }

}
