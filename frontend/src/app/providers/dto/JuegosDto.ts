export class JuegosDto {
    JuegosId?: number;
    elementesPerPage?: number;
    page?: number;

    constructor(JuegosId?: number, elementsPerPage?: number, page?: number) {
        this.JuegosId = JuegosId;
        this.elementesPerPage = elementsPerPage;
        this.page = page;
    }
}
