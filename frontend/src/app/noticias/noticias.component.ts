import { Component, OnInit } from '@angular/core';
import { NoticiasService } from '../services/noticias.service';
import { NoticiaItem } from '../providers/entities/NoticiaItem.entity';
import { NoticiasDto } from '../providers/dto/NoticiasDto';
import { CrearNoticiaDto } from '../providers/dto/CrearNoticiaDto';

@Component({
  selector: 'app-noticias',
  templateUrl: './noticias.component.html',
  styleUrls: ['./noticias.component.css']
})
export class NoticiasComponent implements OnInit {

  noticias: NoticiaItem[];

  constructor(
    private noticiasSrv: NoticiasService
  ) { }

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

  agregarNoticia(){
    const noticia = new CrearNoticiaDto();
    noticia.cuerpo = "cuerpo";
    noticia.titulo = "titulo"
    noticia.descripcion = "descripcion";
    noticia.fecha_publicacion = "2019-08-10T03:00:00.000+0000";
    noticia.id_admin_creado = 1;
    noticia.tags = [1];
    this.noticiasSrv.addNoticia(noticia).subscribe()
  }

}
