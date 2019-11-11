import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { NoticiasComponent } from './noticias/noticias.component';
import { NoticiaComponent } from './noticia/noticia.component';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { RegistroComponent } from './registro/registro.component';
import { JuegosComponent } from './juegos/juegos.component';
import { JuegoComponent } from './juego/juego.component';
import { PanelComponent } from './panel/panel.component';
import { LoginAdminComponent } from './login-admin/login-admin.component';
import { PanelJuegosComponent } from './panel-juegos/panel-juegos.component';
import { PanelNoticiasComponent } from './panel-noticias/panel-noticias.component';


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
    path: 'loginAdmin',
    component: LoginAdminComponent
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
    path: 'panel',
    component: PanelComponent
  },
  {
    path: 'panel/panel-juegos',
    component: PanelJuegosComponent
  },
  {
    path: 'panel/panel-noticias',
    component: PanelNoticiasComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
