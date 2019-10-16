import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { NoticiasDto } from '../providers/dto/NoticiasDto';
import { Observable } from 'rxjs';
import { NoticiaItem } from '../providers/entities/NoticiaItem.entity';
import { environment } from 'src/environments/environment';
import { NoticiaByIdDto } from '../providers/dto/noticiaByIdDto';
import { CrearNoticiaDto } from '../providers/dto/CrearNoticiaDto';

@Injectable({
  providedIn: 'root'
})
export class NoticiasService {

  constructor(private http: HttpClient) { }

  public getAllNoticias(body: NoticiasDto): Observable<NoticiaItem[]> {
    let headers = {};
    return this.http.get<NoticiaItem[]>(
        environment.apiEndpoint + '/noticias',
        headers
    );
  }

  public getNoticia(body: NoticiaByIdDto): Observable<NoticiaItem> {
    let headers = {};
    return this.http.get<NoticiaItem>( environment.apiEndpoint + '/noticias/' + body.id_noticia, headers );
  }

  public addNoticia(body: CrearNoticiaDto): Observable<Response> {
    return this.http.post<Response>(
        environment.apiEndpoint + '/noticias',
        body
    );
  }

}
