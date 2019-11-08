import { Component, OnInit, ElementRef, ViewChild } from '@angular/core';
import { CrearRequisitoDto } from '../providers/dto/dtoCrear/CrearRequisitoDto';
import { FormControl, Validators, FormGroup } from '@angular/forms';
import { RequisitosService } from '../services/requisitos/requisitos.service';
import { Router } from '@angular/router';
import { User } from '../providers/model/user.model';
import { AdminService } from '../services/admin/admin.service';
import { TagItem } from '../providers/entities/TagItem.entity';
import { ModoItem } from '../providers/entities/ModoItem.entity';
import { TagsService } from '../services/tags/tags.service';
import { ModosService } from '../services/modos/modos.service';
import { TagsDto } from '../providers/dto/TagsDto';
import { ModosDto } from '../providers/dto/ModosDto';
import { CrearJuegoDto } from '../providers/dto/dtoCrear/CrearJuegoDto';
import { JuegoItem } from '../providers/entities/juegoItem.entity';
import { JuegosService } from '../services/juegos/juegos.service';
import { NoticiaItem } from '../providers/entities/NoticiaItem.entity';
import { NoticiasService } from '../services/noticias/noticias.service';
import { CrearNoticiaDto } from '../providers/dto/dtoCrear/CrearNoticiaDto';
import { NoticiasDto } from '../providers/dto/NoticiasDto';

@Component({
  selector: 'app-panel',
  templateUrl: './panel.component.html',
  styleUrls: ['./panel.component.css']
})
export class PanelComponent implements OnInit {
  
  sistema_operativo: String;
  procesador: String;
  memoria: String;
  grafica: String;
  almacenamiento: String;

  juego: JuegoItem;
  tituloJuego: String;
  descripcionJuego: String;
  generoJuego: String;
  tipoJuego: String;
  desarrolladorJuego: String;
  fecha_lanzamientoJuego: Date;
  fechaJuego: String;
  analisis_positivosJuego: number;
  analisis_negativosJuego: number;
  id_requisitosJuego: number;
  id_admin_creadoJuego: number;
  
  noticia: NoticiaItem;
  tituloNoticia: String;
  descripcionNoticia: String;
  cuerpoNoticia: String;
  fecha_publicacionNoticia: Date;
  fechaNoticia: String;

  tagsJuego: TagItem[];
  tagsIdJuego: String;
  tagsNoticia: TagItem[];
  tagsIdNoticia: String;
  modosJuego: ModoItem[];
  modosIdJuego: String;

  formAddRequisito: FormGroup;
  formAddJuego: FormGroup;
  formAddNoticia: FormGroup;

  userAdm: User;

  imageFileJuego: number[][];
  imagenesUrlJuego: string[];
  imageFileNoticia: number[][];
  imagenesUrlNoticia: String[];

  @ViewChild('imageUpload', {static: false}) imagInput: ElementRef;

  constructor(
    private adminSrv: AdminService,
    private router: Router,
    private tagsSrv: TagsService,
    private modosSrv: ModosService,
    private juegosSrv: JuegosService,
    private requisitoSrv: RequisitosService,
    private noticiasSrv: NoticiasService,
  ) {
    this.imageFileJuego = [];
    this.imagenesUrlJuego = [];
    this.imageFileNoticia = [];
    this.imagenesUrlNoticia = [];
   }

  ngOnInit() {
    this.userAdm = this.adminSrv.getUserLoggedIn();
    this.formAddRequisito = new FormGroup({
      sistema_operativo: new FormControl(Validators.required),
      procesador: new FormControl(Validators.required),
      memoria: new FormControl(Validators.required),
      grafica: new FormControl(Validators.required),
      almacenamiento: new FormControl(Validators.required),
    });
    this.formAddJuego = new FormGroup({
      tituloJuego: new FormControl(Validators.required),
      descripcionJuego: new FormControl(Validators.required),
      generoJuego: new FormControl(Validators.required),
      tipoJuego: new FormControl(Validators.required),
      desarrolladorJuego: new FormControl(Validators.required),
      id_requisitosJuego: new FormControl(Validators.required),
      tagsIdJuego: new FormControl(Validators.required),
    });
    this.formAddNoticia = new FormGroup({
      tituloNoticia: new FormControl(Validators.required),
      cuerpoNoticia: new FormControl(Validators.required),
      copeteNoticia: new FormControl(Validators.required),
      epigrafeNoticia: new FormControl(Validators.required),
      autorNoticia: new FormControl(Validators.required),
      tagsIdNoticia: new FormControl(Validators.required),
    });
    this.getTags();
    this.getModos();
  }

  agregarRequisito(){
    if (this.formAddRequisito.valid) {
      const requisito = new CrearRequisitoDto();
      requisito.sistema_operativo = this.sistema_operativo;
      requisito.procesador = this.procesador;
      requisito.memoria = this.memoria;
      requisito.grafica = this.grafica;
      requisito.almacenamiento = this.almacenamiento;
      this.requisitoSrv.addRequisito(requisito).subscribe();
    } else {
      console.log('Formulario invalido');
    }
  }

  getTags() {
    this.tagsSrv.getAllTags(new TagsDto()).subscribe(
      response => {
        this.tagsJuego = response;
        this.tagsNoticia = response;
      }
    );
  }
  
  getModos() {
    this.modosSrv.getAllModos(new ModosDto()).subscribe(
      response => {
        this.modosJuego = response;
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
          this.imageFileJuego.push(Array.from(new Uint8Array(arrayBuffer)));
          firstExecution = false;
          console.log('Imagen cargada');
          fr.readAsDataURL(this.imagInput.nativeElement.files[0]);
        } else {
          this.imagenesUrlJuego.push(fr.result as string);
          console.log(this.imagenesUrlJuego);
        }
      
      };
      fr.readAsArrayBuffer(this.imagInput.nativeElement.files[0]);
    };
  }

  agregarJuego(){
    if (this.formAddJuego.valid) {
      const juego = new CrearJuegoDto();
      juego.titulo = this.tituloJuego;
      juego.descripcion = this.descripcionJuego;
      juego.genero = this.generoJuego;
      juego.tipo = this.tipoJuego;
      juego.desarrollador = this.desarrolladorJuego;
      juego.fecha_lanzamiento = this.fecha_lanzamientoJuego;
      juego.analisis_negativos = 0;
      juego.analisis_positivos = 0;
      juego.id_admin_creado = 1;
      juego.id_requisitos = 1;
      if (this.tagsIdJuego != null) {
        juego.tags = this.tagsIdJuego.split(',').map(Number);
      }else{
        juego.tags= [];
      }
      if (this.modosIdJuego != null) {
        juego.modos = this.modosIdJuego.split(',').map(Number);
      }else{
        juego.modos= [];
      }
      juego.nombreImagen = "image";
      juego.archivoImagen = this.imageFileJuego;
      this.juegosSrv.addJuego(juego).subscribe();
    } else {
      console.log('Formulario invalido');
    }
  }

  deleteImage(idx: number) {
    this.imageFileJuego.splice(idx,1);
    this.imagenesUrlJuego.splice(idx,1);
  }

  volverHome(){
    this.router.navigateByUrl(`/`);
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
    } else {
      console.log('Formulario invalido');
    }
  }

  deleteImageNoticia(idx: number) {
    this.imageFileNoticia.splice(idx,1);
    this.imagenesUrlNoticia.splice(idx,1);
  }

}
