import { Component, OnInit } from '@angular/core';
import { Person } from 'src/app/common/person';
import { PersonService } from 'src/app/services/person.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-person-list',
  templateUrl: './person-list.component.html',
  styleUrls: ['./person-list.component.css']
})
export class PersonListComponent implements OnInit {

  persons: Person[] = [];
  searchMode: boolean = false;

  constructor(
    private personService: PersonService, private route: ActivatedRoute
  ) { }


  ngOnInit() {
    this.route.paramMap.subscribe(() => {
      this.listPersons();
    });
  }


  listPersons() {
    this.searchMode = this.route.snapshot.paramMap.has('keyword');
    if (this.searchMode) {
      this.handleSearchPersons();
    }
    else {
      this.cargarPersons();
    }
  }


  handleSearchPersons() {
    const theKeyword: string = this.route.snapshot.paramMap.get('keyword')!;
    // now search for the persons using keyword
    this.personService.searchPerson(theKeyword).subscribe(
      data => {
        this.persons = data;
      }, err => {
        alert(err.error.mensaje)
        console.log(err);
      }
    )
  }


  cargarPersons(): void {
    this.personService.lista().subscribe(
      data => {
        this.persons = data;
      },
      err => {
        console.log(err.mensaje);
      }
    );
  }



}
