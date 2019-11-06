import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { UsuarioItem } from 'src/app/providers/entities/UsuarioItem.entity';
import { UsuariosDto } from 'src/app/providers/dto/UsuariosDto';
import { UsuarioByIdDto } from 'src/app/providers/dto/UsuarioByIdDto';
import { CrearUsuarioDto } from 'src/app/providers/dto/dtoCrear/CrearUsuarioDto';
import { User } from '../../providers/model/user.model';
import { LoginDto } from '../../providers/dto/dtoLogin/LoginDto';

@Injectable({
  providedIn: 'root'
})
export class UsuariosService {

  private isUserLoggedIn: boolean;
  public usserLogged:User;

  constructor(private http: HttpClient) {
    this.isUserLoggedIn = false;
   }

  public getUsuarios(body: UsuariosDto): Observable<UsuarioItem[]> {
    let headers = {};
    return this.http.get<UsuarioItem[]>(
        environment.apiEndpoint + '/usuarios',
        headers
    );
  }

  public getUsuario(body: UsuarioByIdDto): Observable<UsuarioItem> {
    let headers = {};
    return this.http.get<UsuarioItem>( environment.apiEndpoint + '/usuarios/' + body.id_usuario, headers );
  }

  public addUsuario(body: CrearUsuarioDto): Observable<Response> {
    return this.http.post<Response>(
        environment.apiEndpoint + '/usuarios',
        body
    );
  }

  public deleteUsuario(id_usuario: number): Observable<Response> {
    let headers = {};
    return this.http.delete<Response>(
        environment.apiEndpoint + '/usuarios/' + id_usuario,
        headers
    );
  }

  public editUsuario(body: CrearUsuarioDto, id_usuario: string): Observable<Response> {
    let headers = {}
    return this.http.put<Response>(
        environment.apiEndpoint + '/usuarios/' + id_usuario,
        body,
        headers
    );
  }

  public verificarUsuario(body: LoginDto): Observable<number> {
    return this.http.post<number>(
        environment.apiEndpoint + '/usuarios/login',
        body
    );
  }

  setUserLoggedIn(user:User) {
    this.isUserLoggedIn = true;
    this.usserLogged = user;
    localStorage.setItem('currentUser', JSON.stringify(user));
  }

  setUserLoggedOut() {
    this.isUserLoggedIn = false;
    this.usserLogged = null;
    console.log("we");
    localStorage.setItem('currentUser', JSON.stringify(null));
  }

  getUserLoggedIn() {
  	return JSON.parse(localStorage.getItem('currentUser'));
  }

}
