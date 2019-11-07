export class CrearNoticiaDto {
    titulo: string;
    descripcion: string;
    cuerpo: string;
    fecha_publicacion: Date;
    id_admin_creado: number;
    tags: number[];
    nombreImagen: String;
    archivoImagen: number[][];
}
