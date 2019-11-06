export class UsuariosDto {
    UsuarioId?: number;
    elementesPerPage?: number;
    page?: number;

    constructor(UsuarioId?: number, elementsPerPage?: number, page?: number) {
        this.UsuarioId = UsuarioId;
        this.elementesPerPage = elementsPerPage;
        this.page = page;
    }
}
