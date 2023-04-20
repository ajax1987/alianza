import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Person } from '../common/person';

@Injectable({
  providedIn: 'root'
})
export class PersonService {

  personURL = 'http://localhost:8080/api/alianza/';
  
  //personURL = 'http://alianza-testing-env.eba-wgsr7wzr.us-east-1.elasticbeanstalk.com/api/alianza/';

  constructor(private httpClient: HttpClient) { }

  public lista(): Observable<Person[]> {
    return this.httpClient.get<Person[]>(this.personURL + 'list');
  }

  public detail(id: number): Observable<Person> {
    return this.httpClient.get<Person>(this.personURL + `detail/${id}`);
  }

  public detailName(nombre: string): Observable<Person> {
    return this.httpClient.get<Person>(this.personURL + `detailname/${nombre}`);
  }

  public save(person: Person): Observable<any> {
    return this.httpClient.post<any>(this.personURL + 'create', person);
  }

  public update(personId: number, person: Person): Observable<any> {
    return this.httpClient.put<any>(this.personURL + `update/${personId}`, person);
  }

  public delete(personId?: number): Observable<any> {
    return this.httpClient.delete<any>(this.personURL + `delete/${personId}`);
  }

  public searchPerson(theKeyword: string): Observable<Person[]> {
    const searchUrl = `${this.personURL}search/${theKeyword}`;
    return this.httpClient.get<Person[]>(searchUrl);
  }


}