import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-barra-navegacion',
  templateUrl: './barra-navegacion.component.html',
  styleUrls: ['./barra-navegacion.component.css']
})
export class BarraNavegacionComponent implements OnInit {

  constructor(
    private router:Router,
  ) { }

  ngOnInit() {
  }

  clickedJuegos(){
    this.router.navigateByUrl(`/juegos`);
  }

  clickedNoticias(){
    this.router.navigateByUrl(`/noticias`);
  }

}
