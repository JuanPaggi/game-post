import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ModosDto } from 'src/app/providers/dto/ModosDto';
import { ModoItem } from 'src/app/providers/entities/ModoItem.entity';
import { environment } from 'src/environments/environment';
import { CrearModoDto } from 'src/app/providers/dto/dtoCrear/CrearModoDto';

@Injectable({
  providedIn: 'root'
})

export class ModosService {

  constructor(private http: HttpClient) { }

  public getAllModos(body: ModosDto): Observable<ModoItem[]> {
    let headers = {};
    return this.http.get<ModoItem[]>(
        environment.apiEndpoint + '/modos',
        headers
    );
  }

  public addModo(body: CrearModoDto): Observable<Response> {
    return this.http.post<Response>(
        environment.apiEndpoint + '/modos',
        body
    );
  }

  public deleteModo(id_modo: number): Observable<Response> {
    let headers = {};
    return this.http.delete<Response>(
        environment.apiEndpoint + '/modos/' + id_modo,
        headers
    );
  }
}
