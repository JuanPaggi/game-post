import { Component, OnInit } from '@angular/core';
import { JuegosService } from '../services/juegos/juegos.service';
import { JuegosDto } from '../providers/dto/JuegosDto';
import { JuegoItem } from '../providers/entities/juegoItem.entity';

@Component({
  selector: 'app-juegos',
  templateUrl: './juegos.component.html',
  styleUrls: ['./juegos.component.css']
})
export class JuegosComponent implements OnInit {

  juegos: JuegoItem[];

  constructor(
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

}
