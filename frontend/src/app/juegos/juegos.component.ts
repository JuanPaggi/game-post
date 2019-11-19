import { Component, OnInit } from '@angular/core';
import { JuegosService } from '../services/juegos/juegos.service';
import { JuegosDto } from '../providers/dto/JuegosDto';
import { JuegoItem } from '../providers/entities/juegoItem.entity';
import { Router } from '@angular/router';

@Component({
  selector: 'app-juegos',
  templateUrl: './juegos.component.html',
  styleUrls: ['./juegos.component.css']
})
export class JuegosComponent implements OnInit {

  juegos: JuegoItem[];


  juego: JuegoItem;
  titulo: String;
  descripcion: String;
  fecha_lanzamiento: Date;
  fecha: String;

  constructor(
    private router: Router,
    private juegosSrv: JuegosService,
  ) {}

  ngOnInit() {
    this.getJuegos();
  }

  getJuegos() {
    this.juegosSrv.getJuegos(new JuegosDto()).subscribe(
      response => {
        this.juegos = response;
      }
    );
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

  volverHome(){
    this.router.navigateByUrl(`/`);
  }

}
