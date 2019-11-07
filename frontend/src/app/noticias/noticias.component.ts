import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { NoticiasService } from '../services/noticias/noticias.service';
import { NoticiaItem } from '../providers/entities/NoticiaItem.entity';
import { NoticiasDto } from '../providers/dto/NoticiasDto';
import { CrearNoticiaDto } from '../providers/dto/dtoCrear/CrearNoticiaDto';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { TagsService } from '../services/tags/tags.service';
import { TagItem } from '../providers/entities/TagItem.entity';
import { TagsDto } from '../providers/dto/TagsDto';
import { Router } from '@angular/router';
import { User } from '../providers/model/user.model';
import { UsuariosService } from '../services/usuarios/usuarios.service';

@Component({
  selector: 'app-noticias',
  templateUrl: './noticias.component.html',
  styleUrls: ['./noticias.component.css']
})
export class NoticiasComponent implements OnInit {

  noticias: NoticiaItem[];
  tags: TagItem[];
  tagsId: String;

  noticia: NoticiaItem;
  titulo: string;
  descripcion: string;
  cuerpo: string;
  fecha_publicacion: Date;
  fecha: String;
  formAddNoticia: FormGroup;

  imageFile: number[][];
  imagenesUrl: string[];

  user: User;

  @ViewChild('imageUpload', {static: false}) imagInput: ElementRef;


  constructor(
    private noticiasSrv: NoticiasService,
    private router: Router,
    private tagsSrv: TagsService,
    private usuariosSrv: UsuariosService,
  ) { 
    this.imageFile = [];
    this.imagenesUrl = [];
  }

  ngOnInit() {
    this.getNoticias();
    this.formAddNoticia = new FormGroup({
      titulo: new FormControl(Validators.required),
      cuerpo: new FormControl(Validators.required),
      copete: new FormControl(Validators.required),
      epigrafe: new FormControl(Validators.required),
      autor: new FormControl(Validators.required),
      tagsId: new FormControl(Validators.required),
    });
    this.user = this.usuariosSrv.getUserLoggedIn();
  }

  getNoticias() {
    this.noticiasSrv.getAllNoticias(new NoticiasDto()).subscribe(
      response => {
        this.noticias = response;
        this.getTags();
      }
    );
  }

  getTags() {
    this.tagsSrv.getAllTags(new TagsDto()).subscribe(
      response => {
        this.tags = response;
      }
    );
  }

  openImage() {
    this.imagInput.nativeElement.click();
    this.imagInput.nativeElement.onchange = () => {
      const fr = new FileReader();
      let firstExecution = true;
      fr.onload = () => {
        if(firstExecution) {
          const arrayBuffer = fr.result as ArrayBuffer;
          this.imageFile.push(Array.from(new Uint8Array(arrayBuffer)));
          firstExecution = false;
          console.log('Imagen cargada');
          fr.readAsDataURL(this.imagInput.nativeElement.files[0]);
        } else {
          this.imagenesUrl.push(fr.result as string);
          console.log(this.imagenesUrl);
        }
      
      };
      fr.readAsArrayBuffer(this.imagInput.nativeElement.files[0]);
    };
  }

  agregarNoticia(){
    if (this.formAddNoticia.valid) {
      const noticia = new CrearNoticiaDto();
      noticia.cuerpo = this.cuerpo;
      noticia.descripcion = this.descripcion;
      noticia.titulo = this.titulo;
      noticia.fecha_publicacion = this.fecha_publicacion;
      noticia.id_admin_creado = 1;
      if (this.tagsId != null) {
        noticia.tags = this.tagsId.split(',').map(Number);
      }else{
        noticia.tags= [];
      }
      noticia.nombreImagen = "hola";
      noticia.archivoImagen = this.imageFile;
      this.noticiasSrv.addNoticia(noticia).subscribe();
    } else {
      console.log('Formulario invalido');
    }
  }

  deleteImage(idx: number) {
    this.imageFile.splice(idx,1);
    this.imagenesUrl.splice(idx,1);
  }

  clicked(noticia: NoticiaItem) {
    this.noticia = noticia;
    this.router.navigateByUrl(`/noticias/${noticia.id_noticia}`);
  }

  getFecha(noticia: NoticiaItem){
    this.fecha = noticia.fecha_publicacion.toString();
    this.fecha = this.fecha.slice(0,10);
    return this.fecha;
  }

}
