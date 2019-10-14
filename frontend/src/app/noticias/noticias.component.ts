import { Component, OnInit } from '@angular/core';
import { NoticiasService } from '../services/noticias.service';
import { NoticiaItem } from '../providers/entities/NoticiaItem';
import { NoticiasRequestDto } from '../providers/dto/NoticiasRequestDto';

@Component({
  selector: 'app-noticias',
  templateUrl: './noticias.component.html',
  styleUrls: ['./noticias.component.css']
})
export class NoticiasComponent implements OnInit {

  noticias: NoticiaItem[];

  constructor(
    private noticiasSrv: NoticiasService
  ) { }

  ngOnInit() {
    this.getNoticias();
  }

  getNoticias() {
    this.noticiasSrv.getNoticiasAll(new NoticiasRequestDto()).subscribe(
      response => {
        this.noticias = response;
        console.log(this.noticias);
      }
    );
}
}
