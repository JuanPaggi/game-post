import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ComentariosDto } from 'src/app/providers/dto/ComentariosDto';
import { ComentarioItem } from 'src/app/providers/entities/ComentarioItem.entity';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { ComentarioByIdDto } from 'src/app/providers/dto/ComentarioByIdDto';

@Injectable({
  providedIn: 'root'
})
export class ComentariosService {

  constructor(private http: HttpClient) { }

  public getComentarios(body: ComentariosDto): Observable<ComentarioItem[]> {
    let headers = {};
    return this.http.get<ComentarioItem[]>(
        environment.apiEndpoint + '/comentarios',
        headers
    );
  }

  public getComentario(body: ComentarioByIdDto): Observable<ComentarioItem> {
    let headers = {};
    return this.http.get<ComentarioItem>( environment.apiEndpoint + '/comentario/' + body.id_comentario, headers );
  }

}
