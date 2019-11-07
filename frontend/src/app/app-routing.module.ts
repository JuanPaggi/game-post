import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { NoticiasComponent } from './noticias/noticias.component';
import { NoticiaComponent } from './noticia/noticia.component';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { RegistroComponent } from './registro/registro.component';
import { JuegosComponent } from './juegos/juegos.component';
import { JuegoComponent } from './juego/juego.component';
import { RequisitosComponent } from './requisitos/requisitos.component';


const routes: Routes = [
  {
    path: '',
    component: HomeComponent
  },
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'registro',
    component: RegistroComponent
  },
  {
    path: 'noticias',
    component: NoticiasComponent
  },
  {
    path: 'noticias/:id_noticia',
    component: NoticiaComponent
  },
  {
    path: 'juegos',
    component: JuegosComponent
  },
  {
    path: 'juegos/:id_juego',
    component: JuegoComponent
  },
  {
    path: 'requisitos',
    component: RequisitosComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
