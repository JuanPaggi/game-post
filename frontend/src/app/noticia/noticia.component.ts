import { Component, OnInit } from '@angular/core';
import { NoticiasService } from '../services/noticias/noticias.service';
import { NoticiaItem } from '../providers/entities/NoticiaItem.entity';
import { NoticiaByIdDto } from '../providers/dto/dtoById/noticiaByIdDto';
import { ActivatedRoute, Router } from '@angular/router';
import { environment } from 'src/environments/environment';
import { TagsService } from '../services/tags/tags.service';
import { TagsDto } from '../providers/dto/TagsDto';
import { TagItem } from '../providers/entities/TagItem.entity';
import { LocationStrategy } from '@angular/common';
import { ComentariosService } from '../services/comentarios/comentarios.service';
import { ComentariosDto } from '../providers/dto/ComentariosDto';
import { ComentarioItem } from '../providers/entities/ComentarioItem.entity';
import { Validators, FormControl, FormGroup } from '@angular/forms';
import { CrearComentarioDto } from '../providers/dto/dtoCrear/CrearComentarioDto';
import { UsuarioItem } from '../providers/entities/UsuarioItem.entity';
import { UsuariosService } from '../services/usuarios/usuarios.service';
import { UsuarioByIdDto } from '../providers/dto/dtoById/UsuarioByIdDto';
import { User } from '../providers/model/user.model';
import { AdminService } from '../services/admin/admin.service';

@Component({
  selector: 'app-noticia',
  templateUrl: './noticia.component.html',
  styleUrls: ['./noticia.component.css']
})
export class NoticiaComponent implements OnInit {

  noticia: NoticiaItem;
  usuario: UsuarioItem;
  titulo: string;
  descripcion: string;
  cuerpo: string;
  fecha_publicacion: Date;
  fecha:String;
  apiURL: string;
  id_noticia: number;
  id_usuario: number;
  tags: TagItem[];
  tagsEtiquetas: String[];
  comentarios: ComentarioItem[];
  comentariosTexto: String[];

  urlImagen: String[];
  hayImagen: boolean;

  comentarioIngresado: String;
  formAddComentario: FormGroup;

  user: User;
  userAdm: User;

  constructor(
    private noticiasSrv: NoticiasService,
    private ComentariosSrv: ComentariosService,
    private tagsSrv: TagsService,
    private usuariosSrv: UsuariosService,
    private adminSrv: AdminService,
    private route: ActivatedRoute,
    private router: Router,
    private location: LocationStrategy)
  {
    this.tags = [];
    this.tagsEtiquetas = [];
    this.comentarios = [];
    this.comentariosTexto = [];

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
        this.id_usuario = 1;
    });
    this.getNoticia();
    this.getUsuario();

    this.formAddComentario = new FormGroup({
      comentarioIngresado: new FormControl(Validators.required),
    });
    this.user = this.usuariosSrv.getUserLoggedIn();
    this.userAdm = this.adminSrv.getUserLoggedIn();
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
        this.getComentarios();
      }
    },
    err => {
        console.log(err);
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
    }
    );
  }

  getComentarios(){
    this.ComentariosSrv.getComentarios(new ComentariosDto()).subscribe(
      response => {
        if (response) {
        this.comentarios = response;
        this.showDataComentarios(this.noticia);
      }
    },
    err => {
      console.log(err);
      if (err === 401) {
      }
    }
    );
  }

  agregarComentario(){
    if (this.formAddComentario.valid) {
      const comentarioIn = new CrearComentarioDto();
      comentarioIn.comentario = this.comentarioIngresado;
      comentarioIn.fecha_publicacion = new Date();
      comentarioIn.id_noticia = this.noticia.id_noticia;
      console.log(this.usuario.id_usuario)
      comentarioIn.id_usuario = this.usuario.id_usuario;
      this.ComentariosSrv.addComentario(comentarioIn).subscribe(
        response => {
          this.comentariosTexto.push(comentarioIn.comentario);
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

  showDataNoticia(noticia: NoticiaItem) {
    this.titulo = noticia.titulo;
    this.descripcion = noticia.descripcion;
    this.cuerpo = noticia.cuerpo;
    this.fecha_publicacion = noticia.fecha_publicacion;
    this.urlImagen = noticia.imagenes;
    this.fecha = this.fecha_publicacion.toString();
    this.fecha = this.fecha.slice(0, 10);
  }

  showDataTags(noticia: NoticiaItem) {

    let i = 0;
    for (let index = 0; index < this.tags.length; index++) {
      if (this.tags[index].id_tag === noticia.tags[i]) {
        i++;
        this.tagsEtiquetas.push(this.tags[index].etiqueta);
      }
    }

  }

  showDataComentarios(noticia: NoticiaItem) {

    let i = 0;
    for (let index = 0; index < this.comentarios.length; index++) {
      if (this.comentarios[index].id_comentario === noticia.comentarios[i]) {
        i++;
        this.comentariosTexto.push(this.comentarios[index].comentario);
      }
    }

  }

  borrarNoticia(){
    this.noticiasSrv.deleteNoticia(this.noticia.id_noticia).subscribe();
  }

  volverNoticias(){
    this.router.navigateByUrl(`/noticias`);
  }

}
