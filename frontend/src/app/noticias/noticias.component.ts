import { Component, OnInit } from '@angular/core';
import { NoticiasService } from '../services/noticias.service';
import { NoticiaItem } from '../providers/entities/NoticiaItem.entity';
import { NoticiasDto } from '../providers/dto/NoticiasDto';
import { CrearNoticiaDto } from '../providers/dto/CrearNoticiaDto';
import { FormGroup, FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-noticias',
  templateUrl: './noticias.component.html',
  styleUrls: ['./noticias.component.css']
})
export class NoticiasComponent implements OnInit {

  noticias: NoticiaItem[];

  noticia = NoticiaItem;
  titulo: string;
  descripcion: string;
  cuerpo: string;
  fecha_publicacion: string;
  formAddNoticia: FormGroup;

  constructor(
    private noticiasSrv: NoticiasService
  ) { }

  ngOnInit() {
    this.getNoticias();
    this.formAddNoticia = new FormGroup({
      titulo: new FormControl(Validators.required),
      cuerpo: new FormControl(Validators.required),
      copete: new FormControl(Validators.required),
      epigrafe: new FormControl(Validators.required),
      autor: new FormControl(Validators.required),
    });
  }

  getNoticias() {
    this.noticiasSrv.getAllNoticias(new NoticiasDto()).subscribe(
      response => {
        this.noticias = response;
      }
    );
  }

  agregarNoticia(){
    if (this.formAddNoticia.valid) {
      const noticia = new CrearNoticiaDto();
      noticia.cuerpo = this.cuerpo;
      noticia.descripcion = this.descripcion;
      noticia.titulo = this.titulo;
      noticia.fecha_publicacion = this.fecha_publicacion;
      noticia.id_admin_creado = 1;
      noticia.tags = [1];
      this.noticiasSrv.addNoticia(noticia).subscribe();
    } else {
      console.log('Formulario invalido');
    }
  }

}
