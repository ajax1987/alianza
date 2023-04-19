export class Person {

    personId?: number;
    sharedKey: string;
    bussinesId: string;
    mobileNumber: number;
    email: string;
    createdAt: string;
    updatedAt: string;

    

    constructor(sharedKey: string, bussinesId: string, mobileNumber: number, email: string, createdAt: string, updatedAt: string) {
        this.sharedKey = sharedKey;
        this.bussinesId = bussinesId;
        this.mobileNumber = mobileNumber;
        this.email = email;
        this.createdAt= createdAt;
        this.updatedAt= updatedAt;

    }
}