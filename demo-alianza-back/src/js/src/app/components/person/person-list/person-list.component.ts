import { Component, ElementRef, ViewChild, OnInit} from '@angular/core';
import { Person } from 'src/app/common/person';
import { PersonService } from 'src/app/services/person.service';
import { ActivatedRoute } from '@angular/router';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-person-list',
  templateUrl: './person-list.component.html',
  styleUrls: ['./person-list.component.css']
})
export class PersonListComponent implements OnInit {

  @ViewChild('myButton')
  myButton!: ElementRef;

  /*triggerClick() {
    console.log("hola");
    let el: HTMLElement = this.myButton.nativeElement as HTMLElement;
    setTimeout(() => el.click(), 5000);
  }*/

  triggerClick() {
    console.log("hola");
    let el: HTMLElement = this.myButton.nativeElement as HTMLElement;
    el.click();
  }

  persons: Person[] = [];

  searchMode: boolean = false;

  formValue!: FormGroup;

  btnUpdateShow: boolean = false;

  btnSaveShow: boolean = true;

  submitted: boolean = false;

  constructor(private router: Router, private formBuilder: FormBuilder,
    private personService: PersonService, private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(() => {
      this.listPersons();
    });
    this.reset();
    //this.cargarPersons();
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

  personobj: Person = {
    personId: 0,
    sharedKey: '',
    bussinesId: '',
    mobileNumber: 0,
    email: '',
    createdAt: '',
    updatedAt: ''
  };


  reset() {
    this.formValue = this.formBuilder.group({
      sharedKey: [''],
      bussinesId: [''],
      email: [''],
      mobileNumber: [''],
      createdAt: ['']
    })
  }
  addPerson() {
    this.personobj.sharedKey = this.formValue.value.sharedKey;
    this.personobj.bussinesId = this.formValue.value.bussinesId;
    this.personobj.mobileNumber = this.formValue.value.mobileNumber;
    this.personobj.email = this.formValue.value.email;
    this.personobj.createdAt = this.formValue.value.createdAt;

    this.personService.save(this.personobj).subscribe({
      next: (v) => { console.log(v) },
      error: (e) => {
        alert("Error " + e.error.errors);
        console.log(e)
      },
      complete: () => {
        console.log('complete')
        alert("Data Saved")
        this.listPersons();
        this.formValue.reset();
        this.triggerClick();
      }
    })
  }

  deletePerson(data: any) {
    var r = confirm("¿Seguro Deseas Eliminar Este Registro?");
    if (r == true) {
      console.log(data);
      this.personService.delete(data.personId).subscribe(res => {
        alert("Record Deleted");
        this.listPersons();
      })
    }
  }

  updateShowBtn() {
    this.btnUpdateShow = true;
    this.btnSaveShow = false;
    console.log("update " + this.btnUpdateShow);
    console.log("save " + this.btnSaveShow);
  }

  saveShowBtn() {
    this.btnUpdateShow = false;
    this.btnSaveShow = true;
    this.reset();
  }

  open() {
    this.btnUpdateShow = false;
  }

  editPerson(data: any) {
    console.log(data);
    this.formValue.controls['sharedKey'].setValue(data.sharedKey);
    this.formValue.controls['bussinesId'].setValue(data.bussinesId);
    this.formValue.controls['mobileNumber'].setValue(data.mobileNumber);
    this.formValue.controls['email'].setValue(data.email);
    this.formValue.controls['createdAt'].setValue(data.createdAt);
    this.personobj.personId = data.personId;
    this.updateShowBtn();
  }

  updatePerson() {
    this.personobj.sharedKey = this.formValue.value.sharedKey;
    this.personobj.bussinesId = this.formValue.value.bussinesId;
    this.personobj.mobileNumber = this.formValue.value.mobileNumber;
    this.personobj.email = this.formValue.value.email;
    this.personobj.createdAt = this.formValue.value.createdAt;

    this.personService.update(this.personobj.personId as number, this.personobj).subscribe(res => {
      alert("Data Updated");
      this.listPersons();
      this.saveShowBtn();
      this.triggerClick();
    });
  }


}