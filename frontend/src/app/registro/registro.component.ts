import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-registro',
  templateUrl: './registro.component.html',
  styleUrls: ['./registro.component.css']
})
export class RegistroComponent implements OnInit {

  usuario: String;
  clave: String;
  email: String;
  nombre: String;
  apellido: String;
  pais: String;

  constructor() { }

  ngOnInit() {
  }

}
