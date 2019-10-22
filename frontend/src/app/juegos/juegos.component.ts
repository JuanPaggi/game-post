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

  constructor(
    private router: Router,
    private juegosSrv: JuegosService
  ) { }

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

  clicked(juego: JuegoItem) {
    this.juego = juego;
    this.router.navigateByUrl(`/juegos/${juego.id_juego}`);
  }

}
