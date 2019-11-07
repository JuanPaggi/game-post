export class ModosDto {
    ModoId?: number;
    elementesPerPage?: number;
    page?: number;

    constructor(ModoId?: number, elementsPerPage?: number, page?: number) {
        this.ModoId = ModoId;
        this.elementesPerPage = elementsPerPage;
        this.page = page;
    }
}
