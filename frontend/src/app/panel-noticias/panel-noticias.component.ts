import { Component, OnInit, ElementRef, ViewChild } from '@angular/core';
import { NoticiaItem } from '../providers/entities/NoticiaItem.entity';
import { User } from '../providers/model/user.model';
import { Router } from '@angular/router';
import { NoticiasService } from '../services/noticias/noticias.service';
import { NoticiasDto } from '../providers/dto/NoticiasDto';
import { UsuariosService } from '../services/usuarios/usuarios.service';

@Component({
  selector: 'app-panel-noticias',
  templateUrl: './panel-noticias.component.html',
  styleUrls: ['./panel-noticias.component.css']
})
export class PanelNoticiasComponent implements OnInit {

  noticias: NoticiaItem[];

  user: User;

  @ViewChild('imageUpload', {static: false}) imagInput: ElementRef;

  constructor(
    private usuarioSrv: UsuariosService,
    private router: Router,
    private noticiasSrv: NoticiasService,
  ) { }

  ngOnInit() {
    this.user = this.usuarioSrv.getUserLoggedIn();
    this.getNoticias();
  }

  volverPanel(){
    this.router.navigateByUrl(`panel`);
  }

  getNoticias() {
    this.noticiasSrv.getAllNoticias(new NoticiasDto()).subscribe(
      response => {
        this.noticias = response;
      }
    );
  }

  borrarNoticia(id:number){
    this.noticiasSrv.deleteNoticia(id).subscribe();
    for (let index = 0; index < this.noticias.length; index++) {
      if (this.noticias[index].id_noticia === id) {
        this.noticias.splice(index,1);
      }
    }
  }

  agregarNoticia(){
    this.router.navigateByUrl(`panel/panel-noticias/crear-noticia`);
  }

}
