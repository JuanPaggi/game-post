import { Injectable } from '@angular/core';
import { User } from 'src/app/providers/model/user.model';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AdminDto } from 'src/app/providers/dto/AdminDto';
import { AdminItem } from 'src/app/providers/entities/AdminItem.entity';
import { environment } from 'src/environments/environment';
import { AdminByIdDto } from 'src/app/providers/dto/dtoById/AdminByIdDto';
import { CrearAdminDto } from 'src/app/providers/dto/dtoCrear/CrearAdminDto';
import { LoginDto } from 'src/app/providers/dto/dtoLogin/LoginDto';

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  private isUserLoggedIn: boolean;
  public usserLoggedAdm:User;

  constructor(private http: HttpClient) {
    this.isUserLoggedIn = false;
  }

  public getAdmins(body: AdminDto): Observable<AdminItem[]> {
    let headers = {};
    return this.http.get<AdminItem[]>(
        environment.apiEndpoint + '/admin',
        headers
    );
  }

  public getAdmin(body: AdminByIdDto): Observable<AdminItem> {
    let headers = {};
    return this.http.get<AdminItem>( environment.apiEndpoint + '/admin/' + body.id_admin, headers );
  }

  public addAdmin(body: CrearAdminDto): Observable<Response> {
    return this.http.post<Response>(
        environment.apiEndpoint + '/admin',
        body
    );
  }

  public deleteAdmin(id_admin: number): Observable<Response> {
    let headers = {};
    return this.http.delete<Response>(
        environment.apiEndpoint + '/admin/' + id_admin,
        headers
    );
  }

  public editAdmin(body: CrearAdminDto, id_admin: string): Observable<Response> {
    let headers = {}
    return this.http.put<Response>(
        environment.apiEndpoint + '/admin/' + id_admin,
        body,
        headers
    );
  }

  public verificarAdmin(body: LoginDto): Observable<number> {
    return this.http.post<number>(
        environment.apiEndpoint + '/admin/login',
        body
    );
  }

  setUserLoggedIn(user:User) {
    this.isUserLoggedIn = true;
    this.usserLoggedAdm = user;
    localStorage.setItem('currentUserAdm', JSON.stringify(user));
  }

  setUserLoggedOut() {
    this.isUserLoggedIn = false;
    this.usserLoggedAdm = null;
    localStorage.setItem('currentUserAdm', JSON.stringify(null));
  }

  getUserLoggedIn() {
  	return JSON.parse(localStorage.getItem('currentUserAdm'));
  }

}
