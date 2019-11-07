import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { RequisitosDto } from 'src/app/providers/dto/RequisitosDto';
import { RequisitoItem } from 'src/app/providers/entities/RequisitoItem.entity';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { RequisitoByIdDto } from 'src/app/providers/dto/dtoById/RequisitoByIdDto';
import { CrearRequisitoDto } from 'src/app/providers/dto/dtoCrear/CrearRequisitoDto';

@Injectable({
  providedIn: 'root'
})
export class RequisitosService {

  constructor(private http: HttpClient) { }

  public getAllRequisitos(body: RequisitosDto): Observable<RequisitoItem[]> {
    let headers = {};
    return this.http.get<RequisitoItem[]>(
        environment.apiEndpoint + '/requisitos',
        headers
    );
  }

  public getRequisito(body: RequisitoByIdDto): Observable<RequisitoItem> {
    let headers = {};
    return this.http.get<RequisitoItem>( environment.apiEndpoint + '/requisitos/' + body.id_requisito, headers );
  }

  public addRequisito(body: CrearRequisitoDto): Observable<Response> {
    return this.http.post<Response>(
        environment.apiEndpoint + '/requisitos',
        body
    );
  }

  public deleteRequisito(id_requisito: number): Observable<Response> {
    let headers = {};
    return this.http.delete<Response>(
        environment.apiEndpoint + '/requisitos/' + id_requisito,
        headers
    );
  }

  public editRequisito(body: CrearRequisitoDto, id_requisito: string): Observable<Response> {
    let headers = {};
    return this.http.put<Response>(
        environment.apiEndpoint + '/requisitos/' + id_requisito,
        body,
        headers
    );
  }
  
}
