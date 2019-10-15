export class NoticiaItem {
    id_noticia: number;
    titulo: string;
    descripcion: string;
    cuerpo: string;
    fecha_publicacion: Date;
    id_admin_creado: number;
    tags: number[];
    comentarios: number[];
}
