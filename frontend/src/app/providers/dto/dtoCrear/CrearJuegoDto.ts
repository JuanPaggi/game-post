export class CrearJuegoDto {
	titulo: String;
	descripcion: String;
	genero: String;
	desarrollador: String;
	fecha_lanzamiento: Date;
	analisis_positivos: number;
	analisis_negativos: number;
	id_requisitos: number;
	id_admin_creado: number;
    nombreImagen: String;
    archivoImagen: number[][];
	tags: number[];
	modos: number[];
}
