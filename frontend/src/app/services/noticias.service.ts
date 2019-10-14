import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { NoticiasRequestDto } from '../providers/dto/NoticiasRequestDto';
import { Observable } from 'rxjs';
import { NoticiaItem } from '../providers/entities/NoticiaItem';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class NoticiasService {

  constructor(private http: HttpClient) { }

  public getNoticiasAll(body: NoticiasRequestDto): Observable<NoticiaItem[]> {
    let headers = {};
    return this.http.get<NoticiaItem[]>(
        environment.apiEndpoint + '/noticias',
        headers
    );
  }
}
