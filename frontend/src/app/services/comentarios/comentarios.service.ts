import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ComentariosDto } from 'src/app/providers/dto/ComentariosDto';
import { ComentarioItem } from 'src/app/providers/entities/ComentarioItem.entity';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { ComentarioByIdDto } from 'src/app/providers/dto/dtoById/ComentarioByIdDto';
import { CrearComentarioDto } from 'src/app/providers/dto/dtoCrear/CrearComentarioDto';

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

  public addComentario(body: CrearComentarioDto): Observable<Response> {
    return this.http.post<Response>(
        environment.apiEndpoint + '/comentarios',
        body
    );
  }

  public deleteComentario(id_comentario: number): Observable<Response> {
    let headers = {};
    return this.http.delete<Response>(
        environment.apiEndpoint + '/comentarios/' + id_comentario,
        headers
    );
  }

  public editComentario(body: CrearComentarioDto, id_comentario: string): Observable<Response> {
    let headers = {};
    return this.http.put<Response>(
        environment.apiEndpoint + '/comentarios/' + id_comentario,
        body,
        headers
    );
  }

}
