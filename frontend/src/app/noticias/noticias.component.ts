import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { NoticiasService } from '../services/noticias/noticias.service';
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

  imageFile: number[][];
  imagenesUrl: string[];

  @ViewChild('imageUpload', {static: false}) imagInput: ElementRef;


  constructor(
    private noticiasSrv: NoticiasService
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
    });
  }

  getNoticias() {
    this.noticiasSrv.getAllNoticias(new NoticiasDto()).subscribe(
      response => {
        this.noticias = response;
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
      noticia.tags = [1];
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

}
