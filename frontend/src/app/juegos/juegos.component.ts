import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { JuegosService } from '../services/juegos/juegos.service';
import { JuegosDto } from '../providers/dto/JuegosDto';
import { JuegoItem } from '../providers/entities/juegoItem.entity';
import { Router } from '@angular/router';
import { TagItem } from '../providers/entities/TagItem.entity';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { TagsService } from '../services/tags/tags.service';
import { TagsDto } from '../providers/dto/TagsDto';
import { CrearJuegoDto } from '../providers/dto/dtoCrear/CrearJuegoDto';
import { User } from '../providers/model/user.model';
import { UsuariosService } from '../services/usuarios/usuarios.service';
import { ModosService } from '../services/modos/modos.service';
import { ModoItem } from '../providers/entities/ModoItem.entity';
import { ModosDto } from '../providers/dto/ModosDto';

@Component({
  selector: 'app-juegos',
  templateUrl: './juegos.component.html',
  styleUrls: ['./juegos.component.css']
})
export class JuegosComponent implements OnInit {

  juegos: JuegoItem[];
  tags: TagItem[];
  tagsId: String;
  modos: ModoItem[];
  modosId: String;

  juego: JuegoItem;
  titulo: String;
  descripcion: String;
  genero: String;
  tipo: String;
  desarrollador: String;
  fecha_lanzamiento: Date;
  fecha: String;
  analisis_positivos: number;
  analisis_negativos: number;
  id_requisitos: number;
	id_admin_creado: number;
  formAddJuego: FormGroup;

  imageFile: number[][];
  imagenesUrl: string[];

  user: User;

  @ViewChild('imageUpload', {static: false}) imagInput: ElementRef;

  constructor(
    private router: Router,
    private juegosSrv: JuegosService,
    private tagsSrv: TagsService,
    private modosSrv: ModosService,
    private usuariosSrv: UsuariosService,
  ) { 
    this.imageFile = [];
    this.imagenesUrl = [];
  }

  ngOnInit() {
    this.getJuegos();
    this.formAddJuego = new FormGroup({
      titulo: new FormControl(Validators.required),
      descripcion: new FormControl(Validators.required),
      genero: new FormControl(Validators.required),
      tipo: new FormControl(Validators.required),
      desarrollador: new FormControl(Validators.required),
      id_requisitos: new FormControl(Validators.required),
      tagsId: new FormControl(Validators.required),
    });
    this.user = this.usuariosSrv.getUserLoggedIn();
  }

  getJuegos() {
    this.juegosSrv.getJuegos(new JuegosDto()).subscribe(
      response => {
        this.juegos = response;
        this.getTags();
        this.getModos();
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
  
  getModos() {
    this.modosSrv.getAllModos(new ModosDto()).subscribe(
      response => {
        this.modos = response;
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

  agregarJuego(){
    if (this.formAddJuego.valid) {
      const juego = new CrearJuegoDto();
      juego.titulo = this.titulo;
      juego.descripcion = this.descripcion;
      juego.genero = this.genero;
      juego.tipo = this.tipo;
      juego.desarrollador = this.desarrollador;
      juego.desarrollador = this.desarrollador;
      juego.fecha_lanzamiento = this.fecha_lanzamiento;
      juego.analisis_negativos = 0;
      juego.analisis_positivos = 0;
      juego.id_admin_creado = 1;
      juego.id_requisitos = 1;
      juego.tags = this.tagsId.split(',').map(Number);
      juego.modos = this.modosId.split(',').map(Number);
      juego.nombreImagen = "hola";
      juego.archivoImagen = this.imageFile;
      this.juegosSrv.addJuego(juego).subscribe();
    } else {
      console.log('Formulario invalido');
    }
  }

  deleteImage(idx: number) {
    this.imageFile.splice(idx,1);
    this.imagenesUrl.splice(idx,1);
  }

  getFecha(juego: JuegoItem){
    this.fecha = juego.fecha_lanzamiento.toString();
    this.fecha = this.fecha.slice(0,10);
    return this.fecha;
  }

  clicked(juego: JuegoItem) {
    this.juego = juego;
    this.router.navigateByUrl(`/juegos/${juego.id_juego}`);
  }

}
