import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { NoticiasComponent } from './noticias/noticias.component';
import { NoticiaComponent } from './noticia/noticia.component';
import { LoginComponent } from './login/login.component';
import { RegistroComponent } from './registro/registro.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { JuegosComponent } from './juegos/juegos.component';
import { JuegoComponent } from './juego/juego.component';
import { PanelComponent } from './panel/panel.component';
import { LoginAdminComponent } from './login-admin/login-admin.component';
import { PanelJuegosComponent } from './panel-juegos/panel-juegos.component';
import { PanelNoticiasComponent } from './panel-noticias/panel-noticias.component';
import { PanelUsuariosComponent } from './panel-usuarios/panel-usuarios.component';
import { PanelAdminComponent } from './panel-admin/panel-admin.component';
import { BarraNavegacionComponent } from './barra-navegacion/barra-navegacion.component';
import { CrearNoticiaComponent } from './crear-noticia/crear-noticia.component';
import { CrearJuegoComponent } from './crear-juego/crear-juego.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    HeaderComponent,
    FooterComponent,
    NoticiasComponent,
    NoticiaComponent,
    LoginComponent,
    RegistroComponent,
    JuegosComponent,
    JuegoComponent,
    PanelComponent,
    LoginAdminComponent,
    PanelJuegosComponent,
    PanelNoticiasComponent,
    PanelUsuariosComponent,
    PanelAdminComponent,
    BarraNavegacionComponent,
    CrearNoticiaComponent,
    CrearJuegoComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    NgbModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
