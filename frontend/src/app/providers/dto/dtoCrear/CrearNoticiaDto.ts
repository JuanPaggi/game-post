export class CrearNoticiaDto {
    titulo: String;
    descripcion: String;
    cuerpo: String;
    fecha_publicacion: Date;
    id_admin_creado: number;
    tags: number[];
    nombreImagen: String;
    archivoImagen: number[][];
}
