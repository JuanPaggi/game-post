export class NoticiaItem {
    id_noticia: number;
    titulo: string;
    descripcion: string;
    cuerpo: string;
    fecha_publicacion: Date;
    id_usuario_noticia: number;
    tags: number[];
    comentarios: number[];
    imagenes: String[];
}
