import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AnalisisDto } from 'src/app/providers/dto/AnalisisDto';
import { Observable } from 'rxjs';
import { AnalisisItem } from 'src/app/providers/entities/AnalisisItem.entity';
import { environment } from 'src/environments/environment';
import { AnalisisByIdDto } from 'src/app/providers/dto/dtoById/AnalisisByIdDto';
import { CrearAnalisisDto } from 'src/app/providers/dto/dtoCrear/CrearAnalisisDto';

@Injectable({
  providedIn: 'root'
})
export class AnalisisService {

  constructor(private http: HttpClient) { }

  public getAllAnalisis(body: AnalisisDto): Observable<AnalisisItem[]> {
    let headers = {};
    return this.http.get<AnalisisItem[]>(
        environment.apiEndpoint + '/analisis',
        headers
    );
  }

  public getAnalisis(body: AnalisisByIdDto): Observable<AnalisisItem> {
    let headers = {};
    return this.http.get<AnalisisItem>( environment.apiEndpoint + '/analisis/' + body.id_analisis, headers );
  }

  public addAnalisis(body: CrearAnalisisDto): Observable<Response> {
    return this.http.post<Response>(
        environment.apiEndpoint + '/analisis',
        body
    );
  }

  public deleteAnalisis(id_analisis: number): Observable<Response> {
    let headers = {};
    return this.http.delete<Response>(
        environment.apiEndpoint + '/analisis/' + id_analisis,
        headers
    );
  }

  public editAnalisis(body: CrearAnalisisDto, id_analisis: string): Observable<Response> {
    let headers = {};
    return this.http.put<Response>(
        environment.apiEndpoint + '/analisis/' + id_analisis,
        body,
        headers
    );
  }

}
