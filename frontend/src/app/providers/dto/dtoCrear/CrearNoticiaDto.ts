export class CrearNoticiaDto {
    titulo: String;
    descripcion: String;
    cuerpo: String;
    fecha_publicacion: Date;
    id_usuario_noticia: number;
    tags: number[];
    nombreImagen: String;
    archivoImagen: number[][];
}
