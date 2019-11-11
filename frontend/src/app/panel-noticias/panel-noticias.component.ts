import { Component, OnInit, ElementRef, ViewChild } from '@angular/core';
import { CrearNoticiaDto } from '../providers/dto/dtoCrear/CrearNoticiaDto';
import { NoticiaItem } from '../providers/entities/NoticiaItem.entity';
import { TagItem } from '../providers/entities/TagItem.entity';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { User } from '../providers/model/user.model';
import { AdminService } from '../services/admin/admin.service';
import { Router } from '@angular/router';
import { TagsService } from '../services/tags/tags.service';
import { NoticiasService } from '../services/noticias/noticias.service';
import { TagsDto } from '../providers/dto/TagsDto';
import { NoticiasDto } from '../providers/dto/NoticiasDto';

@Component({
  selector: 'app-panel-noticias',
  templateUrl: './panel-noticias.component.html',
  styleUrls: ['./panel-noticias.component.css']
})
export class PanelNoticiasComponent implements OnInit {

  noticias: NoticiaItem[];

  noticia: NoticiaItem;
  tituloNoticia: String;
  descripcionNoticia: String;
  cuerpoNoticia: String;
  fecha_publicacionNoticia: Date;
  fechaNoticia: String;

  tagsNoticia: TagItem[];
  tagsIdNoticia: String;

  formAddNoticia: FormGroup;

  userAdm: User;

  imageFileNoticia: number[][];
  imagenesUrlNoticia: String[];

  @ViewChild('imageUpload', {static: false}) imagInput: ElementRef;

  constructor(
    private adminSrv: AdminService,
    private router: Router,
    private tagsSrv: TagsService,
    private noticiasSrv: NoticiasService,
  ) {
    this.imageFileNoticia = [];
    this.imagenesUrlNoticia = [];
   }

  ngOnInit() {
    this.userAdm = this.adminSrv.getUserLoggedIn();
    this.formAddNoticia = new FormGroup({
      tituloNoticia: new FormControl(Validators.required),
      cuerpoNoticia: new FormControl(Validators.required),
      copeteNoticia: new FormControl(Validators.required),
      epigrafeNoticia: new FormControl(Validators.required),
      autorNoticia: new FormControl(Validators.required),
      tagsIdNoticia: new FormControl(Validators.required),
    });
    this.getTags();
    this.getNoticias();
  }

  getTags() {
    this.tagsSrv.getAllTags(new TagsDto()).subscribe(
      response => {
        this.tagsNoticia = response;
      }
    );
  }

  openImageNoticia() {
    this.imagInput.nativeElement.click();
    this.imagInput.nativeElement.onchange = () => {
      const fr = new FileReader();
      let firstExecution = true;
      fr.onload = () => {
        if(firstExecution) {
          const arrayBuffer = fr.result as ArrayBuffer;
          this.imageFileNoticia.push(Array.from(new Uint8Array(arrayBuffer)));
          firstExecution = false;
          console.log('Imagen cargada');
          fr.readAsDataURL(this.imagInput.nativeElement.files[0]);
        } else {
          this.imagenesUrlNoticia.push(fr.result as string);
          console.log(this.imagenesUrlNoticia);
        }
      
      };
      fr.readAsArrayBuffer(this.imagInput.nativeElement.files[0]);
    };
  }

  agregarNoticia(){
    if (this.formAddNoticia.valid) {
      const noticia = new CrearNoticiaDto();
      noticia.cuerpo = this.cuerpoNoticia;
      noticia.descripcion = this.descripcionNoticia;
      noticia.titulo = this.tituloNoticia;
      noticia.fecha_publicacion = this.fecha_publicacionNoticia;
      noticia.id_admin_creado = 1;
      if (this.tagsIdNoticia != null) {
        noticia.tags = this.tagsIdNoticia.split(',').map(Number);
      }else{
        noticia.tags= [];
      }
      noticia.nombreImagen = "hola";
      noticia.archivoImagen = this.imageFileNoticia;
      this.noticiasSrv.addNoticia(noticia).subscribe();
      window.location.reload();
    } else {
      console.log('Formulario invalido');
    }
  }

  deleteImageNoticia(idx: number) {
    this.imageFileNoticia.splice(idx,1);
    this.imagenesUrlNoticia.splice(idx,1);
  }


  volverPanel(){
    this.router.navigateByUrl(`panel`);
  }

  getNoticias() {
    this.noticiasSrv.getAllNoticias(new NoticiasDto()).subscribe(
      response => {
        this.noticias = response;
      }
    );
  }

  borrarNoticia(id:number){
    this.noticiasSrv.deleteNoticia(id).subscribe();
    for (let index = 0; index < this.noticias.length; index++) {
      if (this.noticias[index].id_noticia === id) {
        this.noticias.splice(index,1);
      }
    }
  }

}
