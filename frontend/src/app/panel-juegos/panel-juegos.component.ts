import { Component, OnInit} from '@angular/core';
import { User } from '../providers/model/user.model';
import { JuegoItem } from '../providers/entities/juegoItem.entity';
import { JuegosService } from '../services/juegos/juegos.service';
import { Router } from '@angular/router';
import { AdminService } from '../services/admin/admin.service';
import { JuegosDto } from '../providers/dto/JuegosDto';

@Component({
  selector: 'app-panel-juegos',
  templateUrl: './panel-juegos.component.html',
  styleUrls: ['./panel-juegos.component.css']
})
export class PanelJuegosComponent implements OnInit {
  
  juegos: JuegoItem[];

  userAdm: User;

  constructor(
    private adminSrv: AdminService,
    private router: Router,
    private juegosSrv: JuegosService,
  ) { }

  ngOnInit() {
    this.userAdm = this.adminSrv.getUserLoggedIn();
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
