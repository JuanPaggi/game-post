import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { NoticiasRequestDto } from '../providers/dto/NoticiasRequestDto';
import { Observable } from 'rxjs';
import { NoticiaItem } from '../providers/entities/NoticiaItem.entity';
import { environment } from 'src/environments/environment';
import { NoticiaByIdDto } from '../providers/dto/noticiaByIdDTO';
import { CrearNoticiaDto } from '../providers/dto/CreateNoticiaDto';

@Injectable({
  providedIn: 'root'
})
export class NoticiasService {

  constructor(private http: HttpClient) { }

  public getAllNoticias(body: NoticiasRequestDto): Observable<NoticiaItem[]> {
    let headers = {};
    return this.http.get<NoticiaItem[]>(
        environment.apiEndpoint + '/noticias',
        headers
    );
  }

  public getNoticia(body: NoticiaByIdDto): Observable<NoticiaItem> {
    let headers = {};
    return this.http.get<NoticiaItem>( environment.apiEndpoint + '/noticias/' + body.idNoticia, headers );
  }

  public addNoticia(body: CrearNoticiaDto): Observable<Response> {
    return this.http.post<Response>(
        environment.apiEndpoint + '/noticias',
        body
    );
  }

}
