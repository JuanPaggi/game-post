import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { CrearJuegoDto } from '../providers/dto/dtoCrear/CrearJuegoDto';
import { ModosDto } from '../providers/dto/ModosDto';
import { TagsDto } from '../providers/dto/TagsDto';
import { CrearRequisitoDto } from '../providers/dto/dtoCrear/CrearRequisitoDto';
import { FormControl, Validators, FormGroup } from '@angular/forms';
import { RequisitosService } from '../services/requisitos/requisitos.service';
import { TagsService } from '../services/tags/tags.service';
import { JuegosService } from '../services/juegos/juegos.service';
import { ModosService } from '../services/modos/modos.service';
import { Router } from '@angular/router';
import { AdminService } from '../services/admin/admin.service';
import { User } from '../providers/model/user.model';
import { TagItem } from '../providers/entities/TagItem.entity';
import { JuegoItem } from '../providers/entities/juegoItem.entity';
import { ModoItem } from '../providers/entities/ModoItem.entity';

@Component({
  selector: 'app-crear-juego',
  templateUrl: './crear-juego.component.html',
  styleUrls: ['./crear-juego.component.css']
})
export class CrearJuegoComponent implements OnInit {

  sistema_operativo: String;
  procesador: String;
  memoria: String;
  grafica: String;
  almacenamiento: String;

  juego: JuegoItem;
  tituloJuego: String;
  descripcionJuego: String;
  generoJuego: String;
  desarrolladorJuego: String;
  fecha_lanzamientoJuego: Date;
  fechaJuego: String;
  analisis_positivosJuego: number;
  analisis_negativosJuego: number;
  id_requisitosJuego: number;
  id_admin_creadoJuego: number;

  tagsJuego: TagItem[];
  tagsIdJuego: String;
  modosJuego: ModoItem[];
  modosIdJuego: String;

  userAdm: User;

  imageFileJuego: number[][];
  imagenesUrlJuego: string[];

  formAddRequisito: FormGroup;
  formAddJuego: FormGroup;


  @ViewChild('imageUpload', {static: false}) imagInput: ElementRef;

  constructor(
    private adminSrv: AdminService,
    private router: Router,
    private modosSrv: ModosService,
    private juegosSrv: JuegosService,
    private tagsSrv: TagsService,
    private requisitoSrv: RequisitosService,
  ) { 
    this.imageFileJuego = [];
    this.imagenesUrlJuego = [];
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
        this.tagsJuego.reverse();
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
      juego.desarrollador = this.desarrolladorJuego;
      juego.fecha_lanzamiento = this.fecha_lanzamientoJuego;
      juego.analisis_negativos = 0;
      juego.analisis_positivos = 0;
      juego.id_admin_creado = this.userAdm.id_usuario;
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
      if (this.imageFileJuego != null) {
        juego.archivoImagen = this.imageFileJuego;
      }else{
        juego.archivoImagen = [];
      }
      this.juegosSrv.addJuego(juego).subscribe(
        response=>{
          this.router.navigateByUrl(`panel/panel-juegos`);
        }
      );
    } else {
      console.log('Formulario invalido');
    }
  }

  deleteImage(idx: number) {
    this.imageFileJuego.splice(idx,1);
    this.imagenesUrlJuego.splice(idx,1);
  }

  volverPanel(){
    this.router.navigateByUrl(`panel/panel-juegos`);
  }

}
