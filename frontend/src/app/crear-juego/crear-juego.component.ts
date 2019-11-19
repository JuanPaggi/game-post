import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { CrearJuegoDto } from '../providers/dto/dtoCrear/CrearJuegoDto';
import { ModosDto } from '../providers/dto/ModosDto';
import { TagsDto } from '../providers/dto/TagsDto';
import { FormControl, Validators, FormGroup } from '@angular/forms';
import { TagsService } from '../services/tags/tags.service';
import { JuegosService } from '../services/juegos/juegos.service';
import { ModosService } from '../services/modos/modos.service';
import { Router } from '@angular/router';
import { User } from '../providers/model/user.model';
import { TagItem } from '../providers/entities/TagItem.entity';
import { JuegoItem } from '../providers/entities/juegoItem.entity';
import { ModoItem } from '../providers/entities/ModoItem.entity';
import { UsuariosService } from '../services/usuarios/usuarios.service';

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
  id_admin_creadoJuego: number;

  tagsJuego: TagItem[];
  tagsIdJuego: String;
  modosJuego: ModoItem[];
  modosIdJuego: String;

  user: User;
  privilegio_agregar_juego = false;

  imageFileJuego: number[][];
  imagenesUrlJuego: string[];

  formAddJuego: FormGroup;


  @ViewChild('imageUpload', {static: false}) imagInput: ElementRef;

  constructor(
    private usuariosSrv: UsuariosService,
    private router: Router,
    private modosSrv: ModosService,
    private juegosSrv: JuegosService,
    private tagsSrv: TagsService,
  ) { 


    this.imageFileJuego = [];
    this.imagenesUrlJuego = [];
  }

  ngOnInit() {
    this.user = this.usuariosSrv.getUserLoggedIn();
    if(this.user){
      this.user.privilegios.forEach(element => {
        if(element === 1){ // 1 = "Agregar Juego"
          this.privilegio_agregar_juego = true;
        }
      });
    }
    this.formAddJuego = new FormGroup({
      tituloJuego: new FormControl(Validators.required),
      descripcionJuego: new FormControl(Validators.required),
      generoJuego: new FormControl(Validators.required),
      tipoJuego: new FormControl(Validators.required),
      desarrolladorJuego: new FormControl(Validators.required),
      tagsIdJuego: new FormControl(Validators.required),
    });
    this.getTags();
    this.getModos();
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
      juego.sistema_operativo = this.sistema_operativo;
      juego.procesador = this.procesador;
      juego.memoria = this.memoria;
      juego.grafica = this.grafica;
      juego.almacenamiento = this.almacenamiento;
      juego.analisis_negativos = 0;
      juego.analisis_positivos = 0;
      juego.id_usuario_juego = this.user.id_usuario;
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
          if(response === 0){
            console.log("No se tiene acceso, no se añadio el juego");
          }else{
            this.router.navigateByUrl(`panel/panel-juegos`);
          }
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
