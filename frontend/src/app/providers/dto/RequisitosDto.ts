export class RequisitosDto {
    RequisitoId?: number;
    elementesPerPage?: number;
    page?: number;

    constructor(RequisitoId?: number, elementsPerPage?: number, page?: number) {
        this.RequisitoId = RequisitoId;
        this.elementesPerPage = elementsPerPage;
        this.page = page;
    }
}
