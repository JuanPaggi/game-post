import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { NoticiaItem } from '../providers/entities/NoticiaItem.entity';
import { TagItem } from '../providers/entities/TagItem.entity';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { User } from '../providers/model/user.model';
import { AdminService } from '../services/admin/admin.service';
import { Router } from '@angular/router';
import { TagsService } from '../services/tags/tags.service';
import { NoticiasService } from '../services/noticias/noticias.service';
import { TagsDto } from '../providers/dto/TagsDto';
import { CrearNoticiaDto } from '../providers/dto/dtoCrear/CrearNoticiaDto';

@Component({
  selector: 'app-crear-noticia',
  templateUrl: './crear-noticia.component.html',
  styleUrls: ['./crear-noticia.component.css']
})
export class CrearNoticiaComponent implements OnInit {

  @ViewChild('imageUpload', {static: false}) imagInput: ElementRef;

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
      titulo: new FormControl(Validators.required),
      descripcion: new FormControl(Validators.required),
      cuerpo: new FormControl(Validators.required),
      tagsIdNoticia: new FormControl(Validators.required),
    });
    this.getTags();
  }



  getTags() {
    this.tagsSrv.getAllTags(new TagsDto()).subscribe(
      response => {
        this.tagsNoticia = response;
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
      noticia.fecha_publicacion = new Date();
      noticia.id_admin_creado = 1;
      if (this.tagsIdNoticia != null) {
        noticia.tags = this.tagsIdNoticia.split(',').map(Number);
      }else{
        noticia.tags= [];
      }
      noticia.nombreImagen = "image";
      if (this.imageFileNoticia != null) {
        noticia.archivoImagen = this.imageFileNoticia;
      }else{
        noticia.archivoImagen = [];
      }
      noticia.archivoImagen = this.imageFileNoticia;
      this.noticiasSrv.addNoticia(noticia).subscribe(
        response=>{
          this.router.navigateByUrl(`panel/panel-noticias`);
        }
      );
    } else {
      console.log('Formulario invalido');
    }
  }

  deleteImage(idx: number) {
    this.imageFileNoticia.splice(idx,1);
    this.imagenesUrlNoticia.splice(idx,1);
  }

  volverPanel(){
    this.router.navigateByUrl(`panel/panel-noticias`);
  }

}
