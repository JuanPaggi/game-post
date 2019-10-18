import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { JuegosDto } from 'src/app/providers/dto/JuegosDto';
import { Observable } from 'rxjs';
import { JuegoItem } from 'src/app/providers/entities/juegoItem.entity';
import { environment } from 'src/environments/environment';
import { JuegoByIdDto } from 'src/app/providers/dto/JuegoByIdDto';
import { CrearJuegoDto } from 'src/app/providers/dto/dtoCrear/CrearJuegoDto';

@Injectable({
  providedIn: 'root'
})
export class JuegosService {

  constructor(private http: HttpClient) { }

  public getJuegos(body: JuegosDto): Observable<JuegoItem[]> {
    let headers = {};
    return this.http.get<JuegoItem[]>(
        environment.apiEndpoint + '/juegos',
        headers
    );
  }

  public getJuego(body: JuegoByIdDto): Observable<JuegoItem> {
    let headers = {};
    return this.http.get<JuegoItem>( environment.apiEndpoint + '/juegos/' + body.id_juego, headers );
  }

  public addComentario(body: CrearJuegoDto): Observable<Response> {
    return this.http.post<Response>(
        environment.apiEndpoint + '/juegos',
        body
    );
  }

  public deleteComentario(id_juego: number): Observable<Response> {
    let headers = {};
    return this.http.delete<Response>(
        environment.apiEndpoint + '/juegos/' + id_juego,
        headers
    );
  }

  public editComentario(body: CrearJuegoDto, id_juego: string): Observable<Response> {
    let headers = {};
    return this.http.put<Response>(
        environment.apiEndpoint + '/juegos/' + id_juego,
        body,
        headers
    );
  }

}