import { Component, OnInit} from '@angular/core';
import { User } from '../providers/model/user.model';
import { JuegoItem } from '../providers/entities/juegoItem.entity';
import { JuegosService } from '../services/juegos/juegos.service';
import { Router } from '@angular/router';
import { JuegosDto } from '../providers/dto/JuegosDto';
import { UsuariosService } from '../services/usuarios/usuarios.service';

@Component({
  selector: 'app-panel-juegos',
  templateUrl: './panel-juegos.component.html',
  styleUrls: ['./panel-juegos.component.css']
})
export class PanelJuegosComponent implements OnInit {
  
  juegos: JuegoItem[];

  user: User;

  constructor(
    private usuarioSrv: UsuariosService,
    private router: Router,
    private juegosSrv: JuegosService,
  ) { }

  ngOnInit() {
    this.user = this.usuarioSrv.getUserLoggedIn();
    this.getJuegos();
  }

  volverPanel(){
    this.router.navigateByUrl(`panel`);
  }

  getJuegos() {
    this.juegosSrv.getJuegos(new JuegosDto()).subscribe(
      response => {
        this.juegos = response;
      }
    );
  }

  borrarJuego(id:number){
    this.juegosSrv.deleteJuego(id).subscribe();
    for (let index = 0; index < this.juegos.length; index++) {
      if (this.juegos[index].id_juego === id) {
        this.juegos.splice(index,1);
      }
    }
  }

  agregarJuego(){
    this.router.navigateByUrl(`panel/panel-juegos/crear-juego`);
  }

}
